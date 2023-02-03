package au.com.safetychampion.scmobile.visitorModule // ktlint-disable filename

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.NonNull
import au.com.safetychampion.scmobile.constantValues.Constants
import au.com.safetychampion.scmobile.database.visitor.VisitorProfileDB
import au.com.safetychampion.scmobile.modules.common.SCBaseMessage
import au.com.safetychampion.scmobile.modules.noticeboard.data.models.SCQuillDeltaMessage
import au.com.safetychampion.scmobile.modules.noticeboard.data.utils.NoticeboardHelper
import au.com.safetychampion.scmobile.utils.DateUtils
import au.com.safetychampion.scmobile.utils.GeneralConverter
import au.com.safetychampion.scmobile.visitorModule.models.SCTextMessage
import au.com.safetychampion.scmobile.visitorModule.models.VisitorMockData
import au.com.safetychampion.data.visitor.models.VisitorPayload
import au.com.safetychampion.scmobile.visitorModule.models.VisitorProfile
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Minh Khoi MAI on 20/12/20.
 */

object VisitorUtil {
    fun profileToPii(p: VisitorProfile) = VisitorPayload.Pii(
        p.name.get() ?: "",
        p.email.get() ?: "",
        p.phoneNumber.get() ?: "",
        p.getPhoneCountryCodePayload() ?: ""
    )

    fun profileDBToPii(p: VisitorProfileDB) = profileToPii(p.toVisitorProfile())

    fun piiToProfile(pii: VisitorPayload.Pii): VisitorProfile {
        return VisitorProfile().apply {
            _id = VisitorMockData.mockProfileId
            name.set(pii.name)
            email.set(pii.email)
            phoneNumber.set(pii.phone)
            phoneCountryCode.set(pii.phoneCountryCode)
        }
    }

    fun decodeQR(code: String): Pair<String, String>? {
        return try {
            val orgSIndex = code.indexOf("/org/") + "/org/".length
            val orgEIndex = code.indexOf("/site/")
            val siteSIndex = code.indexOf("/site/") + "/site/".length
            val siteEIndex = code.length
            val orgString = code.substring(orgSIndex, orgEIndex).trim()
            val siteString = code.substring(siteSIndex, siteEIndex).trim()
            Pair(orgString, siteString)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun bindMessages(textMessages: SCBaseMessage, webView: WebView, ytView: YouTubePlayerView) {
        when (textMessages) {
            is SCTextMessage -> {
                val type = textMessages.type
                val msg = textMessages.value
                if (type == "text/html") {
                    val richTextCss = Constants.RICHTEXT_CSS
                    webView.visibility = View.VISIBLE
                    webView.loadDataWithBaseURL(
                        null,
                        richTextCss + msg,
                        "text/html; charset=utf-8",
                        "utf-8",
                        null
                    )
                } else if (type == "video/x-youtube") {
                    val videoId = msg.split('/').last().replace("watch?v=", "")
                    ytView.visibility = View.VISIBLE
                    ytView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                            youTubePlayer.cueVideo(videoId, 0f)
                        }
                    })
                }
            }

            is SCQuillDeltaMessage -> {
                val html = "file:///android_asset/editor.html"
                webView.visibility = View.VISIBLE
                webView.settings.javaScriptEnabled = true
                webView.settings.useWideViewPort = true
                webView.settings.textZoom = 200
                webView.setInitialScale(1)
                webView.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        if (request?.url.toString() != html) {
                            NoticeboardHelper.openUrl(webView.context, request?.url.toString())
                            return true
                        }
                        return false
                    }
                    override fun onPageFinished(view: WebView, url: String) {
                        view.loadUrl("javascript:setContent(${textMessages.value})")
                    }
                }
                webView.loadUrl(html)
            }
        }
    }

    fun dateTimeTwoLines(date: String?, time: String?): String {
        val mTime = GeneralConverter.convertTimeToAmPm(time)
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.UK)
        val dateString = DateUtils.fromString(date)?.let { dateFormat.format(it) }

        return "${dateString ?: ""} \n ${mTime ?: ""}"
    }

    fun dateTime(date: String?, time: String?): String {
        val mTime = GeneralConverter.convertTimeToAmPm(time)
        val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.UK)
        val dateString = DateUtils.fromString(date)?.let { dateFormat.format(it) }

        return "${dateString ?: ""} ${mTime ?: ""}"
    }
}

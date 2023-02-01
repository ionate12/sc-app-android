package au.com.safetychampion.scmobile.visitorModule.view

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.LinearLayout
import androidx.annotation.NonNull
import androidx.fragment.app.DialogFragment
import au.com.safetychampion.scmobile.R
import au.com.safetychampion.scmobile.databinding.VisitorPostMessageDialogFragmentBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class VisitorPostMessageDialogFragment : DialogFragment() {

    var messageType: String = ""
    var messageValue: String = ""
    var messageTitle: String = ""
    var onDismissListener: DialogInterface.OnDismissListener? = null

    private lateinit var binding: VisitorPostMessageDialogFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = VisitorPostMessageDialogFragmentBinding.inflate(inflater, container, false)
        binding.main = this
        binding.messageTitle.text = messageTitle

        bindMessage()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.apply {
            setBackgroundDrawableResource(R.drawable.rectangle_border_round_corner_fragment_dialog)
        }
    }

    private fun initWebView(msg: String): WebView? {

        val richTextCss = "<style>.ql-align-right {text-align: right;}.ql-align-center {text-align: center;}.ql-align-justify {text-align: justify;}.ql-size-huge {font-size: 2.5em;}.ql-size-large {font-size: 1.5em;}.ql-size-small {font-size: 0.75em;}</style>"
        return WebView(requireContext()).apply {
            loadDataWithBaseURL(null, richTextCss + msg,
                    "text/html; charset=utf-8", "UTF-8", null)
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        }
    }

    private fun bindMessage() {
        val type = messageType
        val msg = messageValue

        if (type == "text/html") {
            val mWebView = initWebView(msg)
            Handler(Looper.getMainLooper()).postDelayed({
                this.binding.webviewLayout.apply {
                    removeAllViews()
                    addView(mWebView)
                }
            }, 500)
        } else if (type == "video/x-youtube") {
            val videoId = msg.split('/').last().replace("watch?v=", "")
            binding.youtubePlayerView.visibility = View.VISIBLE
            lifecycle.addObserver(binding.youtubePlayerView)
            binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            })
        }
    }

    fun onSubmitOnClicked(v: View) {
        dialog?.dismiss()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.onDismiss(dialog)
    }

}
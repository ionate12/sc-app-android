package au.com.safetychampion.data.domain.uncategory

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.util.Base64
import android.util.TypedValue
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.util.*

object GeneralConverter {
    fun dpToFloat(context: Context, dp: Int): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        )
    }

    fun spToFloat(context: Context, sp: Int): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp.toFloat(),
            context.resources.displayMetrics
        )
    }

    @JvmStatic
    fun getStringFromBitmap(bitmapPicture: Bitmap): String {
        /*
         * This functions converts Bitmap picture to a string which can be
         * JSONified.
         * */
        val COMPRESSION_QUALITY = 100
        val encodedImage: String
        val byteArrayBitmapStream = ByteArrayOutputStream()
        bitmapPicture.compress(
            Bitmap.CompressFormat.PNG,
            COMPRESSION_QUALITY,
            byteArrayBitmapStream
        )
        val b = byteArrayBitmapStream.toByteArray()
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT)
        return encodedImage
    }

    @JvmStatic
    fun getBitmapFromString(stringPicture: String?): Bitmap {
        /*
         * This Function converts the String back to Bitmap
         * */
        val decodedString =
            Base64.decode(stringPicture, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }

    fun convertTimeToAmPm(time: String): String? {
        return try {
            val h = time.substring(0, time.indexOf(":")).toInt()
            val m = time.substring(time.indexOf(":") + 1)
            if (h >= 12) {
                val realh = h - 12
                (if (realh == 0) "12" else realh).toString() + ":" + m + "pm"
            } else (if (h == 0) "12" else h).toString() + ":" + m + "am"
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun singularString(text: String): String {
        val vowelString = "ueoai"
        return if (vowelString.contains(
                text.substring(0, 1).lowercase(Locale.getDefault())
            )
        ) {
            "An $text"
        } else "A $text"
    }

    fun setWidthFragmentDialog(window: Window) {
        val size = Point()
        val display = Objects.requireNonNull(window).windowManager.defaultDisplay
        display.getSize(size)
        window.setLayout((size.x * 0.95).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
    }
}

package au.com.safetychampion.scmobile.visitorModule.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import au.com.safetychampion.scmobile.R
import kotlinx.android.synthetic.main.container_visitor_current_sign_in.view.*

/**
 * Created by Minh Khoi MAI on 8/12/20.
 */
class VisitorCurrentSignInView(context: Context, attrs: AttributeSet? = null): LinearLayout(context, attrs) {

    init {
        inflate(context, R.layout.container_visitor_current_sign_in, this)
    }


    companion object {

        fun newInstance(context: Context, location: String, dateTime: String): VisitorCurrentSignInView {
            return VisitorCurrentSignInView(context).apply {
                this.location.text = location
                this.date_time.text = dateTime
                this.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            }
        }
    }
}
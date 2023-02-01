package au.com.safetychampion.scmobile.visitorModule.view

import android.os.Bundle
import au.com.safetychampion.scmobile.R
import au.com.safetychampion.scmobile.ui.activities.BaseActivity

class VisitorPinInputActivity : BaseActivity() {
    val TAG = "VisitorPinInputActivity"

    companion object {
        const val QR_CODE_DATA = "QR_CODE_DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visitor_pin_input)
        // fragment is started.
    }

}
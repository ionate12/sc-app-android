package au.com.safetychampion.scmobile.visitorModule.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import au.com.safetychampion.scmobile.BuildConfig
import au.com.safetychampion.scmobile.R
import au.com.safetychampion.scmobile.databinding.DialogManualQrInputBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by Minh Khoi MAI on 22/12/20.
 */
class ManualQRCodeInputDialog: BottomSheetDialogFragment() {
    lateinit var binding: DialogManualQrInputBinding

    //    private var model =  VisitorMockData.qrManualInput
    private var model = ManualQRCodeInputModel()
    private var callback: ((ManualQRCodeInputModel) -> Unit)? = null
    companion object {
        fun newInstance(): ManualQRCodeInputDialog {
            return ManualQRCodeInputDialog()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheetDialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_manual_qr_input, null, false)
        binding.model = this.model
        binding.submitBtn.setOnClickListener {
            callback?.invoke(this.model)
            this.dismiss()
        }
        return binding.root
    }

    fun setOnCompletedListener(callback: ((ManualQRCodeInputModel) -> Unit)) {
        this.callback = callback
    }

}

data class ManualQRCodeInputModel (var url: String? = null,
                                   var orgId:String? = null,
                                   var siteId:String? = null) {
    fun getCode(): String? {
        return url ?: return let {
            if(orgId == null || siteId == null) return@let null

            return@let "abcd/visitor/${orgId!!}/signin/${siteId!!}"
        }
    }
}
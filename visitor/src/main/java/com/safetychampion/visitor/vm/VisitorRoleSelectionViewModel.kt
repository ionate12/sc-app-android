package au.com.safetychampion.scmobile.visitorModule.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class VisitorRoleSelectionViewModel : ViewModel() {
    val role = MutableLiveData<String?>()
}
package au.com.safetychampion.scmobile.visitorModule.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import au.com.safetychampion.scmobile.modules.incident.model.CustomValue

class VisitorSignInViewModel : ViewModel() {
    val cusvals = MutableLiveData<List<CustomValue>>(listOf())


    init {
        //load some sample data
        val v1 = CustomValue("abc_xyz_1", "Custom Value 1", "", "text", false, listOf())
        val v2 = CustomValue("abc_xyz_2", "Custom Value 2", "", "email", false, listOf())
        val v3 = CustomValue("abc_xyz_3", "Custom Value 3", "", "date", false, listOf())

        cusvals.value = listOf(v1, v2, v3)

    }

}
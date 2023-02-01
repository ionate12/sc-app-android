package au.com.safetychampion.scmobile.visitorModule.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import au.com.safetychampion.scmobile.BaseViewModel
import au.com.safetychampion.scmobile.visitorModule.VisitorRepository
import au.com.safetychampion.scmobile.visitorModule.models.VisitorMockData
import au.com.safetychampion.scmobile.visitorModule.models.VisitorProfile
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class VisitorProfileViewModel : BaseViewModel() {
    var model = MutableLiveData<VisitorProfile>()
    var isEditMode: Boolean = false


    fun loadProfile() {
        cd.add(VisitorRepository.getProfile(VisitorMockData.mockProfileId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .delaySubscription(1000, TimeUnit.MILLISECONDS)
                .subscribe({ mProfile ->
                    model.value = mProfile
                }, { throwable ->
                    throwable.printStackTrace()
                }))
    }

    fun saveProfile(onComplete: Action) {
        val profile = model.value ?: return
        cd.add(VisitorRepository.saveProfile(profile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onComplete))
    }
}
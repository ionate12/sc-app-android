package au.com.safetychampion.data.domain.models.document

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import au.com.safetychampion.data.domain.uncategory.Constants
import au.com.safetychampion.data.domain.uncategory.DocAttachment
import au.com.safetychampion.data.domain.uncategory.GsonHelper
import com.google.gson.JsonObject

class DocumentSignoffTask {
    // -------------These var will not be in payload --------------------
    var radioBtnId = -1
    var enableCopyEdit = ObservableBoolean(false)

    // Kotlin changes the way they reference: This does work but cannot use it to deserialise.

    var hasNewVersion = ObservableBoolean()
    var hasNewVersionChangedCallback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            val value = (sender as ObservableBoolean)
            if (value.get() && !dateIssued.get().isNullOrEmpty() && !dateCompleted.get().isNullOrEmpty()) {
                if (dateCompleted.get()!! < dateIssued.get()!!) {
                    dateCompleted.set(dateIssued.get())
                }
            }
        }
    }

    // ------------- END --------------

    var complete = ObservableBoolean(false)
    var tzDateSignedoff = Constants.tz
    var dateCompleted: ObservableField<String> = object : ObservableField<String>() {
        override fun set(value: String?) {
            super.set(value)
            if (dateDueFrom.get()!!.contains("Completed")) {
                dateDueFrom.set("Date Completed ($value)")
            }
        }
    }

    var recurrent = ObservableBoolean(true)
    var copy = ObservableBoolean(false)
    var shareWithWorkplace = ObservableBoolean(false)
    var dateDueFrom = ObservableField<String>("")
    var name = ObservableField<String>()
    var description = ObservableField<String>()
    var category = object : ObservableField<String>() {
        override fun set(value: String?) {
            super.set(value)
            if (!subcategory.get().isNullOrEmpty()) {
                subcategory.set("")
            }
        }
    }

    var categoryOther = ObservableField<String>()
    var subcategory = ObservableField<String>()
    var subcategoryOther = ObservableField<String>()
    var dateIssued = object : ObservableField<String>() {
        override fun set(value: String?) {
            if (!value.isNullOrEmpty()) {
                if (!dateCompleted.get().isNullOrEmpty() && dateCompleted.get()!! < value) {
                    dateCompleted.set(value)
                }
            }
            super.set(value)
        }
    }
    var customVersion = ObservableField<String>()
    var reviewNotes = ObservableField<String>()
    var attachments: MutableList<DocAttachment> = mutableListOf()
    var tzDateCreated: String? = Constants.tz
    var upVersionFrom: VersionFrom = VersionFrom()

    fun initFieldObservers() {
        // 1. observer HasNewVersion
        hasNewVersion.addOnPropertyChangedCallback(hasNewVersionChangedCallback)
    }

    fun disposeFieldObservers() {
        // 1. observer HasNewVersion
        hasNewVersion.removeOnPropertyChangedCallback(hasNewVersionChangedCallback)
    }

    fun toPayload(): JsonObject {
        val jsonObj = GsonHelper.getGson().toJsonTree(this).asJsonObject
        jsonObj.remove("radioBtnId")
        jsonObj.remove("enableCopyEdit")
        jsonObj.remove("hasNewVersion")
        if (dateDueFrom.get()!!.contains("Date Completed")) {
            jsonObj.addProperty("dateDueFrom", "DATE_COMPLETED")
        } else if (dateDueFrom.get()!!.contains("Due Date")) {
            jsonObj.addProperty("dateDueFrom", "DATE_DUE")
        } else if (hasNewVersion.get()) {
            jsonObj.addProperty("dateDueFrom", "")
        }

        if (!hasNewVersion.get()) {
            // Remove all value from new document
            jsonObj.remove("name")
            jsonObj.remove("description")
            jsonObj.remove("category")
            jsonObj.remove("categoryOther")
            jsonObj.remove("subcategory")
            jsonObj.remove("subcategoryOther")
            jsonObj.remove("dateIssued")
            jsonObj.remove("attachments")
            jsonObj.remove("upVersionFrom")
            jsonObj.remove("copy")
            jsonObj.remove("customVersion")
        }

        return jsonObj
    }

    fun displayCategory(): String? {
        return if (category.get()?.toLowerCase().equals("other")) {
            "Other (${categoryOther.get()})"
        } else {
            category.get()
        }
    }

    fun displaySubCategory(): String? {
        return if (subcategory.get()?.toLowerCase().equals("other")) {
            "Other (${subcategoryOther.get()})"
        } else {
            subcategory.get()
        }
    }
}

class VersionFrom {
    var vNumber: Int = 0
}

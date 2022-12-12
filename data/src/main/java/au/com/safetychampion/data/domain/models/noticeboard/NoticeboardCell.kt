package au.com.safetychampion.scmobile.modules.noticeboard.data.ui.expandableList

import androidx.databinding.ObservableField

interface NoticeboardCell {
    companion object {
        const val VIEW_TYPE_CATEGORY = 0
        const val VIEW_TYPE_SUBCATEGORY = 1
        const val VIEW_TYPE_SUBCATEGORY_CHILD_ITEM = 2
    }
    val marginStart: Int
    var isExpanded: ObservableField<Boolean>
    fun hasData(): Boolean
}

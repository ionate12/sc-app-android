package au.com.safetychampion.scmobile.modules.noticeboard.data.ui.expandableList

import androidx.databinding.ObservableField
import au.com.safetychampion.scmobile.modules.noticeboard.data.ui.expandableList.NoticeboardCell.Companion.VIEW_TYPE_SUBCATEGORY_CHILD_ITEM

data class NoticeboardSubcategoryChildCell(
    override val id: String,
    override var text: String,
    override val searchField: String,
    override val description: String
) :
    NoticeboardDataInfo() {
    override val type: Int = VIEW_TYPE_SUBCATEGORY_CHILD_ITEM
    var lastItem: Boolean = false
    var isSelecting = ObservableField<Boolean>(false)

    override fun clone(): NoticeboardSubcategoryChildCell {
        return this.copy()
    }
}

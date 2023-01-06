package au.com.safetychampion.data.domain.models.noticeboard

import androidx.databinding.ObservableField
import au.com.safetychampion.scmobile.modules.noticeboard.data.ui.expandableList.NoticeBoardSubcategoryCell
import au.com.safetychampion.scmobile.modules.noticeboard.data.ui.expandableList.NoticeboardCell
import au.com.safetychampion.scmobile.modules.noticeboard.data.ui.expandableList.NoticeboardCell.Companion.VIEW_TYPE_CATEGORY
import au.com.safetychampion.scmobile.modules.noticeboard.data.ui.expandableList.NoticeboardDataInfo

data class NoticeboardCategoryCell(
    override val id: String,
    override val text: String,
    override val searchField: String,
    override val description: String
) : NoticeboardDataInfo(),
    NoticeboardCell {
    override val type: Int = VIEW_TYPE_CATEGORY
    override val marginStart: Int = 15
    override var isExpanded = ObservableField<Boolean>(false)
    var dataSource: MutableList<NoticeBoardSubcategoryCell> = mutableListOf()

    /**
     * Calling this after category clicked, to set all subcategory expand state to false.
     * This call not close subcategories which hasn't data
     */

    fun closeAllChildAreExpanding() {
        dataSource.forEach {
            if (it.isExpanded.get()!! && it.hasData()) {
                it.isExpanded.set(false)
            }
        }
    }

    override fun hasData() = dataSource.isNotEmpty()

    override fun clone(): NoticeboardCategoryCell {
        val mList = dataSource.map { it.clone() }.toMutableList()
        return this.copy().apply {
            dataSource = mList
        }
    }
}

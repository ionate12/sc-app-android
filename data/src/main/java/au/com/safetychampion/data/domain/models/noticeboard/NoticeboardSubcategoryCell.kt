package au.com.safetychampion.scmobile.modules.noticeboard.data.ui.expandableList // ktlint-disable filename

import androidx.databinding.ObservableField
import au.com.safetychampion.scmobile.modules.noticeboard.data.ui.expandableList.NoticeboardCell.Companion.VIEW_TYPE_SUBCATEGORY

data class NoticeBoardSubcategoryCell(
    override val id: String,
    override val text: String,
    override val searchField: String,
    override val description: String
) :
    NoticeboardDataInfo(),
    NoticeboardCell {
    override val type: Int = VIEW_TYPE_SUBCATEGORY
    override val marginStart: Int = 25
    override var isExpanded = ObservableField<Boolean>(false)
    var dataSource: MutableList<NoticeboardSubcategoryChildCell> = mutableListOf()
    override fun hasData() = dataSource.isNotEmpty()

    override fun clone(): NoticeBoardSubcategoryCell {
        val mList = this.dataSource.map { it.clone() }.toMutableList()
        return this.copy().apply {
            dataSource = mList
        }
    }
}

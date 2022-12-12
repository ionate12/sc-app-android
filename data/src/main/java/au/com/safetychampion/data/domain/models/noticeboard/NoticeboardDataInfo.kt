package au.com.safetychampion.scmobile.modules.noticeboard.data.ui.expandableList

import au.com.safetychampion.scmobile.modules.noticeboard.data.ui.expandableList.NoticeboardCell.Companion.VIEW_TYPE_SUBCATEGORY_CHILD_ITEM
import java.util.*

abstract class NoticeboardDataInfo : Cloneable {
    abstract val id: String
    abstract val type: Int
    abstract val text: String
    abstract val searchField: String
    abstract val description: String

    override fun equals(other: Any?): Boolean {
        if (other is NoticeboardDataInfo) {
            return ("$id$type" == "${other.id}${other.type}")
        }
        return false
    }

    fun areItemTheSame(other: Any?): Boolean {
        if (other is NoticeboardDataInfo) {
            return ("$text$type" == "${other.text}${other.type}")
        }
        return false
    }

    fun areContentsTheSame(other: NoticeboardDataInfo): Boolean {
        if (this::class != other::class) return false
        when (type) {
            VIEW_TYPE_SUBCATEGORY_CHILD_ITEM -> {
                if (other !is NoticeboardSubcategoryChildCell) {
                    return false
                }

                return other.let {
                    it.isSelecting.get()!! == (this as NoticeboardSubcategoryChildCell).isSelecting.get()
                }
            }

            else -> {
                if (other !is NoticeboardCell) {
                    return false
                }

                (other as NoticeboardCell).let {
                    return it.isExpanded.get()!! == (this as NoticeboardCell).isExpanded.get()
                }
            }
        }
    }

    public override fun clone(): Any {
        return super.clone()
    }

    override fun hashCode(): Int {
        return Objects.hash(id, text, type)
    }
}

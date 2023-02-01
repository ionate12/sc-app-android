package au.com.safetychampion.scmobile.visitorModule.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.core.view.children
import au.com.safetychampion.scmobile.R
import com.google.android.material.switchmaterial.SwitchMaterial
import kotlinx.android.synthetic.main.visitor_menu_popup_view.view.*

/**
 * Created by @Minh_Khoi_MAI on 3/12/20
 */
class VisitorMenuPopup(context: Context) : PopupWindow(context), View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    var onItemClickedListener: ((Menu, View) -> Unit)? = null
    var onSwitchChanged: ((Menu, Boolean) -> Unit)? = null

    init {
        contentView = LayoutInflater.from(context).inflate(R.layout.visitor_menu_popup_view, null)
        val bg = ContextCompat.getDrawable(context, R.color.transparent)
        setBackgroundDrawable(bg)
        elevation = 0f
        isOutsideTouchable = true
        contentView.item_layout.children.forEach {
            it.setOnClickListener(this)
        }
        contentView.auto_signout_flag_switch.setOnCheckedChangeListener(this)
    }

    fun setAutoSignoutFlagSwitch(isChecked: Boolean) {
        contentView.auto_signout_flag_switch.isChecked = isChecked
    }

    override fun onClick(v: View) {
        var selectedMenu: Menu = Menu.UNKNOWN
        when (v.id) {
            R.id.edit_profile -> selectedMenu = Menu.EDIT_PROFILE
            R.id.action_logout -> selectedMenu = Menu.LOGOUT
            R.id.auto_signout_flag_switch -> return
        }
        onItemClickedListener?.invoke(selectedMenu, v)

    }

    enum class Menu {
        EDIT_PROFILE, LOGOUT, AUTO_SIGNOUT_FLAG, UNKNOWN
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (buttonView is SwitchMaterial && buttonView.id == R.id.auto_signout_flag_switch) {
            onSwitchChanged?.invoke(Menu.AUTO_SIGNOUT_FLAG, isChecked)
        }
    }
}
package au.com.safetychampion.scmobile.visitorModule.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import au.com.safetychampion.scmobile.R
import au.com.safetychampion.scmobile.databinding.ContainerRecentActivityBinding
import au.com.safetychampion.scmobile.geolocation.GeoLocationUtils
import au.com.safetychampion.scmobile.geolocation.LocationPrecondition
import au.com.safetychampion.scmobile.visitorModule.models.AutoSignOutStatus
import au.com.safetychampion.scmobile.visitorModule.models.RecentActivityCell

class VisitorHistoryListAdapter : ListAdapter<RecentActivityCell, VisitorHistoryListAdapter.ViewHolder>(RecentActivityCell.DiffCallback) {
    var clickHandler: ((View, String) -> Unit)? = null
    var signOutButtonClickHandler: ((RecentActivityCell) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ContainerRecentActivityBinding = DataBindingUtil.inflate(inflater, R.layout.container_recent_activity, parent, false)
        val holder = ViewHolder(binding)
        holder.onCellClicked = { v, position ->
            clickHandler?.let { it(v, this.currentList[position]._id) }
        }
        holder.onSignOutBtnClicked = { position ->
            signOutButtonClickHandler?.let { it(this.currentList[position]) }
        }
        return holder
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = currentList[position]
        holder.binding.containerView.transitionName = holder.binding.containerView.context.getString(R.string.recent_activity_transition_name, data._id)
        holder.binding.cell = data.let { cell ->
            if (cell.autoSignOutStatus != AutoSignOutStatus.NO) {
                val nCell = cell.apply {
                    this.autoSignOutStatus = if (GeoLocationUtils.checkGeofencePreConditions(holder.binding.root.context) == LocationPrecondition.PASSED &&
                            GeoLocationUtils.isLocationServiceEnabled(holder.binding.root.context)) AutoSignOutStatus.YES else AutoSignOutStatus.PERMISSION_REQUIRE
                }
                nCell
            } else {
                cell
            }
        }
        holder.binding.executePendingBindings()
    }

    fun setDataSource(list: List<RecentActivityCell>) {
        this.submitList(list)
    }


    class ViewHolder(val binding: ContainerRecentActivityBinding) : RecyclerView.ViewHolder(binding.root) {
        var onCellClicked: ((View, Int) -> Unit)? = null
        var onSignOutBtnClicked: ((Int) -> Unit)? = null

        init {
            itemView.setOnClickListener { v ->
                onCellClicked?.let { it(v, adapterPosition) }
            }
            binding.signoutBtn.setOnClickListener { v ->
                onSignOutBtnClicked?.let { it(adapterPosition) }
            }
        }

    }
}
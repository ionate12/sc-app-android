package au.com

import android.annotation.SuppressLint
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import au.com.safetychampion.R
import au.com.safetychampion.databinding.TestAllItemBinding

class Adapter : RecyclerView.Adapter<Adapter.VH>() {

    val list: MutableList<Item> = mutableListOf()

    class VH(val binding: TestAllItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            TestAllItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).apply {
            binding.result.movementMethod = ScrollingMovementMethod()
        }
    }

    lateinit var testAgainn: (Int) -> Unit

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apply {
            path.text = list[position].path
            status.text = list[position].status
            result.text = list[position].result
            progress.isVisible = list[position].loading
            testAgain.visibility = list[position].testAgain
            testAgain.setOnClickListener { testAgainn.invoke(position) }
            var color = root.context.resources.getColor(list[position].color)
            root.setBackgroundColor(color)
        }
    }

    override fun getItemCount(): Int = list.size
}

data class Item(
    val path: String,
    var status: String,
    var result: String,
    var loading: Boolean,
    var color: Int = R.color.loading,
    var testAgain: Int = View.GONE
)

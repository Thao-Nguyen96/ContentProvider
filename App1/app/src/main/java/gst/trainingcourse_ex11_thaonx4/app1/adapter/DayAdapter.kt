package gst.trainingcourse_ex11_thaonx4.app1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import gst.trainingcourse_ex11_thaonx4.app1.databinding.ItemLayoutBinding
import gst.trainingcourse_ex11_thaonx4.app1.model.Day

class DayAdapter : RecyclerView.Adapter<DayAdapter.ViewHolder>() {

    private var binding: ItemLayoutBinding? = null

    private var differUtil = object : DiffUtil.ItemCallback<Day>(){
        override fun areItemsTheSame(oldItem: Day, newItem: Day): Boolean {
            return newItem.monday == oldItem.monday
        }

        override fun areContentsTheSame(oldItem: Day, newItem: Day): Boolean {
           return newItem == oldItem
        }
    }

    val differ = AsyncListDiffer(this,differUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayAdapter.ViewHolder {
        binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: DayAdapter.ViewHolder, position: Int) {
       holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(var binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(day: Day){
            binding.apply {
                tvId.text = day.id.toString()
                tvMonday.text = day.monday
                tvTuesday.text = day.tuesday
                tvWednesday.text = day.wednesday
                tvThursday.text = day.thursday
                tvFriday.text = day.friday
            }
        }
    }
}
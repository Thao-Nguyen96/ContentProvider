package com.nxt.mytable2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nxt.mytable2.databinding.ItemLayoutBinding

class PersonAdapter: RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    private var binding: ItemLayoutBinding? = null

    private val differUtil = object : DiffUtil.ItemCallback<Person>(){
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return newItem.id == oldItem.id
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return newItem == oldItem
        }
    }

    val differ = AsyncListDiffer(this,differUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonAdapter.ViewHolder {
        binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: PersonAdapter.ViewHolder, position: Int) {

        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder(var binding: ItemLayoutBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(person: Person){
            binding.apply {
                tvMonday.text = person.monday
                tvTuesday.text = person.tuesday
                tvWednesday.text = person.wednesday
            }
        }
    }
}
package com.yudha.navigation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.yudha.navigation.R
import com.yudha.navigation.databinding.ItemAnimalBinding
import com.yudha.navigation.model.Animal

/**
 * Created by yudha on 20,August,2019
 */
class AnimalListAdapter(private val animalList: ArrayList<Animal>): RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>(), AnimalClickListener {


    fun updateAnimalList(newAnimalList: List<Animal>){
        animalList.clear()
        animalList.addAll(newAnimalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemAnimalBinding>(inflater, R.layout.item_animal, parent, false)
        return  AnimalViewHolder(view)
    }

    override fun getItemCount() = animalList.size

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.view.animal = animalList[position]
        holder.view.listener = this
    }

    override fun onClick(v: View) {
        for (animal in animalList){
            if (v.tag == animal.name){
                val action = ListFragmentDirections.actionDetail(animal)
                Navigation.findNavController(v).navigate(action)
            }
        }
    }

    class AnimalViewHolder(val view: ItemAnimalBinding): RecyclerView.ViewHolder(view.root)
}
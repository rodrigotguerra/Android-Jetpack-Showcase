package com.rodrigotguerra.dogsapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.rodrigotguerra.dogsapp.R
import com.rodrigotguerra.dogsapp.model.DogBreed
import com.rodrigotguerra.dogsapp.util.getProgressDrawable
import com.rodrigotguerra.dogsapp.util.loadImage
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.item_dog.view.*

class DogListAdapter(private val dogList: ArrayList<DogBreed>) :
    RecyclerView.Adapter<DogListAdapter.DogViewHolder>() {



    fun upgradeDogList(newDogList: List<DogBreed>) {
        dogList.clear()
        dogList.addAll(newDogList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        return DogViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_dog,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val model = dogList[position]
        holder.view.tv_dog_name.text = model.name
        holder.view.tv_lifespan.text = model.lifeSpan
        holder.view.setOnClickListener{
            Navigation.findNavController(it).navigate(ListFragmentDirections.actionDetailFragment())
        }
        holder.view.iv_dog.loadImage(dogList[position].imageUrl, getProgressDrawable(holder.view.iv_dog.context))
    }

    override fun getItemCount(): Int {
        return dogList.size
    }

    class DogViewHolder(var view: View) : RecyclerView.ViewHolder(view)
}

package io.milis.sixt.home.ui.all_cars

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.milis.sixt.core.domain.services.entities.Car
import io.milis.sixt.home.R
import io.milis.sixt.util.DefaultDiffUtils
import kotlinx.android.synthetic.main.item_all_cars.view.*
import javax.inject.Inject


class AllCarsAdapter @Inject
constructor(private val layoutInflater: LayoutInflater) : ListAdapter<Car,
        AllCarsAdapter.ItemViewHolder>(DefaultDiffUtils<Car>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(layoutInflater.inflate(R.layout.item_all_cars, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindView(getItem(holder.adapterPosition))
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val make: TextView = itemView.make
        private val modelName: TextView = itemView.modelName
        private val image: ImageView = itemView.image
        private val name: TextView = itemView.name

        fun bindView(item: Car) {
            make.text = item.make
            modelName.text = item.modelName
            name.text = item.name
            Glide.with(itemView)
                    .load(item.carImageUrl)
                    .error(R.drawable.ic_car_fallback)
                    .into(image)
        }
    }
}
package io.milis.sixt.home.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.milis.sixt.core.domain.services.entities.Car
import io.milis.sixt.home.R
import io.milis.sixt.util.DefaultDiffUtils
import kotlinx.android.synthetic.main.item_car_details.view.*
import javax.inject.Inject


class HomeDetailsAdapter @Inject
constructor(private val layoutInflater: LayoutInflater) : ListAdapter<HomeDetailsAdapter.DetailsItem,
        HomeDetailsAdapter.ItemViewHolder>(DefaultDiffUtils<DetailsItem>()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(layoutInflater.inflate(R.layout.item_car_details, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindView(getItem(holder.adapterPosition))
    }

    fun submitItem(car: Car) {
        val items = listOf(
                DetailsItem(R.string.home_details_item_name, R.drawable.ic_user, car.name),
                DetailsItem(R.string.home_details_item_fuel, R.drawable.ic_fuel, car.fuelType),
                DetailsItem(R.string.home_details_item_licence, R.drawable.ic_licence_plate, car.licensePlate),
                DetailsItem(R.string.home_details_item_transmission, R.drawable.ic_transmission, car.transmission)
        )
        submitList(items)
    }

    override fun submitList(list: List<DetailsItem>?) {
        super.submitList(list)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val category: TextView = itemView.category
        private val value: TextView = itemView.value

        fun bindView(item: DetailsItem) {
            category.text = this.itemView.context.getString(item.title)
            category.setCompoundDrawablesWithIntrinsicBounds(item.drawable, 0, 0, 0)
            value.text = item.subtitle
        }
    }

    data class DetailsItem(
            @StringRes val title: Int,
            @DrawableRes
            val drawable: Int,
            val subtitle: String
    )
}
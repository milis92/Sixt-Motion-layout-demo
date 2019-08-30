package io.milis.sixt.home.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter
import io.milis.sixt.core.domain.services.entities.Car
import io.milis.sixt.home.R
import java.util.Locale
import javax.inject.Inject
import kotlin.collections.ArrayList


internal class CarsSuggestionAdapter @Inject constructor(inflater: LayoutInflater)
    : SuggestionsAdapter<Car, CarsSuggestionAdapter.ViewHolder>(inflater) {

    override fun getSingleViewHeight(): Int {
        return 80
    }

    var onItemSelected: (car: Car) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater.inflate(R.layout.item_car_suggestion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindSuggestionHolder(suggestion: Car, holder: ViewHolder, position: Int) {
        with(holder) {
            title.text = suggestion.make
            subTitle.text = suggestion.modelName
            itemView.setOnClickListener {
                onItemSelected.invoke(suggestion)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val results = FilterResults()
                val term = constraint.toString()
                if (term.isEmpty())
                    suggestions = suggestions_clone
                else {
                    suggestions = ArrayList()
                    for (item in suggestions_clone) {
                        if (item.make.toLowerCase(Locale.getDefault())
                                        .contains(term.toLowerCase(Locale.getDefault())) ||
                                item.modelName.toLowerCase(Locale.getDefault())
                                        .contains(term.toLowerCase(Locale.getDefault()))) {
                            suggestions.add(item)
                        }
                    }
                }
                results.values = suggestions
                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                @Suppress("UNCHECKED_CAST")
                suggestions = results.values as ArrayList<Car>
                notifyDataSetChanged()
            }
        }
    }

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.title)
        val subTitle = itemView.findViewById<TextView>(R.id.subtitle)
        val image = itemView.findViewById<ImageView>(R.id.image)
    }

}
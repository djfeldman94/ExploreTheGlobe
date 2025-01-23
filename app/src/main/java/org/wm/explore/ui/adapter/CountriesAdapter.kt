package org.wm.explore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.etg.core.domain.model.Country
import org.wm.explore.databinding.ItemCountryBinding

class CountriesAdapter(
    private var items: List<Country>
) : RecyclerView.Adapter<CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemCountryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CountryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateItems(countries: List<Country>) {
        items = countries
    }
}

class CountryViewHolder(
    private val binding: ItemCountryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(country: Country) {
        binding.apply {
            nameRegionText.text = "${country.name}, ${country.region} ${country.code}"
            capitalText.text = country.capital
        }
    }
}

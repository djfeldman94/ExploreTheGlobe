package org.wm.explore.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.etg.core.domain.model.Country
import org.wm.explore.R
import org.wm.explore.databinding.ItemCountryBinding

/**
 * Adapter for the list of countries to be displayed in the RecyclerView
 */
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
            nameRegionText.text =
                binding.root.context.getString(R.string.country_format_template, country.name, country.region)
            codeText.text = country.code
            capitalText.text = country.capital
        }
    }
}

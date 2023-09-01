package com.example.walmartlabstest.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.walmartlabstest.data.model.Country
import com.example.walmartlabstest.databinding.CountryListItemBinding
import com.example.walmartlabstest.utils.CountryValidationUtil

/**
 * A simple Recycler view adapter that hooks up with the data from the network repository and binds data from the data source with the Viewholder.
 * This class extends the ListAdapter and has an inner object DiffUtil which acts as a differentiator between any 2 items in the viewholder.
 * */
class CountryListAdapter : ListAdapter<Country, CountryListAdapter.CountryViewHolder>(CountryDiffCallback) {

    class CountryViewHolder(val binding: CountryListItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = CountryListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = getItem(position)
        if (CountryValidationUtil.validateCountry(country)) {
            bind(country = country, binding = holder.binding)
        }
    }

    private fun bind(country: Country, binding: CountryListItemBinding) {

        binding.countryNameAndRegion.text = createConcatenatedString(country.name, country.region)
        binding.countryCapital.text = country.capital
        binding.countryCode.text = country.code
    }

    private fun createConcatenatedString(name: String, region: String): String {
        val input = listOf(name, region)
        val separator = ", "

        return input.joinToString(separator)
    }

    override fun getItemCount(): Int {
        return when (val count = super.getItemCount()) {
            0 -> 1
            else -> count
        }
    }

    // Simple Diff Util callback that checks if two country codes are not the same.
    object CountryDiffCallback : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem.code == newItem.code
        }
    }
}
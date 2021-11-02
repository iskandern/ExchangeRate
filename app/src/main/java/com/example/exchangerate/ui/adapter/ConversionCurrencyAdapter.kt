package com.example.exchangerate.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangerate.data.database.ConversionCurrency
import com.example.exchangerate.databinding.CurrencyItemBinding

class ConversionCurrencyAdapter :
    RecyclerView.Adapter<ConversionCurrencyAdapter.ConversionCurrencyViewHolder>() {

    private val items = mutableListOf<ConversionCurrency>()
    private lateinit var clickListener: (ConversionCurrency) -> Unit

    class ConversionCurrencyViewHolder(val binding: CurrencyItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConversionCurrencyViewHolder {

        val binding = CurrencyItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return ConversionCurrencyViewHolder(binding).apply {
            binding.root.setOnClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION)
                    return@setOnClickListener

                clickListener(items[adapterPosition])
            }
        }
    }

    fun setItemClickListener(itemClickListener: (ConversionCurrency) -> Unit) {
        clickListener = itemClickListener
    }

    override fun onBindViewHolder(holder: ConversionCurrencyViewHolder, position: Int) {
        holder.binding.apply {
            currencyRate.text = "%.${2}f".format(items[position].rate)
            currencyName.text = items[position].code
        }
    }

    override fun getItemCount(): Int = items.size

    fun setCategories(newItems: List<ConversionCurrency>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}
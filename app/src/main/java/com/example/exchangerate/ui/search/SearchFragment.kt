package com.example.exchangerate.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.exchangerate.R
import com.example.exchangerate.databinding.FragmentSearchBinding
import com.example.exchangerate.ui.main.MainActivity
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.core.scope.Scope

class SearchFragment : MvpAppCompatFragment(), SearchView, AndroidScopeComponent {

    override val scope: Scope by fragmentScope()

    private val presenter by moxyPresenter {
        val presenter: SearchPresenter by inject()
        presenter
    }
    private var binding: FragmentSearchBinding? = null
    private var currenciesAdapter : ArrayAdapter<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        val handler = (activity as MainActivity).getSelectBaseHandler()
        presenter.setSelectHandler(handler)

        val currencies = resources.getStringArray(R.array.currencies)
        showSymbols(currencies, 0)

        return binding!!.root
    }

    private fun setupSpinner(basePosition: Int) {
        with(binding!!.spinner) {
            adapter = currenciesAdapter
            setSelection(basePosition)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    currenciesAdapter?.getItem(position)?.let { base ->
                        presenter.broadcastBaseCurrency(base)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) = Unit
            }
        }
    }

    private fun showSymbols(symbols: Array<String>, basePosition: Int) {
        currenciesAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, symbols)
        setupSpinner(basePosition)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}
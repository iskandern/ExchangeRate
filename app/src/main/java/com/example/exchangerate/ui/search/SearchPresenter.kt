package com.example.exchangerate.ui.search

import moxy.MvpPresenter

class SearchPresenter : MvpPresenter<SearchView>() {

    private lateinit var handle: (String) -> Unit

    fun setSelectHandler(selectHandler: (String) -> Unit) {
        handle = selectHandler
    }

    fun broadcastBaseCurrency(base: String) {
        handle(base)
    }
}
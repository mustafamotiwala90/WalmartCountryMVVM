package com.example.walmartlabstest.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Simple Data observer which can attach to any recycler view to notify UI about an empty list of data [RecyclerView.AdapterDataObserver] is the class which it extends and uses its internal functions to check if the data has not turned out to be empty.
 * */
class EmptyCountryDataObserver constructor(recyclerView: RecyclerView?, nullView: View?): RecyclerView.AdapterDataObserver() {

    private var emptyView: View? = null
    private var recyclerView: RecyclerView? = null

    init {
        this.recyclerView = recyclerView
        this.emptyView = nullView
        checkIfEmpty()
    }

    private fun checkIfEmpty() {
        if (emptyView != null && recyclerView?.adapter != null) {
            val emptyViewVisible = recyclerView!!.adapter?.itemCount == 0
            emptyView!!.visibility = if (emptyViewVisible) View.VISIBLE else View.GONE
            recyclerView!!.visibility = if (emptyViewVisible) View.GONE else View.VISIBLE
        }
    }

    override fun onChanged() {
        super.onChanged()
        checkIfEmpty()
    }
}
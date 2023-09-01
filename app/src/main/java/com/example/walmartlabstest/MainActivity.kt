package com.example.walmartlabstest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.walmartlabstest.application.AppContainer
import com.example.walmartlabstest.application.WalmartApplication
import com.example.walmartlabstest.data.model.Country
import com.example.walmartlabstest.databinding.ActivityListCountryBinding
import com.example.walmartlabstest.view.adapter.CountryListAdapter
import com.example.walmartlabstest.view.adapter.EmptyCountryDataObserver
import com.example.walmartlabstest.view.factory.CountryViewModelFactory
import com.example.walmartlabstest.view.viewmodel.CountryViewModel


/**
 * The Main view/activity of the app. It accomplishes a few things along with interacting with the rest of the app.
 * This is the V in the MVVM architecture.
 * It uses Manual dependency Injection to get an app container object.
 * It does setting up of the view and viewstates to be used to interact with the view model class.
 * It initializes the adapter and uses the DI app container logic to fetch the usecase class which will then be in turn used to fetch the data.
 *
 *
 * @property setupViewState : This method sets up the data response to check if the view state is in the "loaded" state. if so it tells the view binding to display the appropriate view
 *
 * Some error handling cases this class handles is :
 * - Handles device rotation by saving the instance state by invoking the internal StateRestorationPolicy of the RecyclerView.
 * - Creates and attaches an Empty Data Observer to handle the edge case of no network when the response is received.
 * - Create cache for network response caching
 * - Clearing the internal viewModel Store @see L120 to see the view model store being cleared.
 * */
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel:CountryViewModel
    private val countryListAdapter by lazy {
        CountryListAdapter()
    }
    lateinit var binding: ActivityListCountryBinding
    private lateinit var appContainer:AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = (application as WalmartApplication).appContainer
        setContentView(R.layout.activity_list_country)
        binding = ActivityListCountryBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setupView()
        setupViewState()
    }

    // Sets up the view and recyclerview adapter
    private fun setupView() {
        initAdapter()
        initializeViewModel()
        val recyclerView = binding.countryListView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        countryListAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
        val emptyDataView:View = findViewById(R.id.emptyDataView)
        val emptyDataObserver = EmptyCountryDataObserver(recyclerView, emptyDataView)
        countryListAdapter.registerAdapterDataObserver(emptyDataObserver)
    }

    private fun initAdapter() {
        binding.countryListView.apply {
            adapter = countryListAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(
            this,
            CountryViewModelFactory(
                appContainer.useCase
            )
        )[CountryViewModel::class.java]

        // Fetch countries data
        viewModel.fetchCountries()
    }

    private fun setupViewState() {
        viewModel.countryData.observe(this) {
           countryData ->
            if(countryData.isLoading) {
                binding.loadingBar.visibility = View.VISIBLE
                binding.countryListView.visibility = View.GONE
            } else if(countryData.countries.isNotEmpty()) {
                renderList(countryList = countryData.countries)
                binding.countryListView.visibility = View.VISIBLE
                binding.loadingBar.visibility = View.GONE
            } else {
                binding.loadingBar.visibility = View.GONE
            }
        }
    }

    // Renders the list by using submitList on the adapter
    private fun renderList(countryList: List<Country>?) {
        if (countryList != null) {
            countryListAdapter.submitList(countryList)
        }
    }

    override fun onDestroy() {
        viewModelStore.clear()
        super.onDestroy()
    }
}
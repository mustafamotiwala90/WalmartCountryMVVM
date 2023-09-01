package com.example.walmartlabstest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.walmartlabstest.data.api.CountryListApiService
import com.example.walmartlabstest.data.model.Country
import com.example.walmartlabstest.domain.GetCountriesListUseCase
import com.example.walmartlabstest.network.NetworkRepository
import com.example.walmartlabstest.view.state.CountriesListState
import com.example.walmartlabstest.view.state.ViewState
import com.example.walmartlabstest.view.viewmodel.CountryViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Sample unit test for the View Model class [CountryViewModel]
 * */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CountryViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: CountryViewModel

    @Mock
    lateinit var networkRepository: NetworkRepository

    @Mock
    lateinit var useCase: GetCountriesListUseCase

    @Mock
    lateinit var countryListApiService: CountryListApiService

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        countryListApiService = mock(CountryListApiService::class.java)
        networkRepository = mock(NetworkRepository::class.java)
        useCase = mock(GetCountriesListUseCase::class.java)
        viewModel = CountryViewModel(useCase)
    }

    @Test
    fun `get all countries test`() {
        runBlocking {
            Mockito.`when`(useCase.invoke())
                .thenReturn(flowOf(ViewState.LoadSuccess(createSampleListOfCountry())))
            viewModel.fetchCountries()
            val result = viewModel.countryData.value
            assertEquals(CountriesListState(isLoading = false, countries = createSampleListOfCountry()), result)
        }
    }

    @Test
    fun `empty countries list test`() {
        runBlocking {
            Mockito.`when`(useCase.invoke())
                .thenReturn(flowOf(ViewState.LoadSuccess(createEmptyListOfCountries())))
            viewModel.fetchCountries()
            val result = viewModel.countryData.value
            assertEquals(CountriesListState(isLoading = false, countries = createEmptyListOfCountries()), result)
        }
    }

    @Test
    fun `loading countries list test`() {
        runBlocking {
            Mockito.`when`(useCase.invoke())
                .thenReturn(flowOf(ViewState.Loading(createEmptyListOfCountries())))
            viewModel.fetchCountries()
            val result = viewModel.countryData.value
            assertEquals(CountriesListState(isLoading = true, countries = createEmptyListOfCountries()), result)
        }
    }

    private fun createSampleListOfCountry(): List<Country> {
        return listOf(Country("bombay", "IN", "India"))
    }

    private fun createEmptyListOfCountries(): List<Country> {
        return listOf()
    }
}
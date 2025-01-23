package org.wm.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etg.core.domain.model.Country
import com.etg.core.domain.repository.CountryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CountriesViewModel(
    private val countriesRepository: CountryRepository
): ViewModel() {
    private val _countriesState = MutableStateFlow<CountriesState>(CountriesState.Loading)
    val countriesState: StateFlow<CountriesState> = _countriesState

    init {
        getCountries()
    }

    fun getCountries() {
        _countriesState.value = CountriesState.Loading
        viewModelScope.launch {
            countriesRepository.getCountries().let { result ->
                result.fold(
                    onSuccess = { countries ->
                        _countriesState.value = CountriesState.Success(countries)
                    },
                    onFailure = { error ->
                        _countriesState.value =
                            CountriesState.Error(error.message ?: "An unexpected error occurred")
                    }
                )
            }
        }
    }


    sealed class CountriesState {
        object Loading : CountriesState()
        data class Success(val countries: List<Country>) : CountriesState()
        data class Error(val message: String) : CountriesState()
    }
}
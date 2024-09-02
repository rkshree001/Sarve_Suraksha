package com.bnet.sarvesuraksha.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bnet.sarvesuraksha.Repository.VehicleRepository
import com.bnet.sarvesuraksha.model_api.GetListVehicleMainData
import com.bnet.sarvesuraksha.model_api.GetListVehicleMainRes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MyVehiclesViewModel @Inject constructor(
    private val repository: VehicleRepository
) : ViewModel() {

    private val _vehicleList = MutableLiveData<List<GetListVehicleMainRes>>()
    val vehicleList: LiveData<List<GetListVehicleMainRes>> get() = _vehicleList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getUserVehicleData(mobileNumberString: String) {
        viewModelScope.launch {
            try {
                val response: Response<GetListVehicleMainData> = repository.getAllVehicleDetailList(mobileNumberString)
                if (response.isSuccessful) {
                    response.body()?.data?.vehicleList?.let {
                        _vehicleList.postValue(it)
                    } ?: run {
                        _error.postValue("No data found")
                    }
                } else {
                    _error.postValue("Server Error: ${response.message()}")
                }
            } catch (e: Exception) {
                _error.postValue("Failed to get user details: ${e.localizedMessage}")
            }
        }
    }
}

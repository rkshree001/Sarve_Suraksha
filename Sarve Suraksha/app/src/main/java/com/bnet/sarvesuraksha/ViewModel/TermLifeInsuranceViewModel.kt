package com.bnet.sarvesuraksha.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bnet.sarvesuraksha.Repository.TermLifeInsuranceRepository
import com.bnet.sarvesuraksha.model_api.GetTermLifeQuoteListMainData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class TermLifeInsuranceViewModel @Inject constructor(
    private val repository: TermLifeInsuranceRepository
) : ViewModel() {

    private val _quoteListData = MutableLiveData<Response<GetTermLifeQuoteListMainData?>>()
    val quoteListData: LiveData<Response<GetTermLifeQuoteListMainData?>> get() = _quoteListData

    fun getQuoteList(termLifeID: String, data: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.postAndGetTLQuote(termLifeID, data).execute()
            _quoteListData.postValue(response)
        }
    }
}

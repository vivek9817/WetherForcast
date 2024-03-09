package com.example.wetherforcasting.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.wetherforcasting.Domains.ApiCallback
import com.example.wetherforcasting.Domains.Constant
import com.example.wetherforcasting.Domains.Repository
import com.example.wetherforcasting.Model.CurrentForcast
import com.example.wetherforcasting.Utils.CommonUtils.convertLinkedTreeMapToClass
import kotlinx.coroutines.Dispatchers

class WetherViewModels() : ViewModel() {

    fun getCurrentWetherReport() = liveData(Dispatchers.Default) {
        emit(ApiCallback.onLoading(null))
        try {
            Repository().getRequest().apply {
                convertLinkedTreeMapToClass<CurrentForcast>(this!!).apply {
                    emit(ApiCallback.onSuccess(data = this))
                }
            }
        } catch (err: Throwable) {
            emit(ApiCallback.onFailure(data = null, message = err.localizedMessage))
        }
    }

}
package com.example.wetherforcasting.Domains

import com.example.wetherforcasting.Utils.CommonUtils

/**
 * Will be used for getting callbacks from api calls
 */
data class ApiCallback<out T>(
    val status: CommonUtils.ApiStatus
    , val data: T?
    , val message: String?
) {
    /* every state-wise ui handling needs to be done only in the views, i.e activities or fragments */
    companion object {

        /**
         * On success state callback
         */
        fun<T> onSuccess(data: T): ApiCallback<T> = ApiCallback(status = CommonUtils.ApiStatus.SUCCESS
            , data = data, message = null)

        /**
         * On failure/ error state callback
         */
        fun<T> onFailure(data: T?, message: String? = "Something went wrong"): ApiCallback<T> = ApiCallback(status = CommonUtils.ApiStatus.FAILURE
            , data = data, message = message)

        /**
         * loading state callback
         */
        fun<T> onLoading(data: T): ApiCallback<T> = ApiCallback(status = CommonUtils.ApiStatus.LOADING
            , data = null, message = null)
    }
}
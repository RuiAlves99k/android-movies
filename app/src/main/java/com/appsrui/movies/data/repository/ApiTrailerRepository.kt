package com.appsrui.movies.data.repository

import com.appsrui.movies.common.Resource
import com.appsrui.movies.common.UNKNOWN_ERROR_OCCURRED
import com.appsrui.movies.data.remote.KinocheckApi
import com.appsrui.movies.data.remote.TrailerResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiTrailerRepository @Inject constructor(private val kinocheckApi: KinocheckApi) :
    TrailerRepository {
    override fun getTrailer(tmdbId: Int): Flow<Resource<TrailerResponse>> = flow {
        val response = kinocheckApi.getTrailer(tmdbId)
        if (response.isSuccessful) {
            emit(Resource.Success(data = response.body()))
        } else {
            val error = response.errorBody()?.string() ?: UNKNOWN_ERROR_OCCURRED
            emit(Resource.Error(message = error))
        }
    }


}
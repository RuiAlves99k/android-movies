package com.appsrui.movies.data.repository

import com.appsrui.movies.common.Resource
import com.appsrui.movies.data.remote.TrailerResponse
import kotlinx.coroutines.flow.Flow

interface TrailerRepository {
    fun getTrailer(tmdbId: Int): Flow<Resource<TrailerResponse>>
}
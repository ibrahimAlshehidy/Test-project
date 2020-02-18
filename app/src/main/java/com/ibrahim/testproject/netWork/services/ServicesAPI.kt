package com.ibrahim.testproject.netWork.services

import com.ibrahim.testproject.netWork.models.DataModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url


interface ServicesAPI {

    @GET
    fun data(@Url url: String): Observable<List<DataModel>>

}
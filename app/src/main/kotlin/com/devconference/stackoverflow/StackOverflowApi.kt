package com.devconference.stackoverflow

import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface StackOverflowApi {

  @GET("/2.2/questions?order=desc&sort=creation&site=stackoverflow")
  fun taggedQuestions(@Query("tagged") tags: String): Observable<StackOverflowQuestions>

}
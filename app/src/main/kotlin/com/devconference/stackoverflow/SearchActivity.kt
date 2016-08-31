package com.devconference.stackoverflow

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class SearchActivity : AppCompatActivity() {

  val api: StackOverflowApi by lazy {
    val retrofit = Retrofit.Builder()
          .baseUrl("https://api.stackexchange.com")
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
          .build()
    retrofit.create(StackOverflowApi::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search)

    api.taggedQuestions("Android")
          .observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.io())
          .subscribe(
                { questions -> },
                { error -> },
                {}
          )
  }

}

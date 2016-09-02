package com.devconference.stackoverflow

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Subscription
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

  val editSearchInput by lazy { (findViewById(R.id.edit_search_input) as SearchWidget) }

  private var searchInputSubscription: Subscription? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search)

    searchInputSubscription = editSearchInput.textChangeSearchBehaviorObservable()
        .concatMap { query ->
          api.taggedQuestions(query)
              .observeOn(AndroidSchedulers.mainThread())
              .subscribeOn(Schedulers.io())
        }
        .subscribe(
            { questions -> Log.d("Uhuu", "Result: ${questions.toString()}") },
            { error -> Log.e("Uhuu", "Error") },
            { Log.d("Uhuu", "Completed") }
        )
  }

  override fun onDestroy() {
    super.onDestroy()
    searchInputSubscription?.unsubscribe()
  }

}

package com.devconference.stackoverflow

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
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

  val editSearchInput by lazy { findViewById(R.id.edit_search_input) as SearchWidget }
  val questionListRecyclerVIew by lazy { findViewById(R.id.question_list_recycler_view) as RecyclerView }

  private var searchInputSubscription: Subscription? = null
  private val adapter: QuestionListAdapter by lazy { QuestionListAdapter(this) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_search)

    questionListRecyclerVIew.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
    questionListRecyclerVIew.adapter = adapter

    searchInputSubscription = editSearchInput.textChangeSearchBehaviorObservable()
        .concatMap { query ->
          api.taggedQuestions(query)
              .observeOn(AndroidSchedulers.mainThread())
              .subscribeOn(Schedulers.io())
        }
        .subscribe(
            { questions -> populateView(questions) },
            { error -> Log.e("Uhuu", "Error") },
            { Log.d("Uhuu", "Completed") }
        )
  }



  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.question_list_sorting_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    if (item == null) return false

    when (item.itemId) {
      R.id.menu_score_ascending -> {
        adapter.scoreAscending()
        return true
      }
      R.id.menu_score_descending -> {
        adapter.scoreDescending()
        return true
      }
    }
    return false
  }

  private fun populateView(questions: StackOverflowQuestions?) {
    adapter.setQuestions(questions?.items ?: emptyList())
  }

  override fun onDestroy() {
    super.onDestroy()
    searchInputSubscription?.unsubscribe()
  }

}

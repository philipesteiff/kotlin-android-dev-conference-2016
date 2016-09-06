package com.devconference.stackoverflow

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class QuestionListAdapter(
    context: Context
) : RecyclerView.Adapter<QuestionViewHolder>() {

  private val questions = mutableListOf<StackOverflowQuestion>()
  private val layoutInflater by lazy { LayoutInflater.from(context) }

  override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
    with(questions[position]) {
      holder.titleView.text = title
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): QuestionViewHolder {
    val view = layoutInflater.inflate(R.layout.item_question, parent, false)
    return QuestionViewHolder(view)
  }

  override fun getItemCount() = questions.size

  fun setQuestions(questions: List<StackOverflowQuestion>) {
    this.questions.apply {
      clear()
      addAll(questions)
    }
    notifyDataSetChanged()
  }

  fun scoreAscending() {
    questions.sortBy { it.score }
  }

  fun scoreDescending() {
    questions.sortByDescending { it.score }
  }
}

class QuestionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  val titleView = view.findViewById(R.id.question_title) as TextView
}

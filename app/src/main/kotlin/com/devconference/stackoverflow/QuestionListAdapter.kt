package com.devconference.stackoverflow

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class QuestionListAdapter(
    context: Context,
    val callBack: (StackOverflowQuestion) -> Unit
) : RecyclerView.Adapter<QuestionViewHolder>() {

  var questions = listOf<StackOverflowQuestion>()
    set(elements) {
      field = elements
      notifyDataSetChanged()
    }

  private val layoutInflater by lazy { LayoutInflater.from(context) }

  override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
    with(questions[position]) {
      holder.titleView.text = title
      holder.detailsView.text = "Score: $score | Respostas: $answerCount | Views: $viewCount"

      holder.view.setOnClickListener {
        callBack(this)
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): QuestionViewHolder {
    val view = layoutInflater.inflate(R.layout.item_question, parent, false)
    return QuestionViewHolder(view)
  }

  override fun getItemCount() = questions.size

  fun scoreAscending() {
    questions = questions.sortedBy { it.score }
    notifyDataSetChanged()
  }

  fun scoreDescending() {
    questions = questions.sortedByDescending { it.score }
    notifyDataSetChanged()
  }

  fun showUnanswered() {
    questions = questions.filter { it.answerCount == 0 }
    notifyDataSetChanged()
  }
}

class QuestionViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
  val titleView = view.findViewById(R.id.question_title_text_view) as TextView
  val detailsView = view.findViewById(R.id.question_details_text_view) as TextView
}

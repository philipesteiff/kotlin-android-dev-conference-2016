package com.devconference.stackoverflow

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import rx.Observable
import rx.subjects.BehaviorSubject

fun EditText.onTextChanged(): Observable<String> {
  val subject = BehaviorSubject.create<String>()
  this.addTextChangedListener(object : TextWatcher {
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
      subject.onNext(s.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun afterTextChanged(s: Editable) {
    }
  })
  return subject
}


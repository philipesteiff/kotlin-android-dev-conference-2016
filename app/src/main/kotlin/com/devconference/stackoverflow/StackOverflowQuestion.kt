package com.devconference.stackoverflow

import com.google.gson.annotations.SerializedName

data class StackOverflowQuestion
(
      @SerializedName("title") val title: String,
      @SerializedName("link") val link: String,
      @SerializedName("view_count") val viewCount: Int,
      @SerializedName("answer_count") val answerCount: Int,
      @SerializedName("score") val score: Int,
      @SerializedName("creation_date") val creationDate: Int
) {

  override fun toString(): String {
    return title
  }
}
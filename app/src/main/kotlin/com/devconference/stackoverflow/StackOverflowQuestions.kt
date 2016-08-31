package com.devconference.stackoverflow

import com.google.gson.annotations.SerializedName

data class StackOverflowQuestions(
    @SerializedName("items") val items: List<StackOverflowQuestion> = emptyList()
)
package com.example.tinkofftest.model

import com.google.gson.annotations.SerializedName

class ResponseEntry(
  @SerializedName("description")
  val description: String,
  @SerializedName("gifURL")
  val gifURL: String
)
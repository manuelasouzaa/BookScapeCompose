package br.com.bookscapecompose.web.json.bookComplete

import com.google.gson.annotations.SerializedName

data class SearchInfo(
    @SerializedName("textSnippet")
    val textSnippet: String?
)

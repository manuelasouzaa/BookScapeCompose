package br.com.bookscapecompose.web.json.bookComplete

import com.google.gson.annotations.SerializedName

data class SaleInfo(

    @SerializedName("country")
    val country: String?,

    @SerializedName("saleability")
    val saleability: String?,

    @SerializedName("isEbook")
    val isEbook: Boolean?,
)
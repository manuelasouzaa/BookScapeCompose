package br.com.bookscapecompose.web.json 

import br.com.bookscapecompose.web.json.bookComplete.AccessInfo
import br.com.bookscapecompose.web.json.bookComplete.BookVolumeInfo
import br.com.bookscapecompose.web.json.bookComplete.SaleInfo
import br.com.bookscapecompose.web.json.bookComplete.SearchInfo
import com.google.gson.annotations.SerializedName

data class BookComplete(
    @SerializedName("kind")
    val kind: String,

    @SerializedName("id")
    val id: String,

    @SerializedName("etag")
    val etag: String,

    @SerializedName("selfLink")
    val selfLink: String?,

    @SerializedName("volumeInfo")
    val volumeInfo: BookVolumeInfo?,

    @SerializedName("saleInfo")
    val saleInfo: SaleInfo?,

    @SerializedName("accessInfo")
    val accessInfo: AccessInfo?,

    @SerializedName("searchInfo")
    val searchInfo: SearchInfo?
)

package br.com.bookscapecompose.web.json.bookComplete

import br.com.bookscapecompose.web.json.bookComplete.bookVolumeInfo.ImageLinks
import br.com.bookscapecompose.web.json.bookComplete.bookVolumeInfo.IndustryIdentifier
import br.com.bookscapecompose.web.json.bookComplete.bookVolumeInfo.PanelizationSummary
import br.com.bookscapecompose.web.json.bookComplete.bookVolumeInfo.ReadingMode
import com.google.gson.annotations.SerializedName

data class BookVolumeInfo(
    @SerializedName("title")
    val title: String,

    @SerializedName("subtitle")
    val subtitle: String?,

    @SerializedName("authors")
    val authors: List<String>?,

    @SerializedName("publisher")
    val publisher: String?,

    @SerializedName("publishedDate")
    val publishedDate: String?,

    @SerializedName("description")
    val description: String?,

    @SerializedName("industryIdentifiers")
    val industryIdentifiers: List<IndustryIdentifier>?,

    @SerializedName("readingModes")
    val readingModes: ReadingMode?,

    @SerializedName("pageCount")
    val pageCount: Int?,

    @SerializedName("printType")
    val printType: String?,

    @SerializedName("categories")
    val categories: List<String>?,

    @SerializedName("maturityRating")
    val maturityRating: String?,

    @SerializedName("allowAnonLogging")
    val allowAnonLogging: Boolean?,

    @SerializedName("contentVersion")
    val contentVersion: String?,

    @SerializedName("panelizationSummary")
    val panelizationSummary: PanelizationSummary?,

    @SerializedName("imageLinks")
    val imageLinks: ImageLinks?,

    @SerializedName("language")
    val language: String?,

    @SerializedName("previewLink")
    val previewLink: String?,

    @SerializedName("infoLink")
    val infoLink: String?,

    @SerializedName("canonicalVolumeLink")
    val canonicalVolumeLink: String?
)
package br.com.bookscapecompose.web.retrofit

import br.com.bookscapecompose.web.json.GoogleApiAnswer
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("books/v1/volumes")
    suspend fun getBooks(
        @Query("q") search: String,
    ): GoogleApiAnswer
}
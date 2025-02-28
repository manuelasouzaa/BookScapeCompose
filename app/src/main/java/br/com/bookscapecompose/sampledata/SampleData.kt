package br.com.bookscapecompose.sampledata

import br.com.bookscapecompose.model.Book

val book1: Book = Book(
    id = "id",
    title = "Percy Jackson and the Olympians - The Lightning Thief",
    authors = "Rick Riordan",
    description = null,
    image = "http://books.google.com/books/content?id=V6A0zgEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api",
    link = ""
)

val sampleList: List<Book> = listOf(
    book1, book1, book1, book1, book1, book1, book1, book1,
)

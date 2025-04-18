package com.github.tkojitu.wagtailjournal

interface ServiceFactory {
    fun create(co: Container?): Any
}

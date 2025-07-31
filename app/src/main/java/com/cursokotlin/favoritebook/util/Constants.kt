package com.cursokotlin.favoritebook.util

import com.cursokotlin.favoritebook.BuildConfig

class Constants {
    companion object {
      const val BASE_URL = "https://www.googleapis.com/books/v1/"
      const val ENDPOINT = "volumes"
      const val API_KEY = BuildConfig.API_KEY
    }
}
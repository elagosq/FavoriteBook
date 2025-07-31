package com.cursokotlin.favoritebook.navigation

import kotlinx.serialization.Serializable

@Serializable
object Splash

@Serializable
object Home

@Serializable
object Favorite

@Serializable
object Search

@Serializable
data class Detail(val id : String)

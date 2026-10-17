package com.example.simplenotesapp.view

import kotlinx.serialization.Serializable


@Serializable
sealed interface AppRoute

@Serializable
data object Home : AppRoute

@Serializable
data class NoteDetails(val id: String)

@Serializable
data object Folders : AppRoute


@Serializable
data object Pinned : AppRoute


@Serializable
data object Tags : AppRoute


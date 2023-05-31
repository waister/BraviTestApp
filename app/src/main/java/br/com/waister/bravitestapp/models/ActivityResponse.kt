package br.com.waister.bravitestapp.models

data class ActivityResponse(
    val activity: String,
    val type: String,
    val participants: Int,
    val price: Float,
    val link: String,
    val key: String,
    val accessibility: Float
)
package com.clandestinestudio.arfurniture.Model

import java.io.Serializable

data class FavoriteFurniture(
    val id: String,
    val name: String,
    val category: String,
    val image_url: String,
    val dimensions: String,
    val description: String,
    val itemFolderName: String,
) : Serializable

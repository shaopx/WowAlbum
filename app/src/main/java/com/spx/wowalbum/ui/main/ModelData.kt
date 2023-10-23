package com.spx.wowalbum.ui.main

import java.io.Serializable

data class ModelList(
    var models: List<ModelData> = listOf(),
)

data class ModelData(
    var avatar_url: String = "",
    var desc: String = "",
    var link: String = "",
    var name: String = "",
    var products: List<Product> = listOf(),
    var state: Int = 0
): Serializable

data class Product(
    var cover_url: String = "",
    var product_desc: String = "",
    var photos: MutableList<String> = mutableListOf<String>(),
): Serializable
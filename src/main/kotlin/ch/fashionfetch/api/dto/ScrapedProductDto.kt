package ch.fashionfetch.api.dto

data class ScrapedProductDto(
    val article_id: String,
    val article_name: String,
    val price: Float,
    val color: String,
    val description: String?,
    val category: String,
    val type: String,
    val type_category: String,
    val brand: String,
    val images: Map<String, String>,
    val url: String
)
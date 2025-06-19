package ch.fashionfetch.api.mapper

import ch.fashionfetch.api.dto.ScrapedProductDto
import ch.fashionfetch.api.model.*
import java.util.*

object ProductMapper {
    fun toEntity(dto: ScrapedProductDto): ProductEntity {
        return ProductEntity(
            title = dto.article_name,
            description = dto.description,
            price = dto.price,
            imageLinks = dto.images.values.toSet(),
            brand = dto.brand,
            colors = dto.color,
            type = dto.type_category,
            gender = Gender.from(dto.category),
            url = dto.url
        )
    }
}
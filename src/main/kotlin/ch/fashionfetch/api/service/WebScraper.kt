package ch.fashionfetch.api.service

import ch.fashionfetch.api.dto.ScrapedProductDto
import ch.fashionfetch.api.mapper.ProductMapper
import ch.fashionfetch.api.model.ProductEntity
import ch.fashionfetch.api.repository.ProductRepository
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class WebScraper {

    private val baseUrl = "https://www.acnestudios.com"

    @Autowired
    lateinit var productRepository: ProductRepository


    fun fetchAllMenProducts(): List<ScrapedProductDto> {
        val url = "$baseUrl/ch/en/man/clothing/?sz=9999"
        val doc = Jsoup.connect(url)
            .userAgent("Mozilla/5.0")
            .get()

        val totalCount = Regex("Showing \\d+ of (\\d+)").find(doc.text())
            ?.groupValues?.get(1)?.toIntOrNull() ?: -1

        val products = extractProductsFromPage(doc)
        val saved = mutableListOf<ProductEntity>()
        println("Parsed ${products.size} men's products.")
        for (dto in products) {
            if (!productRepository.existsByUrl(dto.url)) {
                val entity = ProductMapper.toEntity(dto)
                saved += productRepository.save(entity)
            }
        }
        println("Saved ${saved.size} new men's products.")
        return products
    }

    fun fetchAllWomenProducts(): List<ScrapedProductDto> {
        val url = "$baseUrl/ch/en/woman/clothing/?sz=9999"
        val doc = Jsoup.connect(url)
            .userAgent("Mozilla/5.0")
            .get()

        val totalCount = Regex("Showing \\d+ of (\\d+)").find(doc.text())
            ?.groupValues?.get(1)?.toIntOrNull() ?: -1

        val products = extractProductsFromPage(doc)
        val saved = mutableListOf<ProductEntity>()
        println("Parsed ${products.size} women's products.")
        for (dto in products) {
            if (!productRepository.existsByUrl(dto.url)) {
                val entity = ProductMapper.toEntity(dto.copy(type = "women")) // Set type tag
                saved += productRepository.save(entity)
            }
        }
        println("Saved ${saved.size} new women's products.")
        return products
    }


    private fun extractProductsFromPage(doc: Document): List<ScrapedProductDto> {
        return doc.select(".product-tile").mapNotNull { element ->
            val gaData = element.attr("data-ga4-item")

            val articleId = Regex("\"item_id\"\\s*:\\s*\"(.*?)\"")
                .find(gaData)?.groupValues?.get(1) ?: "UNKNOWN-${UUID.randomUUID()}"

            val name = Regex("\"item_name\"\\s*:\\s*\"(.*?)\"")
                .find(gaData)?.groupValues?.get(1)
                ?: element.selectFirst(".product-name")?.text()?.trim()
                ?: return@mapNotNull null

            val price = Regex("\"price\"\\s*:\\s*(\\d+(\\.\\d+)?)")
                .find(gaData)?.groupValues?.get(1)?.toFloatOrNull() ?: 0f

            val color = Regex("\"color\"\\s*:\\s*\"(.*?)\"")
                .find(gaData)?.groupValues?.get(1).toString()

            val category = Regex("\"item_category\"\\s*:\\s*\"(.*?)\"")
                .find(gaData)?.groupValues?.get(1) ?: "unknown"
            val category2 = Regex("\"item_category2\"\\s*:\\s*\"(.*?)\"")
                .find(gaData)?.groupValues?.get(1) ?: "unknown"
            val category3 = Regex("\"item_category3\"\\s*:\\s*\"(.*?)\"")
                .find(gaData)?.groupValues?.get(1) ?: "unknown"

            val productUrl = element.selectFirst("a.tile__link")?.attr("href")
                ?.let { if (it.startsWith("http")) it else baseUrl + it } ?: return@mapNotNull null

            val imageUrls = element.select(".horizontal-slider__item img")
                .mapNotNull {
                    it.attr("data-srcset")
                        .ifBlank { it.attr("srcset") }
                        .ifBlank { it.attr("data-src") }
                        .ifBlank { it.attr("src") }
                }
                .map { it.split(" ").first() }
                .filter { it.startsWith("http") || it.startsWith("//") }
                .map { if (it.startsWith("//")) "https:$it" else it }
                .distinct()
                .mapIndexed { idx, url -> (idx + 1).toString() to url }
                .toMap()

            ScrapedProductDto(
                article_id = articleId,
                article_name = name,
                price = price,
                color = color,
                description = null,
                category = category.lowercase(),
                type = category2.lowercase(),
                type_category = category3,
                brand = "Acne Studios",
                images = imageUrls,
                url = productUrl
            )
        }
    }
}
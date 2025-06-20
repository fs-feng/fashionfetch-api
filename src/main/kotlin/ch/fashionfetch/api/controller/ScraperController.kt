package ch.fashionfetch.api.controller

import ch.fashionfetch.api.dto.ScrapedProductDto
import ch.fashionfetch.api.service.WebScraper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/scrape")
class ScraperController(
    private val webScraper: WebScraper
) {

    // scrape all men clothing
    @GetMapping("/acne/men/all")
    fun getAllMenProducts(): List<ScrapedProductDto> = webScraper.fetchAllMenProducts()

    // scrape all women clothing
    @GetMapping("/acne/women/all")
    fun getAllWomenProducts(): List<ScrapedProductDto> = webScraper.fetchAllWomenProducts()

    // scrape and save all gender clothing
    @GetMapping("/acne/all")
    fun getAllProducts(): List<ScrapedProductDto> {
        val womenProducts = webScraper.fetchAllWomenProducts()
        val menProducts = webScraper.fetchAllMenProducts()
        return womenProducts + menProducts
    }
}
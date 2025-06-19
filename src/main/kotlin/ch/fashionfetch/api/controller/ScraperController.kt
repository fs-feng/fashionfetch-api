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

    // only scrape first page (32 products)
    @GetMapping("/acne/men")
    fun getMenPage(): List<ScrapedProductDto> = webScraper.fetchSinglePage()

    // scrape all men clothing
    @GetMapping("/acne/men/all")
    fun getAllMenProducts(): List<ScrapedProductDto> = webScraper.fetchAllMenProducts()
}
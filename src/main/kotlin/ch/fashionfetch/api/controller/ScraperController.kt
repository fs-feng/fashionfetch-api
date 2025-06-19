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

    @GetMapping("/acne/men")
    fun getMenPage(): List<ScrapedProductDto> = webScraper.fetchSinglePage()

    @GetMapping("/acne/men/all")
    fun getAllMenProducts(): List<ScrapedProductDto> = webScraper.fetchAllMenProducts()
}
package ch.fashionfetch.api.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
data class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val title: String,

    @Column(columnDefinition = "TEXT")
    val description: String? = null,

    val price: Float,

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = [JoinColumn(name = "product_id")])
    @Column(name = "image_url")
    val imageLinks: Set<String> = emptySet(),

    val brand: String,

    val colors: String,

    val type: String,

    @Enumerated(EnumType.STRING)
    val gender: Gender,

    @Column(name = "product_url", unique = false, nullable = true)
    val url: String
)
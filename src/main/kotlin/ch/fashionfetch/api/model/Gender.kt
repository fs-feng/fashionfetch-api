package ch.fashionfetch.api.model

enum class Gender {
    MALE, FEMALE, UNISEX;

    companion object {
        fun from(raw: String?): Gender = when (raw?.lowercase()) {
            "man", "men" -> MALE
            "woman", "women" -> FEMALE
            else -> UNISEX
        }
    }
}
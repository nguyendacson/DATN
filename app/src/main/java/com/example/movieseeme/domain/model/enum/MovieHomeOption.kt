package com.example.movieseeme.domain.model.enum

enum class MovieHomeOption(val slug: String, val nameOption: String) {
    CARTOON("hoathinh", "Hoạt Hình"),
    SERIES("series", "Phim Bộ"),
    SINGLE("single", "Phim Lẻ"),
    SHOWS("tvshows", "Truyền Hình"),
    THEM("more", "Khác")
}

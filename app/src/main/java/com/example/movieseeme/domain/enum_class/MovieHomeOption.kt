package com.example.movieseeme.domain.enum_class

enum class MovieHomeOption(val slug: String, val nameOption: String) {
    CARTOON("hoathinh", "Hoạt Hình"),
    SERIES("series", "Phim Bộ"),
    SINGLE("single", "Phim Lẻ"),
    SHOWS("tvshows", "Truyền Hình"),
    THEM("more", "Khác")
}

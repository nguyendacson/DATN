package com.example.movieseeme.domain.model.enum

import com.example.movieseeme.R

enum class MovieHotOption(val key: Int, val icon: Int, val valueOption: String) {
    HOT(1,R.drawable.icon_hot, "Phim Hot"),
    FAVORITE(2,R.drawable.icon_heart, "Dành Cho Bạn"),
    UPLOAD(3,R.drawable.icon_upload, "Mới Update"),
}

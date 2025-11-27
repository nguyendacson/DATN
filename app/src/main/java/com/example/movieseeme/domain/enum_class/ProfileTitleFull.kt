package com.example.movieseeme.domain.enum_class

enum class ProfileTitleFull(val slug: String, val nameTitle: String) {
    LIKE("like", "Đã Like"),
    TRAILER("trailer", "Trailer đã xem"),
    MY_LIST("my_list", "Danh sách yêu thích"),
    LIST_FOR_YOU("list_for_you", "Dành cho bạn"),
}

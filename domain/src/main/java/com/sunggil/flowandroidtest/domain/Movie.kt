package com.sunggil.flowandroidtest.domain

import java.io.Serializable

/**
 * "title": "어린 새엄마:다 <b>해</b>줄게",
"link": "https://movie.naver.com/movie/bi/mi/basic.nhn?code=215744",
"image": "https://ssl.pstatic.net/imgmovie/mdi/mit110/2157/215744_P01_105036.jpg",
"subtitle": "",
"pubDate": "2022",
"director": "디렉터 O|",
"actor": "",
"userRating": "0.00"
 */
data class Movie(
    var id : String,
    var title : String,
    var link : String,
    var image : String,
    var pubDate : String,
    var userRating : String,
) : Serializable
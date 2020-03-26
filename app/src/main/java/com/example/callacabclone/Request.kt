package com.example.callacabclone

import com.parse.ParseGeoPoint

data class Request(val username: String, val location: ParseGeoPoint) {}
package com.clara.client.model.request

import com.google.gson.annotations.SerializedName

data class FindClientUserNewRequest(
    @SerializedName("emailId") val emailIdList: List<String>? = listOf()
)

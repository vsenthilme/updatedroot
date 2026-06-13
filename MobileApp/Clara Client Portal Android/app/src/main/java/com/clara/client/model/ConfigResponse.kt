package com.clara.client.model

import com.google.gson.annotations.SerializedName

data class ConfigResponse(
    @SerializedName("status") private var status: String? = null
)

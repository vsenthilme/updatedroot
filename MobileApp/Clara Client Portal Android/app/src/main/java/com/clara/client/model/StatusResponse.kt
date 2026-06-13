package com.clara.client.model

import com.google.gson.annotations.SerializedName

data class StatusResponse(
    @SerializedName("statusId") var statusId: Int? = null,
    @SerializedName("status") var status: String? = null
)

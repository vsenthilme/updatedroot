package com.clara.client.model

import com.google.gson.annotations.SerializedName

data class MatterIdResponse(
    @SerializedName("matterNumber") var matterNumber: String? = null
)

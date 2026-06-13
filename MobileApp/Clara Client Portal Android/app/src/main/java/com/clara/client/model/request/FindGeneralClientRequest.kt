package com.clara.client.model.request

import com.google.gson.annotations.SerializedName

data class FindClientGeneralEmailRequest(
    @SerializedName("emailId") val emailId: String? = null
)
data class FindGeneralClientMobileRequest(
    @SerializedName("contactNumber") val contactNumber: String? = null
)

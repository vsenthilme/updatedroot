package com.clara.client

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.clara.client.model.SetupServiceAuthResponse

open class BaseViewModel : ViewModel() {
    var authTokenMutableLiveData: MutableLiveData<SetupServiceAuthResponse> = MutableLiveData()
}
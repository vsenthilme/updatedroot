package com.clara.client

import androidx.fragment.app.Fragment
import com.clara.client.utils.CommonUtils
import com.clara.client.utils.PreferenceHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseFragment : Fragment() {
    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    @Inject
    lateinit var commonUtils: CommonUtils
}
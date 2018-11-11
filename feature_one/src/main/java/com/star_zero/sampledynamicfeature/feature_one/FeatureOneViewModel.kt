package com.star_zero.sampledynamicfeature.feature_one

import androidx.lifecycle.ViewModel
import com.star_zero.sampledynamicfeature.data.repository.SampleRepository
import javax.inject.Inject

class FeatureOneViewModel @Inject constructor(
    private val sampleRepository: SampleRepository
) : ViewModel() {

    fun getData(): String {
        return sampleRepository.getData()
    }
}

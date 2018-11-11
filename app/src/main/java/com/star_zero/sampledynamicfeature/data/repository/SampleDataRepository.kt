package com.star_zero.sampledynamicfeature.data.repository

import android.content.Context

class SampleDataRepository(private val context: Context) : SampleRepository {
    override fun getData(): String {
        return "Package name: ${context.packageName}, currentTimeMillis: ${System.currentTimeMillis()}"
    }
}

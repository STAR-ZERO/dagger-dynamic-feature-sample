package com.star_zero.sampledynamicfeature.di

import android.app.Activity
import androidx.fragment.app.Fragment
import com.star_zero.sampledynamicfeature.App
import dagger.android.DispatchingAndroidInjector

interface BaseModuleInjector {

    fun inject(app: App)

    fun activityInjector(): DispatchingAndroidInjector<Activity>

    fun fragmentInjector(): DispatchingAndroidInjector<Fragment>
}

package com.star_zero.sampledynamicfeature.feature_two.di

import android.app.Activity
import androidx.annotation.Keep
import androidx.fragment.app.Fragment
import com.star_zero.sampledynamicfeature.App
import com.star_zero.sampledynamicfeature.di.BaseModuleInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

@Keep
class FeatureTwoInjector: BaseModuleInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun inject(app: App) {
        DaggerFeatureTwoComponent
            .builder()
            .appComponent(app.appComponent)
            .build()
            .inject(this)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return activityInjector
    }

    override fun fragmentInjector(): DispatchingAndroidInjector<Fragment> {
        return fragmentInjector
    }
}

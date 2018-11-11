package com.star_zero.sampledynamicfeature.feature_two.di

import com.star_zero.sampledynamicfeature.di.AppComponent
import com.star_zero.sampledynamicfeature.di.ModuleScope
import com.star_zero.sampledynamicfeature.di.ViewModelModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@ModuleScope
@Component(
    dependencies = [
        AppComponent::class
    ],
    modules = [
        AndroidSupportInjectionModule::class,
        FeatureFragmentModule::class,
        ViewModelModule::class
    ]
)
interface FeatureTwoComponent {
    fun inject(injector: FeatureTwoInjector)
}
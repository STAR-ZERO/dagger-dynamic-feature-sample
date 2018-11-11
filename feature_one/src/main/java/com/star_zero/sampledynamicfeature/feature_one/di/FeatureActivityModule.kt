package com.star_zero.sampledynamicfeature.feature_one.di

import androidx.lifecycle.ViewModel
import com.star_zero.sampledynamicfeature.di.ViewModelKey
import com.star_zero.sampledynamicfeature.feature_one.FeatureOneActivity
import com.star_zero.sampledynamicfeature.feature_one.FeatureOneViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FeatureActivityModule {

    @ContributesAndroidInjector(modules = [FeatureOneActivityModule::class])
    abstract fun contributeFeatureOneActivity(): FeatureOneActivity

    @Module
    abstract class FeatureOneActivityModule {
        @Binds
        @IntoMap
        @ViewModelKey(FeatureOneViewModel::class)
        abstract fun bindFeatureOneViewModel(viewModel: FeatureOneViewModel): ViewModel
    }
}

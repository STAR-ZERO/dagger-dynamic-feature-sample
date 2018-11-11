package com.star_zero.sampledynamicfeature.feature_two.di

import androidx.lifecycle.ViewModel
import com.star_zero.sampledynamicfeature.di.ViewModelKey
import com.star_zero.sampledynamicfeature.feature_two.FeatureTwoFragment
import com.star_zero.sampledynamicfeature.feature_two.FeatureTwoViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FeatureFragmentModule {

    @ContributesAndroidInjector(modules = [FeatureTwoFragmentModule::class])
    abstract fun contributeFeatureTwoFragment(): FeatureTwoFragment

    @Module
    abstract class FeatureTwoFragmentModule {
        @Binds
        @IntoMap
        @ViewModelKey(FeatureTwoViewModel::class)
        abstract fun bindFeatureTwoViewModel(viewModel: FeatureTwoViewModel): ViewModel
    }

}

package com.star_zero.sampledynamicfeature.di

import androidx.lifecycle.ViewModel
import com.star_zero.sampledynamicfeature.MainActivity
import com.star_zero.sampledynamicfeature.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @Module
    abstract class MainActivityModule {
        @Binds
        @IntoMap
        @ViewModelKey(MainViewModel::class)
        abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
    }
}

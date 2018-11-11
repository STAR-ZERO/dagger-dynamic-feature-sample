package com.star_zero.sampledynamicfeature.di

import com.star_zero.sampledynamicfeature.App
import com.star_zero.sampledynamicfeature.data.repository.SampleRepository
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ViewModelModule::class,
        ActivityModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    // For feature module component
    fun sampleRepository(): SampleRepository

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>() {
        abstract override fun build(): AppComponent
    }
}

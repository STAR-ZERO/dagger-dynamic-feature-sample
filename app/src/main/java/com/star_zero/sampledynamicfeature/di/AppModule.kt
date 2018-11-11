package com.star_zero.sampledynamicfeature.di

import android.content.Context
import com.star_zero.sampledynamicfeature.App
import com.star_zero.sampledynamicfeature.data.repository.SampleDataRepository
import com.star_zero.sampledynamicfeature.data.repository.SampleRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(app: App): Context {
        return app
    }

    @Singleton
    @Provides
    fun provideSampleRepository(context: Context): SampleRepository {
        return SampleDataRepository(context)
    }
}
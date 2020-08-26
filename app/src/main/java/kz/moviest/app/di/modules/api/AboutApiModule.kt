package kz.moviest.app.di.modules.api

import dagger.Module
import dagger.Provides
import kz.moviest.app.network.api.MoviesApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AboutApiModule {

    @Provides
    @Singleton
    fun provideActionsApi(retrofit: Retrofit): MoviesApi {
        return retrofit.create(MoviesApi::class.java!!)
    }

}
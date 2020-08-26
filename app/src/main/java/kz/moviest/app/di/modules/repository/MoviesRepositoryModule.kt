package kz.moviest.app.di.modules.repository

import dagger.Binds
import dagger.Module
import kz.moviest.app.data.repository.movies.MovieRepository
import kz.moviest.app.data.repository.movies.MovieRepositoryImpl
import javax.inject.Singleton

@Module
abstract class MoviesRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMoviesRepository(impl: MovieRepositoryImpl): MovieRepository

}
package kz.moviest.app.di.contributes_android_injector.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kz.moviest.app.ui.movies.MoviesFragment
import kz.moviest.app.ui.movies.filter.MovieFilterFragment
import kz.moviest.app.ui.movies.movie.MovieDetailFragment

@Module
abstract class MoviesFragmentsBuildersModule {

    @ContributesAndroidInjector
    internal abstract fun moviesFragment(): MoviesFragment

    @ContributesAndroidInjector
    internal abstract fun movieFragment(): MovieDetailFragment

    @ContributesAndroidInjector
    internal abstract fun movieFilterFragment(): MovieFilterFragment
}
package kz.moviest.app.di.view_model

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kz.moviest.app.di.view_model.base.ViewModelKey
import kz.moviest.app.ui.movies.MoviesViewModel
import kz.moviest.app.ui.movies.filter.MovieFilterViewModel
import kz.moviest.app.ui.movies.filter.SharedMovieFilterViewModel
import kz.moviest.app.ui.movies.movie.MovieDetailViewModel

@Suppress("unused")
@Module
abstract class MoviesViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    abstract fun bindMoviesViewModel(viewModel: MoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun bindMovieDetailViewModel(viewModel: MovieDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieFilterViewModel::class)
    abstract fun bindMovieFilterViewModel(viewModel: MovieFilterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SharedMovieFilterViewModel::class)
    abstract fun bindSharedMovieFilterViewModel(viewModel: SharedMovieFilterViewModel): ViewModel

}
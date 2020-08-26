package kz.moviest.app.di.view_model

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kz.moviest.app.di.view_model.base.ViewModelKey
import kz.moviest.app.ui.splash.SplashViewModel

@Suppress("unused")
@Module
abstract class SplashViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

}
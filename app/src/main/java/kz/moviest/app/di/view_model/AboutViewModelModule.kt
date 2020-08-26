package kz.moviest.app.di.view_model

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kz.moviest.app.di.view_model.base.ViewModelKey
import kz.moviest.app.ui.about.AboutViewModel

@Suppress("unused")
@Module
abstract class AboutViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AboutViewModel::class)
    abstract fun bindAboutViewModel(viewModel: AboutViewModel): ViewModel

}
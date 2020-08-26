package kz.moviest.app.di.modules

import dagger.Module
import kz.moviest.app.di.view_model.AboutViewModelModule
import kz.moviest.app.di.view_model.ActivityViewModelModule
import kz.moviest.app.di.view_model.MoviesViewModelModule
import kz.moviest.app.di.view_model.SplashViewModelModule

@Module(
    includes = [
        ActivityViewModelModule::class,
        SplashViewModelModule::class,
        AboutViewModelModule::class,
        MoviesViewModelModule::class
    ]
)
class ViewModelModule {
}
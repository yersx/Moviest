package kz.moviest.app.di.modules

import dagger.Module
import kz.moviest.app.di.modules.common.NetworkModule
import kz.moviest.app.di.modules.common.PreferenceModule
import kz.moviest.app.di.modules.common.UtilsModule
import kz.moviest.app.di.view_model.base.ViewModelFactoryModule

@Module(
    includes = [
        NetworkModule::class,
        PreferenceModule::class,
        UtilsModule::class,

        //api
        ApiModule::class,

        //repository
        RepositoryModule::class,

        //
        ViewModelFactoryModule::class,
        ViewModelModule::class
    ]
)
class AppModule {
}
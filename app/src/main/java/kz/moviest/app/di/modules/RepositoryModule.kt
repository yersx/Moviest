package kz.moviest.app.di.modules

import dagger.Module
import kz.moviest.app.di.modules.repository.MoviesRepositoryModule

@Module(
    includes = [
        MoviesRepositoryModule::class
    ]
)
class RepositoryModule {
}
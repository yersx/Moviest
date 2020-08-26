package kz.moviest.app.di.modules

import dagger.Module
import kz.moviest.app.di.modules.api.AboutApiModule

@Module(
    includes = [
        AboutApiModule::class
    ]
)
class ApiModule {
}
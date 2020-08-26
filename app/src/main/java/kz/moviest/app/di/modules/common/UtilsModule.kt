package kz.moviest.app.di.modules.common

import dagger.Module
import dagger.Provides
import kz.moviest.app.data.preferences.Preferences
import kz.moviest.app.network.utils.HeaderUtils
import javax.inject.Singleton

@Module
class UtilsModule {

    @Provides
    @Singleton
    fun provideHeaderUtils(preferences: Preferences) = HeaderUtils(preferences)

}
package kz.moviest.app.di.modules.common

import android.app.Application
import dagger.Module
import dagger.Provides
import kz.moviest.app.data.preferences.Preferences
import kz.moviest.app.data.preferences.PreferencesImpl

import javax.inject.Singleton

@Module
class PreferenceModule {

    @Provides
    @Singleton
    fun providePreferences(app: Application): Preferences {
        return PreferencesImpl(app)
    }

}
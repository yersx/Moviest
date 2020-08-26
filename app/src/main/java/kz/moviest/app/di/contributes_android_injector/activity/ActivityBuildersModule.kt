package kz.moviest.app.di.contributes_android_injector.activity

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kz.moviest.app.di.contributes_android_injector.fragment.AboutFragmentsBuildersModule
import kz.moviest.app.di.contributes_android_injector.fragment.MoviesFragmentsBuildersModule
import kz.moviest.app.di.contributes_android_injector.fragment.SplashFragmentsBuildersModule
import kz.moviest.app.ui.activities.main.MainActivity
import kz.moviest.app.ui.activities.splash.SplashActivity

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(
        modules = [
            SplashFragmentsBuildersModule::class
        ]
    )
    internal abstract fun splashActivity(): SplashActivity

    @ContributesAndroidInjector(
        modules = [
            AboutFragmentsBuildersModule::class,
            MoviesFragmentsBuildersModule::class
        ]
    )
    internal abstract fun mainActivity(): MainActivity

}
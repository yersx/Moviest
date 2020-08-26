package kz.moviest.app.di.contributes_android_injector.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import kz.moviest.app.ui.about.AboutFragment

@Module
abstract class AboutFragmentsBuildersModule {

    @ContributesAndroidInjector
    internal abstract fun aboutFragment(): AboutFragment

}
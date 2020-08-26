package kz.moviest.app.utils.locale

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import kz.moviest.app.data.enums.api.LanguageEnums
import kz.moviest.app.data.preferences.PreferencesImpl
import java.util.*

//http://developine.com/android-app-language-change-localization-programatically-kotlin-example/
object LocaleUtils {

    fun setLocale(context: Context?): Context? {
        return if (context != null) {
            val preferences = PreferencesImpl(context)

            if (preferences.getLanguage() != null) {
                updateResources(context, preferences.getLanguage()!!)
            } else {
                context
            }

        } else {
            context
        }
    }

    inline fun setNewLocale(context: Context, language: String) {
        updateResources(context, language)
    }

    fun updateResources(context: Context, language: String): Context {
        var contextFun = context

        var locale = Locale(getSystemLanguage(language))
        Locale.setDefault(locale)

        var resources = context.resources
        var configuration = Configuration(resources.configuration)

        if (Build.VERSION.SDK_INT >= 17) {
            configuration.setLocale(locale)
            contextFun = context.createConfigurationContext(configuration)
        } else {
            configuration.locale = locale
            resources.updateConfiguration(configuration, resources.getDisplayMetrics())
        }

        return contextFun
    }

    private fun getSystemLanguage(language: String): String {
        return when (language) {
            LanguageEnums.KZ.apiId -> {
                LanguageEnums.KZ.systemId
            }
            LanguageEnums.RU.apiId -> {
                LanguageEnums.RU.systemId
            }
            else -> {
                LanguageEnums.DEFAULT.systemId
            }
        }
    }

}
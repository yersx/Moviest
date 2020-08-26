package kz.moviest.app.data.preferences

import android.content.Context
import android.content.SharedPreferences

class PreferencesImpl(
    private val context: Context
) : Preferences {

    companion object {
        private const val UNIQUE_ID = "unique_id"
        private const val LANGUAGE = "language"
        private const val APP_TOKEN = "app_token"
    }

    private lateinit var shPr: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    init {
        shPr = context.getSharedPreferences("PreferencesImpl", Context.MODE_PRIVATE)
        editor = shPr.edit()
    }

    /**
     * DEVICE UNIQUE ID
     */
    override fun setUniqueID(uniqueID: String?) {
        editor.putString(UNIQUE_ID, uniqueID)
        editor.commit()
    }

    override fun getUniqueID(): String? = shPr.getString(UNIQUE_ID, null)

    /**
     * LANGUAGE
     */
    override fun setLanguage(language: String) {
        editor.putString(LANGUAGE, language)
        editor.commit()
    }

    override fun getLanguage(): String? {
        return shPr.getString(LANGUAGE, null)
    }

    /**
     *
     */
    override fun setAppToken(appToken: String) {
        editor.putString(APP_TOKEN, appToken)
        editor.commit()
    }

    override fun getAppToken(): String? {
        return shPr.getString(APP_TOKEN, null)
    }

    override fun removeAppToken() {
        editor
            .remove(APP_TOKEN)
            .commit()
    }

}
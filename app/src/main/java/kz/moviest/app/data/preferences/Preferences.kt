package kz.moviest.app.data.preferences

interface Preferences {

    /**
     *
     */
    fun setUniqueID(uniqueID: String?)

    fun getUniqueID(): String?

    fun setLanguage(language: String)

    fun getLanguage(): String?

    /**
     *
     */
    fun setAppToken(appToken: String)

    fun getAppToken(): String?

    fun removeAppToken()

}
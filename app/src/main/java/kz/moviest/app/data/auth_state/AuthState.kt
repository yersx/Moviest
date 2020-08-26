package kz.moviest.app.data.auth_state

import kz.moviest.app.data.preferences.Preferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthState
@Inject constructor(
    private val preferences: Preferences
) {

    val isAuthorized: Boolean
        get() = preferences.getAppToken() != null

}
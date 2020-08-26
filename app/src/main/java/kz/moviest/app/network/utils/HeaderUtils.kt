package kz.moviest.app.network.utils

import android.os.Build
import kz.moviest.app.BuildConfig
import kz.moviest.app.data.preferences.Preferences
import javax.inject.Inject

class HeaderUtils
@Inject constructor(
    private val preferences: Preferences
) {

    fun getAccept(): String = "application/json"

    fun getAppToken(): String = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkM2JkMDdhZjg0MDk2ZTgyNzBkNGVlYzIzMzg3ZjRkMiIsInN1YiI6IjVmNDM4MjBjNDFhNTYxMDAzNGQwM2VkYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.lC0sZgD2MNk2_g5ZF-qQSv1c3EcheN3OIse6ZMIP9PM"

}
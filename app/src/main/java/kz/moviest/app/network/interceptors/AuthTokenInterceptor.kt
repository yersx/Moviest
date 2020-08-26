package kz.moviest.app.network.interceptors

import kz.moviest.app.data.consts.Header
import kz.moviest.app.network.utils.HeaderUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthTokenInterceptor(
    private val headerUtils: HeaderUtils
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val request = original.newBuilder()
            .header(Header.CONTENT_TYPE, headerUtils.getAccept())
            .header(Header.APP_TOKEN, headerUtils.getAppToken())
            .method(original.method, original.body)
            .build()

        return chain.proceed(request)
    }

}
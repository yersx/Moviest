package kz.moviest.app.di.modules.common

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dagger.Module
import dagger.Provides
import kz.moviest.app.BuildConfig
import kz.moviest.app.data.enums.app.DatePatternEnums
import kz.moviest.app.network.interceptors.AuthTokenInterceptor
import kz.moviest.app.network.utils.HeaderUtils
import kz.moviest.app.utils.convertStringToDate
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val CONNECTION_TIMEOUT_IN_MS = 60000
        private const val READ_TIMEOUT_IN_MS = 60000
    }

    @Provides
    @Singleton
    fun provideAuthTokenInterceptor(headerUtils: HeaderUtils): AuthTokenInterceptor {
        return AuthTokenInterceptor(headerUtils)
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()

        logging.level = HttpLoggingInterceptor.Level.BODY

        return logging
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        authTokenInterceptor: AuthTokenInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {

        val builder = OkHttpClient.Builder()
            .addInterceptor(authTokenInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(CONNECTION_TIMEOUT_IN_MS.toLong(), TimeUnit.MILLISECONDS)
            .readTimeout(READ_TIMEOUT_IN_MS.toLong(), TimeUnit.MILLISECONDS)

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            //.setDateFormat(DatePatternEnums.GSON_DATE)
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(Date::class.java,
                JsonDeserializer<Date> { json, typeOfT, context ->
                    try {
                        return@JsonDeserializer convertStringToDate(
                            json.asString,
                            DatePatternEnums.YYYYMMDD_BY_DASH_HHMMSS_BY_COLON.id
                        )

                    } catch (e: Exception) {
                        return@JsonDeserializer null
                    }
                })
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }
    
}
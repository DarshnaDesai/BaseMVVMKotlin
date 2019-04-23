package com.basemvvmkotlin.di

import android.arch.persistence.room.Room
import android.content.Context
import com.basemvvmkotlin.BaseMVVMKotlin
import com.basemvvmkotlin.BuildConfig
import com.basemvvmkotlin.data.local.db.AppDatabase
import com.basemvvmkotlin.data.local.prefs.Prefs
import com.basemvvmkotlin.data.remote.ApiService
import com.basemvvmkotlin.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {

    /**
     * Provides the Application context.
     * @return the Application context
     */
    @Provides
    @Reusable
    internal fun provideContext(application: BaseMVVMKotlin): Context = application.applicationContext

    /**
     * Provides the preferences.
     * @param context the context used to instantiate the preference
     * @return the preference object.
     */
    @Provides
    fun providePrefs(context: Context) : Prefs = Prefs.getInstance(context)!!

    /**
     * Provides the database name.
     * @return the database name
     */
    @Provides
    @DatabaseInfo
    internal fun provideDatabaseName(): String = AppConstants.DB_NAME

    /**
     * Provides the AppDatabase instance.
     * @param dbName the db name used to instantiate the AppDatabase
     * @param context the application context
     * @return the Post service implementation.
     */
    @Provides
    @Singleton
    internal fun provideAppDatabase(@DatabaseInfo dbName: String, context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, dbName).fallbackToDestructiveMigration()
                .build()
    }


    /**
     * Provides the Api service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Api service implementation.
     */
    @Provides
    @Reusable
    internal fun provideApiClient(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    internal fun provideRetrofitInterface(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(httpClient)
                .build()
    }

    /**
     * Provides the Http client object.
     * @return the Http client object
     */
    @Provides
    @Reusable
    internal fun providetHttpClient(): OkHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(90, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            /*.addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request: Request = chain.request()
                    val response: Response = chain.proceed(request)
                    if (response.code() == HTTP_UNAUTHORIZED) {
                        //TODO: Logout the user and clear prefs
                        //AppUtils.logoutUser(applicationContext)
                    } else if (response.code() == HTTP_NOT_FOUND) {
                        Log.d("not found", "not found")
                    }
                    return response
                }
            })*/
            .build()

}
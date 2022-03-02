package com.example.reliatest

import android.app.Application
import com.example.reliatest.di.appModule
import com.example.reliatest.di.localModule
import com.example.reliatest.di.remoteModule
import com.example.reliatest.di.repositoryModule
import com.example.reliatest.model.User
import com.example.reliatest.utils.PrefUtil
import com.facebook.stetho.Stetho
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    companion object {
        lateinit var instance: MainApplication
            private set
    }

    private val prefUtil: PrefUtil by inject()

    var user: User? = null
        private set

    var token: String? = null
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this

        Stetho.initializeWithDefaults(this)

        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(localModule, remoteModule, repositoryModule, appModule))
        }
    }

    fun isNetworkConnected(): Boolean {
        return prefUtil.isNetworkConnected()
    }

    fun setCurrentUser(user: User?) {
//        prefUtil.user = user
        this.user = user
    }

    fun setToken(token: String?) {
        this.token = token
    }

//    fun getUser(): User? = prefUtil.user
//
//    fun clearPref() = prefUtil.clearAllData()

}
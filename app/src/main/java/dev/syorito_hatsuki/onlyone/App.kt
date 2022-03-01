package dev.syorito_hatsuki.onlyone

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import dev.syorito_hatsuki.onlyone.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import java.util.prefs.Preferences
import kotlin.reflect.KProperty

class App : Application() {

    override fun onCreate() {
        super.onCreate()



        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(networkModule)
        }
    }
}

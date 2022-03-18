package com.onlyone.androidAPP.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.onlyone.androidAPP.api.user.get_users.response.User
import io.ktor.client.features.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataStore = StoreMain(baseContext)
        val viewModelLogin: LoginViewModel by viewModels()
        val user: User? = null

        lifecycleScope.launchWhenCreated {
            dataStore.getToken.collect { token ->
                if (token != null) {
                    viewModelLogin.testToken(token).catch { e ->
                        if (e is ClientRequestException) {
                            if (e.response.status == HttpStatusCode.Unauthorized) {
                                dataStore.saveToken("")
                            }
                        }
                    }.collect { user ->
                        viewModelLogin.setToken(token).collect {
                            setContent {
                                MainScreenView(user)
                            }
                        }
                    }
                }
            }
        }
        setContent {
            MainScreenView(user)
        }

    }
}

class StoreMain(private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
        val TOKEN_KEY = stringPreferencesKey("token")
    }

    val getToken: Flow<String?> = context.dataStore.data
        .map { preferences ->
            preferences[TOKEN_KEY] ?: ""
        }

    suspend fun saveToken(name: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = name
        }
    }
}


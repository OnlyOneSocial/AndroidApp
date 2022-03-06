package dev.syorito_hatsuki.onlyone.ui.login

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.safetynet.SafetyNet
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import dev.syorito_hatsuki.onlyone.databinding.ActivityLoginBinding
import dev.syorito_hatsuki.onlyone.ui.MainActivity
import io.ktor.client.features.*
import io.ktor.http.*
import kotlinx.coroutines.flow.*

class LoginActivity : AppCompatActivity() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tokenPreferenceID = stringPreferencesKey("token2")
        val intent = Intent(this, MainActivity::class.java)

        val exampleCounterFlow: Flow<String> = baseContext.dataStore.data
            .map { preferences ->
                // No type safety.
                preferences[tokenPreferenceID] ?: ""
            }
        lifecycleScope.launchWhenCreated {
            exampleCounterFlow.collect { token ->
                if (token != "") loginViewModel.testToken(token).catch { e ->
                    when (e) {
                        is ClientRequestException -> {
                            //Log.d(TAG, "ClientRequestException ${e.response.status}")
                            if (e.response.status == HttpStatusCode.Unauthorized) {
                                baseContext.dataStore.edit { settings ->
                                    settings[tokenPreferenceID] = ""
                                }
                            }
                        }
                        else -> Log.d(TAG, "Caught $e")
                    }

                }.collect {
                    intent.putExtra("avatar", it.avatar)
                    intent.putExtra("id", it.id)
                    intent.putExtra("token", token)
                    startActivity(intent)
                }
            }
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }
        }

        login.setOnClickListener {
            SafetyNet.getClient(this)
                .verifyWithRecaptcha("6LepD6geAAAAAGr_UrhHRv4gpdu1ATyX02r-seio")
                .addOnSuccessListener(this, OnSuccessListener { response ->
                    // Indicates communication with reCAPTCHA service was
                    // successful.
                    val userResponseToken = response.tokenResult
                    if (response.tokenResult?.isNotEmpty() == true) {
                        // Validate the user response token using the
                        // reCAPTCHA siteverify API.
                        loading.visibility = View.VISIBLE
                        lifecycleScope.launchWhenCreated {
                            loginViewModel
                                .LoginUser(
                                    username.text.toString(), password.text.toString(),
                                    response.tokenResult!!
                                )
                                .onCompletion {
                                    Log.d(TAG, "onCompletion")
                                }
                                .catch { e ->
                                    when (e) {
                                        is ClientRequestException -> {
                                            Log.d(
                                                TAG,
                                                "ClientRequestException ${e.response.status}"
                                            )
                                        }
                                        else -> Log.d(TAG, "Caught $e")
                                    }

                                }
                                .collect {
                                    baseContext.dataStore.edit { settings ->
                                        settings[tokenPreferenceID] = it.jwt
                                    }

                                    intent.putExtra("token", it.jwt)
                                    startActivity(intent)
                                }
                        }
                    }
                })
                .addOnFailureListener(this, OnFailureListener { e ->
                    if (e is ApiException) {
                        // An error occurred when communicating with the
                        // reCAPTCHA service. Refer to the status code to
                        // handle the error appropriately.
                        Log.d(TAG, "Error: ${CommonStatusCodes.getStatusCodeString(e.statusCode)}")
                    } else {
                        // A different, unknown type of error occurred.
                        Log.d(TAG, "Error: ${e.message}")
                    }
                })
        }
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
package me.rooshi.podcastapp.feature.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.login_activity.*
import me.rooshi.podcastapp.WaveformUtils
import me.rooshi.podcastapp.common.Navigator
import me.rooshi.podcastapp.common.base.MyThemedActivity
import me.rooshi.podcastapp.common.util.extensions.viewBinding
import me.rooshi.podcastapp.databinding.LoginActivityBinding
import javax.inject.Inject
import javax.security.auth.Subject

@AndroidEntryPoint
class LoginActivity : MyThemedActivity(), LoginView {
    @Inject lateinit var callbackManager: CallbackManager
    @Inject lateinit var firebaseAuth: FirebaseAuth
    @Inject lateinit var navigator: Navigator

    override val emailChangedIntent: @NonNull Observable<CharSequence> by lazy { binding.emailTextEdit.textChanges() }
    override val passwordChangedIntent: @NonNull Observable<CharSequence> by lazy { binding.passwordTextEdit.textChanges() }
    override val signInClickedIntent: Observable<Unit> by lazy { binding.signInButton.clicks() }
    override val registerClickedIntent: Observable<Unit> by lazy { binding.registerTextView.clicks() }

    private val binding by viewBinding(LoginActivityBinding::inflate)
    //private val viewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        viewModel.bindView(this)

        binding.fbLoginButton.setPermissions(listOf("email", "public_profile"))
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                if (result != null) {
                    handleFacebookAccessToken(result.accessToken)
                }
            }

            override fun onError(error: FacebookException?) {
                TODO("Not yet implemented")
            }

            override fun onCancel() {
                TODO("Not yet implemented")
            }
        })

        //firebaseAuth = FirebaseAuth.getInstance()
        //emailTextInputLayout = findViewById<View>(R.id.emailTextInputLayout)
        //passwordTextInputLayout = findViewById<View>(R.id.passwordTextInputLayout)
    }

    override fun render(state: LoginState) {
        if (state.loggedIn) finish()

        if (state.registering) {
            navigator.startRegisterActivity()
            finish()
        }

        binding.signInButton.isEnabled = state.emailFilled && state.passwordFilled
        binding.errorMessage.text = state.loginMessage
    }


    /*
    fun onRegister(view: View?) {
        val registerIntent = Intent(this, RegisterActivity::class.java)
        //TODO: add email and password to the new activity
        registerIntent.putExtra("email", WaveformUtils.getStringFromTextInputLayout(emailTextInputLayout))
        //registerIntent.putExtra("password", WaveformUtils.getStringFromTextInputLayout(passwordTextInputLayout))
        startActivity(registerIntent)
    }
     */

    //refactor to reactive if possible
    fun onEmailSignIn(view: View?) {
        //TODO: check for email format
        val email = WaveformUtils.getStringFromTextInputLayout(emailTextInputLayout)
        val password = WaveformUtils.getStringFromTextInputLayout(passwordTextInputLayout)
        when {
            TextUtils.isEmpty(email) -> {
                //TODO: replace toast with something better looking
                Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(password) -> {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
            }

            else -> {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener { task ->
                            Toast.makeText(this, "Logged in!", Toast.LENGTH_LONG).show()
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                        }
            }
        }
    }

    fun handleFacebookAccessToken(token : AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener {
                    Toast.makeText(this, "Logged in with Facebook!", Toast.LENGTH_LONG).show()
                    finish()
                }
                .addOnFailureListener {e ->
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }
    }

}
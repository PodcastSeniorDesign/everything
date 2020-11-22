package me.rooshi.podcastapp.feature.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import me.rooshi.podcastapp.R
import me.rooshi.podcastapp.common.Navigator
import me.rooshi.podcastapp.common.base.MyThemedActivity
import me.rooshi.podcastapp.common.util.extensions.dismissKeyboard
import me.rooshi.podcastapp.common.util.extensions.viewBinding
import me.rooshi.podcastapp.databinding.LoginActivityBinding
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : MyThemedActivity(), LoginView {
    @Inject lateinit var callbackManager: CallbackManager
    @Inject lateinit var firebaseAuth: FirebaseAuth
    @Inject lateinit var navigator: Navigator

    override val emailChangedIntent: @NonNull Observable<CharSequence> by lazy { binding.emailTextEdit.textChanges() }
    override val passwordChangedIntent: @NonNull Observable<CharSequence> by lazy { binding.passwordTextEdit.textChanges() }
    override val signInClickedIntent: Observable<Unit> by lazy { binding.signInButton.clicks() }
    override val registerClickedIntent: Observable<Unit> by lazy { binding.toRegisterButton.clicks() }

    private val binding by viewBinding(LoginActivityBinding::inflate)
    //private val viewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }
    private val viewModel: LoginViewModel by viewModels()

    companion object {
        const val RC_SIGN_IN: Int = 666
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        viewModel.bindView(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.googleSignInButton.setOnClickListener {
            val intent = mGoogleSignInClient.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        }

        binding.fbLoginButton.setPermissions(listOf("email", "public_profile"))
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                if (result != null) {
                    handleFacebookAccessToken(result.accessToken)
                }
            }

            override fun onError(error: FacebookException?) {
                Log.e("Facebook login", error.toString())
            }

            override fun onCancel() {
                Log.e("Facebook login", "cancelled")
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            //google
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.e("googlesignin", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.e("googlesignin", "Google sign in failed", e)
                // ...
            }
        } else {
            //facebook
            callbackManager.onActivityResult(requestCode, resultCode, data)
        }
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

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("firebaseGoogle", "signInWithCredential:success")
                        if (task.result != null) {
                            if (task.result?.additionalUserInfo?.isNewUser!!) {
                                navigator.startFavoriteGenreActivity()
                            }
                            finish()
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("firebaseGoogle", "signInWithCredential:failure", task.exception)

                    }
                }
    }


    fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener {
                    Log.e("FB firebase success", it.user.toString())
                    finish()
                }
                .addOnCompleteListener { task ->
                    if (task.result != null) {
                        if (task.result?.additionalUserInfo?.isNewUser!!) {
                            navigator.startFavoriteGenreActivity()
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("FB firebase fail", e.toString())
                }
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    override fun closeKeyboard() {
        this.dismissKeyboard()
    }

}
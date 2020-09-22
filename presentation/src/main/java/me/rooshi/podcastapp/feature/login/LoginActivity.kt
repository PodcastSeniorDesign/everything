package me.rooshi.podcastapp.feature.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import me.rooshi.podcastapp.common.Navigator
import me.rooshi.podcastapp.common.base.MyThemedActivity
import me.rooshi.podcastapp.common.util.extensions.viewBinding
import me.rooshi.podcastapp.databinding.LoginActivityBinding
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : MyThemedActivity(), LoginView {
    //private FirebaseAuth firebaseAuth;
    //private TextInputLayout emailTextInputLayout;
    //private TextInputLayout passwordTextInputLayout;
    @Inject lateinit var navigator: Navigator
    //@Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override val emailChangedIntent: @NonNull Observable<CharSequence> by lazy { binding.emailTextEdit.textChanges() }
    override val passwordChangedIntent: @NonNull Observable<CharSequence> by lazy { binding.passwordTextEdit.textChanges() }
    override val signInClickedIntent: Observable<Unit> by lazy { binding.signInButton.clicks() }

    private val binding by viewBinding(LoginActivityBinding::inflate)
    //private val viewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        viewModel.bindView(this)

        //firebaseAuth = FirebaseAuth.getInstance()
        //emailTextInputLayout = findViewById<View>(R.id.emailTextInputLayout)
        //passwordTextInputLayout = findViewById<View>(R.id.passwordTextInputLayout)
    }

    override fun render(state: LoginState) {
        binding.signInButton.isEnabled = state.emailFilled && state.passwordFilled
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

    /*fun onEmailSignIn(view: View?) {
        //TODO: check for email format
        val email = WaveformUtils.getStringFromTextInputLayout(emailTextInputLayout)
        val password = WaveformUtils.getStringFromTextInputLayout(passwordTextInputLayout)
        if (TextUtils.isEmpty(email)) {
            //TODO: replace toast with something better looking
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, OnCompleteListener<AuthResult?> { task ->
                        if (!task.isSuccessful) {
                            Toast.makeText(this@LoginActivity, "Authentication Failed", Toast.LENGTH_LONG).show()
                        } else {
                            finish()
                        }
                    })
        }
    }
     */

}
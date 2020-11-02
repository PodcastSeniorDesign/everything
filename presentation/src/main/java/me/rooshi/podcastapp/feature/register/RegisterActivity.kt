package me.rooshi.podcastapp.feature.register

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import me.rooshi.podcastapp.common.Navigator
import me.rooshi.podcastapp.common.base.MyThemedActivity
import me.rooshi.podcastapp.common.util.extensions.dismissKeyboard
import me.rooshi.podcastapp.common.util.extensions.viewBinding
import me.rooshi.podcastapp.databinding.RegisterActivityBinding
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : MyThemedActivity(), RegisterView {
    @Inject lateinit var navigator: Navigator

    override val emailChangedIntent: @NonNull Observable<CharSequence> by lazy { binding.emailTextEdit.textChanges() }
    override val passwordChangedIntent: @NonNull Observable<CharSequence> by lazy { binding.passwordTextEdit.textChanges() }
    override val signInClickedIntent: Observable<Unit> by lazy { binding.cancelButton.clicks() }
    override val registerClickedIntent: Observable<Unit> by lazy { binding.registerButton.clicks() }

    private val binding by viewBinding(RegisterActivityBinding::inflate)
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        viewModel.bindView(this)
    }

    override fun render(state: RegisterState) {
        if (state.cancel) {
            navigator.startLoginActivity()
            finish()
        }
        if (state.loggedIn) {
            navigator.startFavoriteGenreActivity()
            finish()
        }

        binding.registerButton.isEnabled = state.emailFilled && state.passwordFilled
        binding.errorMessage.text = state.loginMessage

    }

    override fun closeKeyboard() {
        dismissKeyboard()
    }

}
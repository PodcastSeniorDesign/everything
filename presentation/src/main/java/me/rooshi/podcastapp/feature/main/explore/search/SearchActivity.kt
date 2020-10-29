package me.rooshi.podcastapp.feature.main.explore.search

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.jakewharton.rxbinding4.widget.editorActionEvents
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import me.rooshi.podcastapp.R
import me.rooshi.podcastapp.common.base.MyFragment
import me.rooshi.podcastapp.common.base.MyThemedActivity
import me.rooshi.podcastapp.common.util.extensions.dismissKeyboard
import me.rooshi.podcastapp.common.util.extensions.viewBinding
import me.rooshi.podcastapp.databinding.SearchActivityBinding
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity constructor(

) : MyThemedActivity(), SearchView {

    @Inject lateinit var searchAdapter: SearchAdapter

    override val queryChangedIntent by lazy { binding.textInputEditText.textChanges() }
    override val searchIntent by lazy { binding.textInputEditText.editorActionEvents() }

    private val binding by viewBinding(SearchActivityBinding::inflate)
    private val viewModel : SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.bindView(this)

        binding.searchRV.adapter = searchAdapter
    }

    override fun render(state: SearchState) {
        searchAdapter.data = state.results
        //Log.d("inside render", state.results.size.toString())
    }

    override fun closeKeyboard() {
        dismissKeyboard()
    }
}
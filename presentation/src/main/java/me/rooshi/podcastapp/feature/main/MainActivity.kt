package me.rooshi.podcastapp.feature.main;

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.jakewharton.rxbinding4.view.clicks
import dagger.hilt.android.AndroidEntryPoint
import me.rooshi.podcastapp.feature.main.player.PlayerFragment
import me.rooshi.podcastapp.R
import me.rooshi.podcastapp.common.Navigator
import me.rooshi.podcastapp.common.base.MyFragment
import me.rooshi.podcastapp.common.base.MyThemedActivity
import me.rooshi.podcastapp.common.util.extensions.viewBinding
import me.rooshi.podcastapp.databinding.MainActivityBinding
import me.rooshi.podcastapp.feature.main.explore.ExploreFragment
import me.rooshi.podcastapp.feature.main.social.SocialFragment
import me.rooshi.podcastapp.feature.main.subscriptions.SubscriptionsFragment
import javax.inject.Inject

//ACTIVITY JUST DOES THE UI PARTS AND SETTING UP THE INTENTS
// THE ACTUAL LOGIC IS IN THE VIEWMODEL CLASS
@AndroidEntryPoint
class MainActivity : MyThemedActivity(), MainView {

    @Inject lateinit var navigator : Navigator

    //@Inject lateinit var myFragmentFactory: MyFragmentFactory

    //need to inject these. on second thought if i use fragmentfactory I might not be able to
    private var exploreFragment = ExploreFragment()
    private var subscriptionsFragment = SubscriptionsFragment()
    private var socialFragment = SocialFragment()
    private var playerFragment = PlayerFragment()

    private var last: MyFragment? = null

    private var switchCount: Int = 0

    override val castIntent by lazy { binding.cast.clicks() }
    override val profileIntent by lazy { binding.profileImage.clicks() }

    private val binding by viewBinding(MainActivityBinding::inflate)
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        viewModel.bindView(this)

        setSupportActionBar(findViewById(R.id.toolbar))

        supportFragmentManager.beginTransaction()
                .add(R.id.playerContainerView, playerFragment, "player")
                .commit()

        supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainerView, socialFragment, "social")
                .commit()
        last = socialFragment

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            setFragmentContainer(item)
        }
    }

    override fun refreshFragments() {
        supportFragmentManager.beginTransaction().remove(last!!)
        supportFragmentManager.beginTransaction().remove(playerFragment)

        exploreFragment = ExploreFragment()
        subscriptionsFragment = SubscriptionsFragment()
        socialFragment = SocialFragment()
        playerFragment = PlayerFragment()

    }

    private fun setFragmentContainer(item: MenuItem) : Boolean {
        //2 if presenting
        //5 or so if testing
        if (switchCount%5 == 0) refreshFragments()
        when (item.itemId) {
            R.id.bottom_nav_social -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, socialFragment, "social")
                        .commit()
                last = socialFragment
            }

            R.id.bottom_nav_subscriptions -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, subscriptionsFragment, "subscriptions")
                        .commit()
                last = subscriptionsFragment
            }

            R.id.bottom_nav_explore -> {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, exploreFragment, "explore")
                        .commit()
                last = exploreFragment
            }
            else -> return false
        }
        switchCount++
        return true
    }

    override fun render(state: MainState) {
        if (state.hasError) {
            finish()
            return
        }

    }
}

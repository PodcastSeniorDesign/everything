package me.rooshi.podcastapp.feature.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import me.rooshi.podcastapp.feature.main.explore.ExploreFragment
import javax.inject.Inject

class MyFragmentFactory
@Inject constructor(
        //private val someObject: Object
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){

            ExploreFragment::class.java.name -> {
                ExploreFragment(/*someObject*/)
            }
            //for each of the 3 fragments, add above

            else -> super.instantiate(classLoader, className)
        }
    }

}
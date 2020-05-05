
package com.mindorks.todonotesapp.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.lang.IllegalStateException

class FragmentAdapter(fragmentManager : FragmentManager) :FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 ->
                return OnBoardingOneFragment()

            1 ->
                return OnBoardingTwoFragment()

        }
        throw IllegalStateException("position $position is invalid for this viewpager")
    }

    override fun getCount(): Int {
       return  2
    }

}
package com.example.eletricscarts.presentation.adapter

import android.os.Parcel
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.eletricscarts.presentation.CarFragment
import com.example.eletricscarts.presentation.FavoritesFragment

class TabAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
     return when(position){
            0 -> CarFragment()
            1 -> FavoritesFragment()
            else -> CarFragment()
        }

    }

    override fun getItemCount(): Int {
        return 2
    }
}
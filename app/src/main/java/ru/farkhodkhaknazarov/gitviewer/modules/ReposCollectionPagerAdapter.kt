package ru.farkhodkhaknazarov.gitviewer.modules

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ru.farkhodkhaknazarov.gitviewer.viewes.AbstractGistsFragments

class ReposCollectionPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm)  {
    var titels: ArrayList<String> = arrayListOf()
    var fragments: ArrayList<AbstractGistsFragments> = arrayListOf()

    override fun getItem(position: Int): AbstractGistsFragments {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titels[position]
    }
}
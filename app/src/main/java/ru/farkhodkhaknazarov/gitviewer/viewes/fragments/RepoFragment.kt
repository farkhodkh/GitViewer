package ru.farkhodkhaknazarov.gitviewer.viewes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.farkhodkhaknazarov.gitviewer.R
import ru.farkhodkhaknazarov.gitviewer.viewes.AbstractGistsFragments

class RepoFragment: AbstractGistsFragments() {

    companion object {

        lateinit var instance: RepoFragment

        lateinit var fragmentView: View

        fun setupInstance(repoFragment: RepoFragment, view: View) {
            instance = repoFragment
            fragmentView = view
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_gist, container, false)

        RepoFragment.setupInstance(this, view)

        return view
    }

}
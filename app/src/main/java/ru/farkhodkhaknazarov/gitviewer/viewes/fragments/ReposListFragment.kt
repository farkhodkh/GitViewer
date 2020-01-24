package ru.farkhodkhaknazarov.gitviewer.viewes.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.farkhodkhaknazarov.gitviewer.R
import ru.farkhodkhaknazarov.gitviewer.presenters.ReposListFragmentPresenter
import ru.farkhodkhaknazarov.gitviewer.utils.App
import ru.farkhodkhaknazarov.gitviewer.viewes.AbstractGistsFragments
import javax.inject.Inject

class ReposListFragment: AbstractGistsFragments() {

    @Inject
    lateinit var presenter: ReposListFragmentPresenter

    lateinit var fragmentView: View

    companion object {
        lateinit var instance: ReposListFragment

        fun setupGistsListFragment(fragment: ReposListFragment) {
            instance = fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        ReposListFragment.setupGistsListFragment(this)

        App.getComponent()?.injectGistsListFragment(this)

        fragmentView = inflater.inflate(R.layout.fragment_gists_list, container, false)

        presenter.initView()

        return fragmentView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroyView()
    }


}
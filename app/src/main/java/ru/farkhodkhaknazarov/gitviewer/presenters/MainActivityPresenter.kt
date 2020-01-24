package ru.farkhodkhaknazarov.gitviewer.presenters

import android.os.Build
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.farkhodkhaknazarov.gitviewer.modules.MainActivityModule
import ru.farkhodkhaknazarov.gitviewer.modules.ReposCollectionPagerAdapter
import ru.farkhodkhaknazarov.gitviewer.utils.App
import ru.farkhodkhaknazarov.gitviewer.utils.ManagePermissionsUtility
import ru.farkhodkhaknazarov.gitviewer.viewes.MainView
import ru.farkhodkhaknazarov.gitviewer.viewes.fragments.RepoFragment
import ru.farkhodkhaknazarov.gitviewer.viewes.fragments.ReposListFragment
import javax.inject.Inject

@InjectViewState
class MainActivityPresenter : MvpPresenter<MainView>() {

    lateinit var listFragment: ReposListFragment

    lateinit var repoFragment: RepoFragment

    @Inject
    lateinit var module: MainActivityModule

    fun checkAppPermission() {
        App.getComponent()?.injectMainActivityPresenter(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            ManagePermissionsUtility().checkPermissions()
    }


    fun showSnakebarMessage(message: String) {
        viewState.showSnakebar(message)
    }

    fun preparePagerAdapter(adapter: ReposCollectionPagerAdapter) {
        adapter.titels.add("List")
        adapter.titels.add("Repo")

        listFragment = ReposListFragment()
        repoFragment = RepoFragment()

        adapter.fragments.add(listFragment)
        adapter.fragments.add(repoFragment)
    }

    fun setCurrentView(pageNumber: Int) {

        viewState.setPagerCurrentItem(pageNumber)

    }

    fun showSnakebar(message: String) {
        viewState.showSnakebar(message)
    }

    fun onRefreshButtonClick() {
        module.onRefreshButtonClick()
    }
}
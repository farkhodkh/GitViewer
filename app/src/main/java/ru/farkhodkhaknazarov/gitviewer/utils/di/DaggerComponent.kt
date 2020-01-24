package ru.farkhodkhaknazarov.gitviewer.utils.di

import dagger.Component
import ru.farkhodkhaknazarov.gitviewer.activities.LoginActivity
import ru.farkhodkhaknazarov.gitviewer.activities.MainActivity
import ru.farkhodkhaknazarov.gitviewer.modules.LoginActivityModule
import ru.farkhodkhaknazarov.gitviewer.modules.MainActivityModule
import ru.farkhodkhaknazarov.gitviewer.modules.fragments.RepoFragmentModule
import ru.farkhodkhaknazarov.gitviewer.modules.RepoRecyclerViewAdapter
import ru.farkhodkhaknazarov.gitviewer.modules.fragments.ReposListFragmentModule
import ru.farkhodkhaknazarov.gitviewer.presenters.LoginActivityPresenter
import ru.farkhodkhaknazarov.gitviewer.presenters.MainActivityPresenter
import ru.farkhodkhaknazarov.gitviewer.presenters.RepoFragmentPresenter
import ru.farkhodkhaknazarov.gitviewer.presenters.ReposListFragmentPresenter
import ru.farkhodkhaknazarov.gitviewer.utils.di.modules.CommonObjects
import ru.farkhodkhaknazarov.gitviewer.utils.di.modules.FragmentObjects
import ru.farkhodkhaknazarov.gitviewer.utils.di.modules.WebViewObjects
import ru.farkhodkhaknazarov.gitviewer.viewes.fragments.ReposListFragment
import javax.inject.Singleton


@Singleton
@Component(modules = [CommonObjects::class, WebViewObjects::class, FragmentObjects::class])
interface DaggerComponent {

    fun injectLoginActivity(loginActivity: LoginActivity)

    fun injectLoginActivityPresenter(loginPresenter: LoginActivityPresenter)

    fun injectLoginActivityModule(loginActivityModule: LoginActivityModule)

    fun injectMainActivityPresenter(mainActivityPresenter: MainActivityPresenter)

    fun injectMainActivity(mainActivity: MainActivity)

    fun injectRepoRecyclerViewAdapter(repoRecyclerViewAdapter: RepoRecyclerViewAdapter)

    fun injectRepoFragmentPresenter(repoFragmentPresenter: RepoFragmentPresenter)

    fun injectRepoFragmentModule(repoFragmentModule: RepoFragmentModule)

    fun injectGistsListFragment(reposListFragment: ReposListFragment)

    fun injectGistsListFragmentPresenter(reposListFragmentPresenter: ReposListFragmentPresenter)

    fun injectGistsListFragmentModule(reposListFragmentModule: ReposListFragmentModule)

    fun injectMainActivityModule(mainActivityModule: MainActivityModule)
}
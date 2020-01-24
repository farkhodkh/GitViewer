package ru.farkhodkhaknazarov.gitviewer.utils.di.modules

import dagger.Module
import dagger.Provides
import ru.farkhodkhaknazarov.gitviewer.modules.fragments.RepoFragmentModule
import ru.farkhodkhaknazarov.gitviewer.modules.fragments.ReposListFragmentModule
import ru.farkhodkhaknazarov.gitviewer.presenters.RepoFragmentPresenter
import ru.farkhodkhaknazarov.gitviewer.presenters.ReposListFragmentPresenter

@Module
object FragmentObjects {
    @JvmStatic
    @Provides
    fun getGistsListFragmentPresenter(): ReposListFragmentPresenter =
        ReposListFragmentPresenter.getPresenterInstance()

    @JvmStatic
    @Provides
    fun getGistsListFragmentModule(): ReposListFragmentModule = ReposListFragmentModule()

    @JvmStatic
    @Provides
    fun getRepoFragmentPresenter(): RepoFragmentPresenter =
        RepoFragmentPresenter.getPresenterInstance()

    @JvmStatic
    @Provides
    fun getRepoFragmentModule(): RepoFragmentModule = RepoFragmentModule()
}
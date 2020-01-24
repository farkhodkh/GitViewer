package ru.farkhodkhaknazarov.gitviewer.modules.fragments

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.doAsync

import ru.farkhodkhaknazarov.elegion.data.room.utils.RoomSingleton
import ru.farkhodkhaknazarov.gitviewer.activities.MainActivity
import ru.farkhodkhaknazarov.gitviewer.data.api.github.GitHubServiceImpl
import ru.farkhodkhaknazarov.gitviewer.data.api.github.entities.Repo
import ru.farkhodkhaknazarov.gitviewer.data.api.github.entities.UserToken
import ru.farkhodkhaknazarov.gitviewer.presenters.MainActivityPresenter
import ru.farkhodkhaknazarov.gitviewer.presenters.ReposListFragmentPresenter
import ru.farkhodkhaknazarov.gitviewer.utils.App
import ru.farkhodkhaknazarov.gitviewer.utils.NetworkService
import java.util.ArrayList
import javax.inject.Inject

class ReposListFragmentModule {
    @Inject
    lateinit var mDb: RoomSingleton

    @Inject
    lateinit var presenter: ReposListFragmentPresenter

    @Inject
    lateinit var mainActivityPresenter: MainActivityPresenter

    var disposable: Disposable? = null

    var pageNumber = 0

    fun updateRecyclerViewAdapter() {

        App.getComponent()?.injectGistsListFragmentModule(this)

        doAsync {

            val networkStatus = NetworkService.checkNetworkState()

            val token: UserToken = mDb.tokensDao().firstToken()

            var observable = when (networkStatus) {

                true -> {

                    pageNumber += 1

                    if (pageNumber == 1) {
                        clearAdapterList()
                    }

                    GitHubServiceImpl.getApiService().getUserRepos(
                        "token " + token.token,
                        pageNumber
                    )
                }

                false -> {

                    clearAdapterList()

                    var list = mDb.reposDao().allRepos()

                    if (list.size == 0) {
                        list = getEmptyStateList()
                    }

                    Observable.just(list) as Observable<ArrayList<Repo>>
                }
                else -> null
            }

            disposable = observable?.subscribe {
                if (it != null) {
                    onDataUpdate(it)

                    if (networkStatus) {
                        saveRepoList(it)
                    }
                }
            }

            if (!networkStatus) {
                mainActivityPresenter.showSnakebar("Отсутствует интернет соединение")
                presenter.swipeContainer.isRefreshing = false
            }
        }
    }

    fun clearAdapterList() {

        App.getComponent()?.injectGistsListFragmentModule(this)

        presenter.viewAdapter.repoList?.clear()

        presenter.notifyDataSetChanged()

    }

    private fun onDataUpdate(repoList: ArrayList<Repo>?) {

        repoList?.asIterable()?.let { presenter.viewAdapter.repoList?.addAll(it) }

        MainActivity.activity.runOnUiThread {
            if (repoList?.size!! > 0) {
                presenter.viewAdapter.notifyDataSetChanged()
            }
        }

        presenter.swipeContainer.isRefreshing = false
    }

    fun onDestroyView() {
        disposable?.dispose()
    }

    private fun saveRepoList(repoList: List<Repo>) {
        doAsync {
            repoList.forEach {
                mDb.reposDao().insertRepo(it)
            }
        }
    }

    private fun getEmptyStateList(): List<Repo> {

        val repo = Repo(
            "2020-01-01T00:00:01Z",
            "License",
            9999,
            "Repo language",
            "Repo name",
            "Owner name",
            ""
        )

        return arrayListOf<Repo>(repo, repo, repo, repo, repo)
    }
}
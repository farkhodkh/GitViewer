package ru.farkhodkhaknazarov.gitviewer.modules.fragments

import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.doAsync
import ru.farkhodkhaknazarov.elegion.data.entities.Commit
import ru.farkhodkhaknazarov.elegion.data.room.utils.RoomSingleton
import ru.farkhodkhaknazarov.gitviewer.data.api.github.GitHubServiceImpl
import ru.farkhodkhaknazarov.gitviewer.data.api.github.entities.Repo
import ru.farkhodkhaknazarov.gitviewer.presenters.RepoFragmentPresenter
import ru.farkhodkhaknazarov.gitviewer.utils.App
import ru.farkhodkhaknazarov.gitviewer.utils.NetworkService
import javax.inject.Inject

class RepoFragmentModule {
    @Inject
    lateinit var mDb: RoomSingleton

    @Inject
    lateinit var presenter: RepoFragmentPresenter

    var disposable: Disposable? = null
    var disposableForks: Disposable? = null

    fun onItemSelected(repoId: Int, commitsUrl: String) {
        App.getComponent()?.injectRepoFragmentModule(this)

        doAsync {

            val repo = mDb.reposDao().getrepoById(repoId)
            presenter.fillRepoData(repo)

            repo?.let {
                getCommitDetails(repo!!)
            }
        }
    }

    fun getCommitDetails(repo: Repo){
        doAsync {
            val networkStatus = NetworkService.checkNetworkState()


            var observable = if (repo != null && networkStatus) {
                val commits_url = repo.commits_url.replace("{/sha}", "")

                GitHubServiceImpl.getApiService().getRepoCommits(commits_url)
            } else {
                null
            }
            var observer =
                CommitObserver()

            observable?.subscribe(observer)
        }
    }

    fun onDestroyView() {
        disposable?.dispose()
        disposableForks?.dispose()

    }

    class CommitObserver: Observer<ArrayList<Commit>> {
        lateinit var disposableCommits: Disposable

        override fun onComplete() {
            disposableCommits?.dispose()
        }

        override fun onSubscribe(disposable: Disposable) {
            disposableCommits = disposable
        }

        override fun onNext(commitList: ArrayList<Commit>) {

            if (commitList != null && commitList.size > 0) {
                RepoFragmentPresenter.instance?.filRepoCommitDetails(commitList[0])
            }else{
                RepoFragmentPresenter.instance?.filRepoCommitDetails(null)
            }
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
            RepoFragmentPresenter.instance?.filRepoCommitDetails(null)
        }

    }
}
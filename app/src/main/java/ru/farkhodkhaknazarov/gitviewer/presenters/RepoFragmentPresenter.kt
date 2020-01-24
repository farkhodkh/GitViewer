package ru.farkhodkhaknazarov.gitviewer.presenters

import android.view.View
import android.widget.TextView
import ru.farkhodkhaknazarov.elegion.data.entities.Commit
import ru.farkhodkhaknazarov.elegion.data.entities.Repo
import ru.farkhodkhaknazarov.gitviewer.R
import ru.farkhodkhaknazarov.gitviewer.activities.MainActivity
import ru.farkhodkhaknazarov.gitviewer.modules.fragments.RepoFragmentModule
import ru.farkhodkhaknazarov.gitviewer.utils.App
import ru.farkhodkhaknazarov.gitviewer.viewes.fragments.RepoFragment
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RepoFragmentPresenter {
    @Inject
    lateinit var module: RepoFragmentModule

    lateinit var fragmentView: View

    lateinit var txtWRepoFullName: TextView

    lateinit var txtWRepoDescription: TextView

    lateinit var txtWForks: TextView

    lateinit var txtWWatcher: TextView

    lateinit var txtWStars: TextView

    lateinit var txtWCommitMessage: TextView

    lateinit var txtWCommitAuthor: TextView

    lateinit var txtWCommitDate: TextView

    companion object {
        var instance: RepoFragmentPresenter? = null

        fun getPresenterInstance(): RepoFragmentPresenter {
            if (instance == null) {
                instance = RepoFragmentPresenter()
            }

            return instance!!
        }
    }

    fun onItemSelected(repoId: Int, commitsUrl: String) {
        App.getComponent()?.injectRepoFragmentPresenter(this)

        module.onItemSelected(repoId, commitsUrl)
    }

    fun fillRepoData(repo: Repo?) {

        App.getComponent()?.injectRepoFragmentPresenter(this)

        MainActivity.activity.runOnUiThread {

            fragmentView = RepoFragment.fragmentView

            txtWRepoFullName = fragmentView.findViewById(R.id.txtWRepoFullName)

            txtWRepoDescription = fragmentView.findViewById(R.id.txtWRepoDescription)

            txtWForks = fragmentView.findViewById<TextView>(R.id.txtWForks)

            txtWWatcher = fragmentView.findViewById<TextView>(R.id.txtWWatcher)

            txtWStars = fragmentView.findViewById<TextView>(R.id.txtWStars)

            txtWRepoFullName.text = repo?.full_name

            txtWRepoDescription.text = repo?.description

            txtWForks.text = repo?.forks.toString()

            txtWWatcher.text = repo?.watchers.toString()

            txtWStars.text = repo?.stargazers_count.toString()

            MainActivity.activity.setPagerCurrentItem(1)
        }
    }

    fun filRepoCommitDetails(commit: Commit?) {

        App.getComponent()?.injectRepoFragmentPresenter(this)
        MainActivity.activity.runOnUiThread {

            fragmentView = RepoFragment.fragmentView

            txtWCommitMessage = fragmentView.findViewById<TextView>(R.id.txtWCommitMessage)

            txtWCommitAuthor = fragmentView.findViewById<TextView>(R.id.txtWCommitAuthor)

            txtWCommitDate = fragmentView.findViewById<TextView>(R.id.txtWCommitDate)

            if(commit==null) {
                txtWCommitMessage.text = ""

                txtWCommitAuthor.text = ""

                txtWCommitDate.text = ""

            } else{
                txtWCommitMessage.text = commit?.commit?.message

                txtWCommitAuthor.text = commit?.author?.login

                commit?.commit?.author?.date.let {

                    val dateFormat =
                        SimpleDateFormat("dd-MM-yyyy")

                    val dateFormat_yyyyMMddHHmmss =
                        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)

                    val date = dateFormat_yyyyMMddHHmmss.parse(commit?.commit?.author?.date)

                    txtWCommitDate.text = dateFormat.format(date)

                }
            }
        }
    }

    fun onDestroyView() {
        module.onDestroyView()
    }
}
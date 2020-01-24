package ru.farkhodkhaknazarov.gitviewer.modules

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.farkhodkhaknazarov.gitviewer.R
import ru.farkhodkhaknazarov.gitviewer.data.api.github.entities.Repo
import ru.farkhodkhaknazarov.gitviewer.presenters.ReposListFragmentPresenter
import ru.farkhodkhaknazarov.gitviewer.utils.App
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RepoRecyclerViewAdapter(var repoList: ArrayList<Repo>?) :
    RecyclerView.Adapter<RepoRecyclerViewAdapter.RepoViewHolder>()  {
    @Inject
    lateinit var picasso: Picasso //= Picasso.with(MainActivity.instance.applicationContext)
    //2018-02-22T16:04:38Z
    val dateFormat_yyyyMMddHHmmss = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH
    )

    class RepoViewHolder(var repoItemView: LinearLayout) : RecyclerView.ViewHolder(repoItemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val repotemView =
            LayoutInflater.from(parent.context).inflate(
                R.layout.repo_list_item_view,
                parent,
                false
            ) as LinearLayout

        return RepoViewHolder(repotemView)

    }

    override fun getItemId(position: Int): Long {
        return if (repoList?.size == 0) 0 else super.getItemId(position)
    }

    override fun getItemCount(): Int {
        if (repoList == null) {
            return 0
        } else {
            return repoList!!.size
        }
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {

        App.getComponent()?.injectRepoRecyclerViewAdapter(this)

        val txtWRepoName = holder.repoItemView.findViewById<TextView>(R.id.txtWRepoName)
        val imgVAuthor = holder.repoItemView.findViewById<ImageView>(R.id.imgVAuthor)
        val txtWAuthorName = holder.repoItemView.findViewById<TextView>(R.id.txtWAuthorName)
        val txtWDate = holder.repoItemView.findViewById<TextView>(R.id.txtWDate)
        val txtWWatchers = holder.repoItemView.findViewById<TextView>(R.id.txtWWatchers)
        val txtWLangDesc = holder.repoItemView.findViewById<TextView>(R.id.txtWLangDesc)
        val txtWLicense = holder.repoItemView.findViewById<TextView>(R.id.txtWLicense)

        val repo = repoList!![position]
        //"2018-02-22T16:04:53Z"
        val dateFormat =
            SimpleDateFormat("dd-MM-yyyy")

        val dateFormat_yyyyMMddHHmmss = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val date = dateFormat_yyyyMMddHHmmss.parse(repo.updated_at)

        txtWDate.text = dateFormat.format(date)
        txtWLicense.text = repo.license
        txtWWatchers.text = repo.watchers.toString()
        txtWLangDesc.text = repo.language
        txtWRepoName.text = repo.name
        txtWAuthorName.text = repo.owner.login


        txtWRepoName.setOnClickListener {
            ReposListFragmentPresenter.getPresenterInstance().onItemSelectedListener(repo.id, repo.commits_url)
        }

        if (repo.owner.avatar_url != "") {
            try {
                picasso
                    .load(repo.owner.avatar_url)
                    .into(imgVAuthor)
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
        }

    }
}
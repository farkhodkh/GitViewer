package ru.farkhodkhaknazarov.gitviewer.presenters

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.farkhodkhaknazarov.elegion.data.entities.Repo
import ru.farkhodkhaknazarov.gitviewer.R
import ru.farkhodkhaknazarov.gitviewer.activities.MainActivity
import ru.farkhodkhaknazarov.gitviewer.modules.RepoRecyclerViewAdapter
import ru.farkhodkhaknazarov.gitviewer.modules.fragments.ReposListFragmentModule
import ru.farkhodkhaknazarov.gitviewer.utils.App
import ru.farkhodkhaknazarov.gitviewer.viewes.fragments.ReposListFragment
import javax.inject.Inject

class ReposListFragmentPresenter {
    @Inject
    lateinit var module: ReposListFragmentModule

    @Inject
    lateinit var mainActivityPresenter: MainActivityPresenter

    @Inject
    lateinit var repoFragmentPresenter: RepoFragmentPresenter

    lateinit var fragmentView: View

    lateinit var reposRecyclerView: RecyclerView

    lateinit var viewManager: RecyclerView.LayoutManager

    lateinit var swipeContainer: SwipeRefreshLayout

    lateinit var viewAdapter: RepoRecyclerViewAdapter

    companion object {

        private var instance: ReposListFragmentPresenter? = null

        fun getPresenterInstance(): ReposListFragmentPresenter {
            if (instance == null) {
                instance = ReposListFragmentPresenter()
            }

            return instance!!
        }
    }

    fun initView() {
        App.getComponent()?.injectGistsListFragmentPresenter(this)

        fragmentView = ReposListFragment.instance.fragmentView

        swipeContainer = fragmentView.findViewById(R.id.swipeContainer)

        swipeContainer.setOnRefreshListener(onRefresh())

        swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        val emptyList: ArrayList<Repo> = arrayListOf()

        viewAdapter = RepoRecyclerViewAdapter(emptyList)

        viewManager = LinearLayoutManager(MainActivity.context)

        reposRecyclerView =
            fragmentView.findViewById<RecyclerView>(R.id.repos_recycler_view).apply {
                setHasFixedSize(true)
                layoutManager = viewManager
            }

        reposRecyclerView.adapter = viewAdapter

        reposRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                    mainActivityPresenter.showSnakebar("Загрузка")
                    instance?.module?.updateRecyclerViewAdapter()
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })

        module.updateRecyclerViewAdapter()
    }

    fun onItemSelectedListener(repoId: Int, commitsUrl: String){
        repoFragmentPresenter.onItemSelected(repoId, commitsUrl)
    }

    fun onDestroyView() {
        module.onDestroyView()
    }

    fun notifyDataSetChanged() {
        MainActivity.activity.runOnUiThread {
            viewAdapter.notifyDataSetChanged()
        }
    }

    class onRefresh : SwipeRefreshLayout.OnRefreshListener {
        override fun onRefresh() {
            instance?.module?.pageNumber = 0

            instance?.module?.updateRecyclerViewAdapter()
        }
    }


}
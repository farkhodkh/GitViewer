package ru.farkhodkhaknazarov.gitviewer.activities

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.farkhodkhaknazarov.gitviewer.R
import ru.farkhodkhaknazarov.gitviewer.modules.ReposCollectionPagerAdapter
import ru.farkhodkhaknazarov.gitviewer.presenters.MainActivityPresenter
import ru.farkhodkhaknazarov.gitviewer.utils.App
import ru.farkhodkhaknazarov.gitviewer.viewes.MainView

@StateStrategyType(value = OneExecutionStateStrategy::class)
class MainActivity : MvpAppCompatActivity(), MainView {
    @InjectPresenter
    lateinit var presenter: MainActivityPresenter

    lateinit var pager: ViewPager

    lateinit var adapter: ReposCollectionPagerAdapter

    companion object {
        lateinit var context: Context
        lateinit var activity: MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.getComponent()?.injectMainActivity(this)

        MainActivity.Companion.context = applicationContext
        MainActivity.Companion.activity = this

        pager = findViewById(R.id.pager)

        adapter = ReposCollectionPagerAdapter(supportFragmentManager)

        presenter.preparePagerAdapter(adapter)

        pager.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        checkAppPermission()
    }

    override fun checkAppPermission() {
        presenter.checkAppPermission()
    }

    override fun showSnakebar(message: String) {

        Toast.makeText(context, "Test", Toast.LENGTH_LONG).show()

        Snackbar
            .make(pager, message, Snackbar.LENGTH_SHORT)
            .setAction("Обновить", View.OnClickListener {
                presenter.onRefreshButtonClick()
            })
            .show()
    }

    override fun setPagerCurrentItem(pageNumber: Int) {
        pager.currentItem = pageNumber
    }

    override fun onBackPressed() {
        if (pager.currentItem == 0) {
            super.onBackPressed()
            finish()
        } else {
            setPagerCurrentItem(0)
        }
    }
}


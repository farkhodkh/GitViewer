package ru.farkhodkhaknazarov.gitviewer.modules

import com.squareup.picasso.Picasso
import ru.farkhodkhaknazarov.gitviewer.activities.MainActivity
import ru.farkhodkhaknazarov.gitviewer.modules.fragments.ReposListFragmentModule
import ru.farkhodkhaknazarov.gitviewer.utils.App
import javax.inject.Inject

class MainActivityModule {

    @Inject
    lateinit var fragmentModule: ReposListFragmentModule

    companion object{

        var picasso: Picasso? = null

        fun returnPicasso(): Picasso {

            if(picasso == null){
                picasso = Picasso.with(MainActivity.context)
            }

            return picasso!!
        }
    }

    fun onRefreshButtonClick() {
        App.getComponent()?.injectMainActivityModule(this)

        fragmentModule.updateRecyclerViewAdapter()
    }
}
package ru.farkhodkhaknazarov.gitviewer.utils.di.modules

import androidx.room.Room
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import ru.farkhodkhaknazarov.elegion.data.room.utils.RoomSingleton
import ru.farkhodkhaknazarov.gitviewer.activities.LoginActivity
import ru.farkhodkhaknazarov.gitviewer.modules.MainActivityModule
import ru.farkhodkhaknazarov.gitviewer.presenters.MainActivityPresenter
import ru.farkhodkhaknazarov.gitviewer.utils.App

@Module
object CommonObjects {
    @Provides
    @JvmStatic
    fun providesMainActivityPresenter():MainActivityPresenter = MainActivityPresenter()

    @Provides
    @JvmStatic
    fun providesMainActivityModule():MainActivityModule = MainActivityModule()
//
//    @Provides
//    @JvmStatic
//    fun getLoginPresenter(): LoginActivityPresenter = LoginActivityPresenter()
//
//    @Provides
//    @JvmStatic
//    fun getLoginModule(): LoginActivityModule = LoginActivityModule.initModule()
//
//    @Provides
//    @JvmStatic
//    fun getPermissionsPresenter(): ManagePermissionsPresenter = ManagePermissionsPresenter()
//
    @Provides
    fun getRoomDataBase(): RoomSingleton = RoomSingleton.getInstance(LoginActivity.context)

    @Provides
    @JvmStatic
    fun getPicasso(): Picasso = MainActivityModule.returnPicasso()

}
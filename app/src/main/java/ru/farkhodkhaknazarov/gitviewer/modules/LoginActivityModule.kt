package ru.farkhodkhaknazarov.gitviewer.modules

import org.jetbrains.anko.doAsync

import ru.farkhodkhaknazarov.elegion.data.room.utils.RoomSingleton
import ru.farkhodkhaknazarov.gitviewer.data.api.github.entities.UserToken
import ru.farkhodkhaknazarov.gitviewer.utils.App
import javax.inject.Inject

class LoginActivityModule {
    @Inject
    lateinit var mDb: RoomSingleton

    fun saveToken(token: String){

        App.getComponent()?.injectLoginActivityModule(this)

        doAsync {
            mDb.tokensDao().insert(UserToken(0, token))
        }
    }
}
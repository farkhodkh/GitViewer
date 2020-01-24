package ru.farkhodkhaknazarov.gitviewer.utils

import android.app.Application
import ru.farkhodkhaknazarov.gitviewer.utils.di.DaggerComponent
import ru.farkhodkhaknazarov.gitviewer.utils.di.DaggerDaggerComponent

class App : Application() {
    companion object {
        fun getComponent(): DaggerComponent? {

            return DaggerDaggerComponent
                .builder()
                .build()
        }
    }

}
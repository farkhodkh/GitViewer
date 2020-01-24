package ru.farkhodkhaknazarov.gitviewer.utils.di.modules

import dagger.Module
import dagger.Provides
import ru.farkhodkhaknazarov.gitviewer.presenters.webcomponents.WebChrClient
import ru.farkhodkhaknazarov.gitviewer.presenters.webcomponents.WebClient

@Module
object WebViewObjects {

    @Provides
    fun getWebClient(): WebClient = WebClient()

    @Provides
    fun getWebChromeClient(): WebChrClient = WebChrClient()
}
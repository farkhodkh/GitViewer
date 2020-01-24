package ru.farkhodkhaknazarov.gitviewer.presenters

import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import moxy.InjectViewState
import moxy.MvpPresenter
import org.jetbrains.anko.doAsync
import ru.farkhodkhaknazarov.elegion.data.room.utils.RoomSingleton
import ru.farkhodkhaknazarov.gitviewer.common.Constants
import ru.farkhodkhaknazarov.gitviewer.data.api.github.entities.UserToken
import ru.farkhodkhaknazarov.gitviewer.presenters.webcomponents.WebChrClient
import ru.farkhodkhaknazarov.gitviewer.presenters.webcomponents.WebClient
import ru.farkhodkhaknazarov.gitviewer.utils.App
import ru.farkhodkhaknazarov.gitviewer.viewes.LoginView
import javax.inject.Inject

@InjectViewState
class LoginActivityPresenter : MvpPresenter<LoginView>() {

    @Inject
    lateinit var webViewClient: WebClient

    @Inject
    lateinit var webChromeClient: WebChrClient

    @Inject
    lateinit var mDb: RoomSingleton

    fun prepareView(wvGitHub: WebView) {

        App.getComponent()?.injectLoginActivityPresenter(this)

        webViewClient.setPresenter(this)

        prepareWebView(wvGitHub)
    }

    fun prepareWebView(wvGitHub: WebView) {
        wvGitHub.settings.javaScriptEnabled = true
        wvGitHub.settings.javaScriptCanOpenWindowsAutomatically = true
        wvGitHub.settings.domStorageEnabled = true

        wvGitHub.settings.setAppCacheEnabled(true)
        wvGitHub.settings.databaseEnabled = true

        wvGitHub.settings.setGeolocationEnabled(true)

        wvGitHub.webViewClient = webViewClient
        wvGitHub.webChromeClient = webChromeClient

        wvGitHub.settings.loadWithOverviewMode = true
        wvGitHub.settings.useWideViewPort = true

        wvGitHub.settings.pluginState = WebSettings.PluginState.ON

        wvGitHub.settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE

        wvGitHub.loadUrl(Constants.loginUrl + "?client_id=" + Constants.client_id + "&state=" + Constants.state)

        wvGitHub.visibility = View.VISIBLE
    }

    fun checkLoginState() {
        App.getComponent()?.injectLoginActivityPresenter(this)

        doAsync {
            val token: UserToken = mDb.tokensDao().firstToken()

            if (token == null) {
                viewState.startOpening()
            } else {
                viewState.startMainActivity()
            }
        }
    }
}
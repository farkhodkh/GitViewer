package ru.farkhodkhaknazarov.gitviewer.presenters.webcomponents

import android.net.Uri
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import ru.farkhodkhaknazarov.gitviewer.data.api.github.GitHubServiceImpl
import ru.farkhodkhaknazarov.gitviewer.presenters.LoginActivityPresenter

class WebClient : WebViewClient() {
    var authComplete = false

    lateinit var viewPresenter: LoginActivityPresenter

    override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {

        if (url!!.contains("?code=") && authComplete != true) {
            val uri = Uri.parse(url)
            val authCode = uri.getQueryParameter("code")
            val state = uri.getQueryParameter("state")

            GitHubServiceImpl().getGitHubToken(authCode, state)


        }
        return super.shouldInterceptRequest(view, url)
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
    }

    fun setPresenter(loginActivityPresenter: LoginActivityPresenter) {
        viewPresenter = loginActivityPresenter
    }

}
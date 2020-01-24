package ru.farkhodkhaknazarov.gitviewer.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType
import ru.farkhodkhaknazarov.gitviewer.R
import ru.farkhodkhaknazarov.gitviewer.presenters.LoginActivityPresenter
import ru.farkhodkhaknazarov.gitviewer.viewes.LoginView

@StateStrategyType(value = OneExecutionStateStrategy::class)
class LoginActivity : MvpAppCompatActivity(), LoginView {

    @InjectPresenter
    lateinit var presenter: LoginActivityPresenter

//    @ProvidePresenter
//    fun provideLoginActivityPresenter(): LoginActivityPresenter {
//        return LoginActivityPresenter()
//    }

    lateinit var wvGitHub: WebView

    lateinit var loginProgressBar: ProgressBar

    companion object{
        lateinit var context: Context

        fun startMainActivity(){
            val intent = Intent(context, MainActivity::class.java)
            ContextCompat.startActivity(context, intent, null)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        LoginActivity.Companion.context = applicationContext

        presenter.checkLoginState()
    }

    override fun startOpening() {

        wvGitHub = findViewById(R.id.wvGitHub)
        loginProgressBar = findViewById(R.id.loginProgressBar)

        loginProgressBar.visibility = View.INVISIBLE

        runOnUiThread {
            presenter.prepareView(wvGitHub)
        }
    }


    override fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun showMessage(message: Int) {
        Toast.makeText(applicationContext, getString(message), Toast.LENGTH_LONG).show()
    }

    override fun startMainActivity() {
        LoginActivity.startMainActivity()
        finish()
    }
}

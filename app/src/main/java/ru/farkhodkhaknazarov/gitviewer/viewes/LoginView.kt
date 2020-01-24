package ru.farkhodkhaknazarov.gitviewer.viewes

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value= OneExecutionStateStrategy::class)
interface LoginView: MvpView {
    fun startOpening()
    fun showMessage(message: String)
    fun showMessage(message: Int)
    fun startMainActivity()
}
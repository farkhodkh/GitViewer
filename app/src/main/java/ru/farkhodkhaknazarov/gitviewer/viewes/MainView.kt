package ru.farkhodkhaknazarov.gitviewer.viewes

import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value= OneExecutionStateStrategy::class)
interface MainView: MvpView {

    fun checkAppPermission()

    fun showSnakebar(message: String)

    fun setPagerCurrentItem(pageNumber: Int)
}
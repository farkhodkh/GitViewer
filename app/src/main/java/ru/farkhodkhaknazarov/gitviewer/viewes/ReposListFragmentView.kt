package ru.farkhodkhaknazarov.gitviewer.viewes

import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value= OneExecutionStateStrategy::class)
interface ReposListFragmentView {

}
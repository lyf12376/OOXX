package com.yi.xxoo.Const

object Settlement {
    var userPassTime = 0
    var userSubmitTimes = 0
    var enemyPassTime = 0
    var enemySubmitTimes = 0

    fun setUserSettlement(time: Int, submitTimes: Int) {
        userPassTime = time
        userSubmitTimes = submitTimes
    }

    fun setEnemySettlement(time: Int, submitTimes: Int) {
        enemyPassTime = time
        enemySubmitTimes = submitTimes
    }

}
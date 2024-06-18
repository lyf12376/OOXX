package com.yi.xxoo.page.rankPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yi.xxoo.Room.rank.passNum.PassNumRank
import com.yi.xxoo.Room.rank.passNum.PassNumRankDao
import com.yi.xxoo.Room.rank.time.GameTimeRank
import com.yi.xxoo.Room.rank.time.GameTimeRankDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankViewModel @Inject constructor(private val passNumRankDao: PassNumRankDao,private val gameTimeRankDao: GameTimeRankDao):ViewModel(){
    private var passNumRankList = getAllPassNumRankList()
    private var gameTimeRankList = getAllGameTimeRankList()

    val passNumRankListFlow: Flow<List<PassNumRank>> get() = passNumRankList
    val gameTimeRankListFlow: Flow<List<GameTimeRank>> get() = gameTimeRankList

    private fun getAllPassNumRankList(): Flow<List<PassNumRank>> {
        return passNumRankDao.getPassNumRank()
    }

    private fun getAllGameTimeRankList(): Flow<List<GameTimeRank>> {
        return gameTimeRankDao.getGameTimeRank()
    }




}
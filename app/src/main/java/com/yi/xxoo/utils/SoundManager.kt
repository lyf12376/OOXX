package com.yi.xxoo.utils

import android.content.Context
import android.media.SoundPool
import android.util.Log
import com.yi.xxoo.R

object SoundManager {
    private val soundPool :SoundPool = SoundPool.Builder().setMaxStreams(10).build()
    private var soundId: Int? = null

    fun loadSound(context: Context,musicId:Int) {
        soundId = soundPool.load(context, musicId, 1)
        Log.d("TAG", "loadSound: dsadsadasdasdasdsadsadasdasdasdasd")
    }

    fun playSound() {
        soundId?.let {
            soundPool.play(it, 1f, 1f, 0, 0, 1f)
        }
    }

    fun release() {
        soundPool.release()
    }
}
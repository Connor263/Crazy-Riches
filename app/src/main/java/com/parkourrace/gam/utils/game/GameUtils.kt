package com.parkourrace.gam.utils.game

import android.content.Context
import android.media.MediaPlayer
import com.parkourrace.gam.R
import com.parkourrace.gam.data.game.model.Block
import com.parkourrace.gam.utils.game.enums.GameSound
import kotlin.random.Random


fun generateBlock(): Block {
    val rand = Random(System.nanoTime())
    return Block(
        drawableId = 0, type = when ((0..15).random(rand)) {
            in 0..3 -> 0
            in 3..6 -> 1
            in 7..10 -> 2
            in 11..12 -> 3
            in 12..14 -> 4
            else -> 0
        }
    )
}

fun makeSound(context: Context, sound: GameSound) {
    val soundId = when (sound) {
        GameSound.DROP -> listOf(R.raw.drop, R.raw.drop_1, R.raw.drop_2).random()
        GameSound.ROW_MATCH -> listOf(R.raw.smash, R.raw.smash_1).random()
    }
    MediaPlayer.create(context, soundId).apply {
        setOnCompletionListener {
            it.release()
        }
        start()
    }
}
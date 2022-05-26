package com.parkourrace.gam.data.game.model

import androidx.annotation.DrawableRes
import com.parkourrace.gam.utils.TETRIS_COLUMN_SIZE
import com.parkourrace.gam.utils.TETRIS_ROW_SIZE

data class Block(
    @DrawableRes var drawableId: Int,
    var isBlank: Boolean = false,
    var column: Int = TETRIS_COLUMN_SIZE / 2,
    var row: Int = TETRIS_ROW_SIZE,
    var move: Int = 0,
    var type: Int = 0
)

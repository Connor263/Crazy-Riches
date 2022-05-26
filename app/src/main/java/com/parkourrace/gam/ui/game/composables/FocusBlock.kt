package com.parkourrace.gam.ui.game.composables

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.parkourrace.gam.R
import com.parkourrace.gam.ui.game.GameViewModel
import com.parkourrace.gam.utils.TETRIS_COLUMN_SIZE
import com.parkourrace.gam.utils.TETRIS_ROW_SIZE
import com.parkourrace.gam.utils.enums.GameSound
import com.parkourrace.gam.utils.makeSound

@Composable
fun FocusBlock(viewModel: GameViewModel = viewModel()) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val HEIGHT_STEP = remember { (screenHeight.value / TETRIS_ROW_SIZE).toInt() }
    val WIDTH_STEP = remember { (screenWidth.value / TETRIS_COLUMN_SIZE).toInt() }

    val focusBlockIsCreated by remember { viewModel.focusBlockIsCreated }

    var index by remember { mutableStateOf(0) }
    val focusBlockLastX = remember { Animatable(WIDTH_STEP.toFloat() * 3) }
    val focusBlockLastY = remember { Animatable(HEIGHT_STEP * -2F) }

    Box(
        modifier = Modifier
            .size(WIDTH_STEP.dp, HEIGHT_STEP.dp)
            .offset(focusBlockLastX.value.dp, focusBlockLastY.value.dp)

    ) {
        FocusBlockView(
            type = viewModel.currentFocusBlock.value.type,
            widthStep = WIDTH_STEP,
            heightStep = HEIGHT_STEP
        )
    }


    LaunchedEffect(focusBlockIsCreated) {
        if (focusBlockIsCreated) {
            index++
        } else {
            viewModel.createFocusBlock()
        }
    }

    LaunchedEffect(viewModel.getFocusMove()) {
        if (focusBlockIsCreated && !viewModel.quickMoveDown.value) {
            val blocks = viewModel.listOfBlock
            val stepWidth = when (viewModel.getFocusMove()) {
                -1 -> focusBlockLastX.value - WIDTH_STEP
                1 -> focusBlockLastX.value + WIDTH_STEP
                else -> return@LaunchedEffect
            }

            val focusBlock = viewModel.currentFocusBlock.value
            val newColumn = viewModel.getNewColumn()

            val bufferFocusBlockList =
                when (focusBlock.type) {
                    0 -> listOf(focusBlock.copy(column = newColumn))
                    1 -> {
                        listOf(
                            focusBlock.copy(
                                column = newColumn
                            ),
                            focusBlock.copy(
                                column = newColumn,
                                row = focusBlock.row + 1
                            ),
                            focusBlock.copy(
                                column = newColumn + 1,
                            ),
                            focusBlock.copy(
                                column = newColumn + 1,
                                row = focusBlock.row + 1
                            )
                        )
                    }
                    2 -> {
                        listOf(
                            focusBlock.copy(
                                column = newColumn,
                            ),
                            focusBlock.copy(
                                column = newColumn + 1,
                            ),

                            focusBlock.copy(
                                column = newColumn + 1,
                                row = focusBlock.row + 1
                            ),
                            focusBlock.copy(
                                column = newColumn + 2,
                                row = focusBlock.row + 1
                            )
                        )
                    }
                    3 -> {
                        listOf(
                            focusBlock.copy(
                                column = newColumn,
                                row = focusBlock.row + 1
                            ),
                            focusBlock.copy(
                                column = newColumn + 1,
                                row = focusBlock.row + 1
                            ),
                            focusBlock.copy(
                                column = newColumn + 1,
                            ),
                            focusBlock.copy(
                                column = newColumn + 2,
                            ),
                        )
                    }
                    4 -> {
                        listOf(
                            focusBlock.copy(column = newColumn, row = focusBlock.row - 1),
                            focusBlock.copy(column = newColumn, row = focusBlock.row),
                            focusBlock.copy(column = newColumn, row = focusBlock.row + 1)
                        )
                    }
                    else -> return@LaunchedEffect
                }

            var indexValid = 0
            bufferFocusBlockList.forEach { block ->

                if (blocks.any { it.column == block.column && it.row == block.row && !it.isBlank } ||
                    block.column < 0 ||
                    block.column >= TETRIS_COLUMN_SIZE) {
                    return@LaunchedEffect
                } else {
                    indexValid++
                    Log.d("TAG", "FocusBlock: $indexValid")
                    if (indexValid != bufferFocusBlockList.count()) return@forEach
                }

                focusBlockLastX.snapTo(stepWidth)
                viewModel.currentFocusBlock.value.column = newColumn
                viewModel.moveResetSide()
            }
        }
    }

    LaunchedEffect(index) {
        if (focusBlockIsCreated) {
            val stepHeight = focusBlockLastY.value + HEIGHT_STEP
            val result = focusBlockLastY.animateTo(
                targetValue = stepHeight,
                animationSpec = tween(if (viewModel.quickMoveDown.value) 100 else viewModel.moveSpeed)
            )
            when (result.endReason) {
                AnimationEndReason.BoundReached -> {}
                AnimationEndReason.Finished -> {
                    val blocks = viewModel.listOfBlock
                    val focusBlock = viewModel.currentFocusBlock.value
                    val nextRow = viewModel.nextMoveDown()

                    val bufferFocusBlockList =
                        when (focusBlock.type) {
                            0 -> listOf(focusBlock.copy(row = nextRow))
                            1 -> listOf(
                                focusBlock.copy(row = nextRow),
                                focusBlock.copy(row = nextRow, column = focusBlock.column + 1)
                            )
                            2 -> listOf(
                                focusBlock.copy(row = nextRow),
                                focusBlock.copy(row = nextRow, column = focusBlock.column + 1),
                                focusBlock.copy(row = nextRow + 1, column = focusBlock.column + 2),
                            )
                            3 -> {
                                listOf(
                                    focusBlock.copy(row = nextRow + 1),
                                    focusBlock.copy(row = nextRow, column = focusBlock.column + 1),
                                    focusBlock.copy(row = nextRow, column = focusBlock.column + 2),
                                )
                            }
                            4 -> listOf(focusBlock.copy(row = nextRow - 1))

                            else -> return@LaunchedEffect
                        }

                    bufferFocusBlockList.forEach { block ->
                        val blocksForColumn =
                            blocks.filter { it.column == block.column }
                                .sortedByDescending { it.row }.filter { it.row <= block.row }

                        val nextBlock = blocksForColumn.firstOrNull()
                        val row = nextBlock?.row ?: 0
                        val isBlank = nextBlock?.isBlank ?: false

                        if (
                            blocksForColumn.any { it.column == block.column && it.row == block.row && !it.isBlank } ||
                            (block.row <= row && !isBlank)
                        ) {
                            viewModel.addBlock()
                            makeSound(context, GameSound.DROP)

                            viewModel.checkWinRow(block.type, block.row) { smash ->
                                if (smash) {
                                    makeSound(context, GameSound.ROW_MATCH)
                                }
                            }

                            index = 0
                            focusBlockLastX.snapTo(WIDTH_STEP.toFloat() * 3)
                            focusBlockLastY.snapTo(HEIGHT_STEP * -2F)
                            return@LaunchedEffect
                        }
                    }
                    viewModel.moveDown()
                    index++
                }
            }
        }
    }

    LaunchedEffect(viewModel.quickMoveDown.value) {
        if (viewModel.quickMoveDown.value) {
            index++
        }
    }
}
package com.parkourrace.gam.ui.game

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parkourrace.gam.R
import com.parkourrace.gam.data.game.model.Block
import com.parkourrace.gam.data.game.preferences.GamePreferencesDataStore
import com.parkourrace.gam.utils.TETRIS_COLUMN_SIZE
import com.parkourrace.gam.utils.TETRIS_ROW_SIZE
import com.parkourrace.gam.utils.generateBlock
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    var moveSpeed by mutableStateOf(1000)

    var listOfBlock = mutableStateListOf<Block>()
        private set

    var currentFocusBlock =
        mutableStateOf(Block(drawableId = R.drawable.decal_8))
        private set

    var nextFocusBlock = mutableStateOf(Block(drawableId = R.drawable.decal_8))
        private set


    var score = mutableStateOf(0)
        private set


    var quickMoveDown = mutableStateOf(false)
        private set

    var focusBlockIsCreated = mutableStateOf(false)
        private set

    var gameOver = mutableStateOf(false)
        private set


    fun addBlock() {
        val block = currentFocusBlock.value
        when (block.type) {
            0 -> processBlocks(listOf(block.copy(row = block.row)), listOf())

            1 -> {
                val leadBlocks = mutableListOf(block, block.copy(column = block.column + 1))

                val restBlocks =
                    listOf(
                        block.copy(row = block.row + 1),
                        leadBlocks[1].copy(row = block.row + 1)
                    )

                processBlocks(leadBlocks, restBlocks)
            }
            2 -> {
                val leadBlocks = mutableListOf(
                    block,
                    block.copy(column = block.column + 1),
                    block.copy(column = block.column + 2, row = block.row + 1)
                )
                val restBlocks =
                    listOf(
                        block.copy(row = block.row + 1, column = block.column + 1),
                    )
                processBlocks(leadBlocks, restBlocks)
            }
            3 -> {
                val leadBlocks = mutableListOf(
                    block.copy(row = block.row + 1),
                    block.copy(column = block.column + 1),
                    block.copy(column = block.column + 2),

                    )
                val restBlocks =
                    listOf(
                        block.copy(row = block.row + 1, column = block.column + 1),
                    )
                processBlocks(leadBlocks, restBlocks)
            }
            4 -> {
                val leadBlocks = mutableListOf(block.copy(row = block.row - 1))
                val restBlocks =
                    listOf(
                        block.copy(row = block.row + 1),
                        block.copy(row = block.row),
                    )
                processBlocks(leadBlocks, restBlocks)
            }
        }

        checkWinRow(block.type, block.row)
        focusBlockIsCreated.value = false
        disableQuickMoveDown()
        createFocusBlock()
    }

    private fun processBlocks(leadBlocks: List<Block>, restBlocks: List<Block>) {
        val listToDelete = mutableListOf<Block>()
        val resultList = mutableListOf<Block>()
        resultList.addAll(leadBlocks)
        resultList.addAll(restBlocks)
        if (resultList.any { it.row >= TETRIS_ROW_SIZE }) {
            gameOver.value = true
            return
        }

        resultList.forEach { currentBlock ->
            if (listOfBlock.any {
                    it.column == currentBlock.column &&
                            it.row == currentBlock.row &&
                            it.isBlank
                }) {
                val block = listOfBlock.find {
                    it.column == currentBlock.column &&
                            it.row == currentBlock.row &&
                            it.isBlank
                }

                val index = listOfBlock.indexOf(block)
                listOfBlock.remove(block)
                listOfBlock.add(index, currentBlock)
                listToDelete.add(currentBlock)
            }
        }
        resultList.removeAll(listToDelete)

        leadBlocks.forEach { lead ->
            val leadColumn =
                listOfBlock.filter { it.column == lead.column }.sortedByDescending { it.row }
            val peekRow = leadColumn.firstOrNull()?.row ?: 0

            if (lead.row > peekRow + 1) {
                repeat(lead.row - peekRow - 1) { index ->
                    val emptyBlock = lead.copy(
                        isBlank = true,
                        row = peekRow + index + 1
                    )
                    if (!listOfBlock.any { it.column == emptyBlock.column && it.row == emptyBlock.row && it.isBlank }) {
                        resultList.add(emptyBlock)
                    }
                }
            }
        }
        resultList.sortBy { it.row }
        listOfBlock.addAll(resultList)
    }

    private fun checkWinRow(type: Int, row: Int) {
        val coef = when (type) {
            0 -> 0
            1 -> 1
            2 -> 1
            3 -> 1
            4 -> 3
            else -> 0
        }
        for (n in row + coef downTo 0) {
            val blockInRow = listOfBlock.filter { it.row == n && !it.isBlank }
            if (blockInRow.count() == TETRIS_COLUMN_SIZE) {
                increaseScore()
                removeBlocks(blockInRow)
                for (i in n + 1..TETRIS_ROW_SIZE) {
                    listOfBlock.filter { it.row == i }.forEach {
                        it.row--
                    }
                }
            }
        }
    }

    private fun removeBlocks(list: List<Block>) {
        listOfBlock.removeAll(list)
    }

    fun createFocusBlock() {
        focusBlockIsCreated.value = true
        currentFocusBlock.value = nextFocusBlock.value
        currentFocusBlock.value.drawableId = when (currentFocusBlock.value.type) {
            0 -> R.drawable.decal_1
            1 -> R.drawable.decal_2
            2 -> R.drawable.decal_3
            3 -> R.drawable.decal_4
            4 -> R.drawable.decal_5
            else -> R.drawable.decal_1
        }

        nextFocusBlock.value = generateBlock()
    }

    private fun increaseScore() {
        score.value += 50
    }

    fun moveLeftSide() {
        currentFocusBlock.value.move = -1
    }

    fun moveResetSide() {
        currentFocusBlock.value.move = 0
    }

    fun moveRightSide() {
        currentFocusBlock.value.move = 1
    }

    fun moveDown() = currentFocusBlock.value.row--


    fun nextMoveDown() = currentFocusBlock.value.row - 1

    fun getNewColumn(): Int {
        val focusMove = getFocusMove()
        moveResetSide()
        return currentFocusBlock.value.column + focusMove
    }


    fun getFocusMove() = currentFocusBlock.value.move


    fun enableQuickMoveDown() {
        quickMoveDown.value = true
    }

    private fun disableQuickMoveDown() {
        quickMoveDown.value = false
    }

    fun setLevel(context: Context) = viewModelScope.launch {
        val level = GamePreferencesDataStore(context).level.first()
        moveSpeed = 1000 / (if (level - 1 == 0) 1 else level * 2)
    }
}
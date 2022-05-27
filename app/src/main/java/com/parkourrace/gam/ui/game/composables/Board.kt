package com.parkourrace.gam.ui.game.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.parkourrace.gam.R
import com.parkourrace.gam.ui.game.GameViewModel
import com.parkourrace.gam.utils.game.TETRIS_COLUMN_SIZE


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Board() {
    val viewModel: GameViewModel = viewModel()
    val blocks = viewModel.listOfBlock

    Box(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxSize()) {
            repeat(TETRIS_COLUMN_SIZE) { columnIndex ->
                LazyColumn(
                    reverseLayout = true,
                    state = rememberLazyListState(),
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1 / TETRIS_COLUMN_SIZE.toFloat())
                ) {
                    val columnBlocks = blocks.filter { it.column == columnIndex }

                    items(columnBlocks) {
                        Image(
                            modifier = Modifier
                                .size(50.dp),
                            painter = if (it.isBlank) {
                                painterResource(id = R.drawable.decal_8)
                            } else {
                                painterResource(id = it.drawableId)
                            },
                            alpha = if (it.isBlank) 0F else 1F,
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }

    FocusBlock()
}
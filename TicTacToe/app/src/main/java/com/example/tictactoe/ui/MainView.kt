package com.example.tictactoe.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.tictactoe.ViewModel
import com.example.tictactoe.ui.components.Board
import com.example.tictactoe.ui.components.StartNewGame
import com.example.tictactoe.ui.components.StateDescriptor

@Composable
fun GameView(viewModel: ViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        StateDescriptor(viewModel)
        Board(viewModel)
        StartNewGame(viewModel)
    }
}
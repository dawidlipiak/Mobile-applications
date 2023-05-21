package com.example.tictactoe.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoe.ViewModel

import com.example.tictactoe.model.utils.FieldState


@Composable
fun Board(viewModel: ViewModel) {
    val padding = 3

    val boardSize by viewModel.boardSize.observeAsState(3)

    val boardDimension = kotlin.math.min(
        LocalConfiguration.current.screenWidthDp, LocalConfiguration.current.screenHeightDp
    )
    val buttonDimension = (boardDimension - 2 * boardSize * padding) / boardSize

    Column(
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for (i in 0 until boardSize) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (j in 0 until boardSize) {
                    BoardField(
                        size = buttonDimension,
                        padding = padding,
                        viewModel = viewModel,
                        index = i * boardSize + j
                    ) {
                        viewModel.makeMove(i * boardSize + j)
                    }
                }
            }
        }
    }
}

@Composable
fun BoardField(
    viewModel: ViewModel, index: Int, size: Int, padding: Int, onclick: () -> Unit
) {
    var buttonText by remember { mutableStateOf("") }
    val turn by viewModel.turnNumber.observeAsState(0)

    LaunchedEffect(turn) {
        val fieldState = viewModel.boardFields.value!![index]
        buttonText = when (fieldState) {
            FieldState.EMPTY -> ""
            FieldState.X -> "X"
            FieldState.O -> "O"
        }
    }

    Box(
        modifier = Modifier
            .padding(padding.dp)
            .width(size.dp)
            .height(size.dp)
    ) {
        Button(
            modifier = Modifier
                .width(size.dp)
                .height(size.dp),
            shape = MaterialTheme.shapes.medium,
            onClick = onclick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.LightGray
            )
        ) {}
        Text(
            text = buttonText,
            fontSize = (size/1.5).sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(0.dp)
        )
    }
}

package com.neurosky.zonetrainer.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neurosky.zonetrainer.ui.theme.NeuroTheme
import kotlin.random.Random

@Composable
fun MultiplicationTable(modifier: Modifier = Modifier) {
    val random = Random(System.currentTimeMillis())

    var x: Int by mutableStateOf(getRandomNumber(random))
    var y: Int by mutableStateOf(getRandomNumber(random))
    var table by mutableStateOf(getRandomTable(random, x, y))

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        QuestionPanel(x, y)
        Spacer(modifier = Modifier.height(24.dp))
        NumberTable(
            numbers = table,
            answer = x * y,
            onCorrect = {
                x = getRandomNumber(random)
                y = getRandomNumber(random)
                table = getRandomTable(random, x, y)
            }
        )
    }
}

@Composable
fun QuestionPanel(
    x: Int,
    y: Int
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White
    ) {
        Text(
            "$x  ×  $y  =  ?",
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 48.dp)
        )
    }
}

@Composable
fun NumberTable(
    numbers: List<Int>,
    answer: Int,
    onCorrect: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.Transparent
    ) {
        LazyVerticalGrid(columns = GridCells.Fixed(TABLE_SIZE)) {
            items(numbers) { number ->
                ElevatedButton(
                    onClick = { if (number == answer) onCorrect() },
                    modifier = Modifier.aspectRatio(1F),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    Text(number.toString(), maxLines = 1, overflow = TextOverflow.Visible)
                }
            }
        }
    }
}

fun getRandomNumber(random: Random): Int = random.nextInt(2, 10)

fun getRandomTable(random: Random, x: Int, y: Int): MutableList<Int> {
    val table =
        MutableList(TABLE_SIZE * TABLE_SIZE) { getRandomNumber(random) * getRandomNumber(random) }
    if (!table.contains(x * y)) {
        table[random.nextInt(TABLE_SIZE * TABLE_SIZE)] = x * y
    }
    return table
}

private const val TABLE_SIZE = 6

@Preview
@Composable
private fun MultiplicationTablePreview() {
    NeuroTheme {
        MultiplicationTable(modifier = Modifier.padding(16.dp))
    }
}

package com.example.simplecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplecalculator.ui.theme.SimpleCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Composable
fun App() {
    var calcText by remember {
        mutableStateOf("")
    }
    var operator by remember {
        mutableStateOf("")
    }
    val buttons = listOf(
        listOf("AC", "0", "=", "+"),
        listOf("7", "8", "9", "-"),
        listOf("4", "5", "6", "*"),
        listOf("1", "2", "3", "/"))
    SimpleCalculatorTheme {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = calcText,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 30.dp),
                    fontWeight = FontWeight.Light,
                    fontSize = 80.sp,
                    color = Color.Black,
                    maxLines = 2
                )
                buttons.forEach {
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        it.forEach {
                            SingleButton(s = it,
                                modifier = Modifier
                                    .background(handleColor(it))
                                    .aspectRatio(1f)
                                    .weight(1f),
                                onClick = {
                                    when (it) {
                                        "=" -> calcText = calculate(calcText, operator)
                                        "AC" -> calcText = ""
                                        else -> {
                                            calcText += it
                                            when (it) {
                                                "+" -> operator = "+"
                                                "-" -> operator = "-"
                                                "*" -> operator = "*"
                                                "/" -> operator = "/"
                                                else -> {}
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

fun handleColor(it: String): Color {
    return when (it) {
        "+" -> Color.Blue
        "-" -> Color.Blue
        "*" -> Color.Blue
        "/" -> Color.Blue
        "AC" -> Color.Red
        "=" -> Color.Magenta
        else -> Color.DarkGray
    }
}

fun calculate(calcText: String, operator: String): String {
    val index = calcText.indexOf(operator)
    var number1 = calcText.substring(0, index).toInt()
    val number2 = calcText.substring(index + 1).toInt()

    when (operator) {
        "+" -> number1 += number2
        "-" -> number1 -= number2
        "*" -> number1 *= number2
        "/" -> number1 /= number2
    }
    return number1.toString()
}


@Composable
fun SingleButton(s: String, modifier: Modifier, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .then(modifier)
    ) {
        Text(text = s, fontSize = 36.sp, color = Color.White)
    }
}





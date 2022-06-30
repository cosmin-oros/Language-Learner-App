package com.example.languagelearnerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.languagelearnerapp.ui.theme.LanguageLearnerAppTheme
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LanguageLearnerAppTheme {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                var textFieldState by remember{
                    mutableStateOf("")
                }
                val wordsList = ListOfWords.getData()
                var word by remember{
                    mutableStateOf(GetRandomWord(wordsList = wordsList).randomWord())
                }
                var percentage: Float = 0F

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ){
                            Text(text = word.ita)
                        }

                        Spacer(modifier = Modifier.height(32.dp))

                        OutlinedTextField(
                            value = textFieldState,
                            label = {
                                Text(text = "Enter the translation")
                            },
                            onValueChange = {
                                textFieldState = it
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth(0.6f),
                            textStyle = TextStyle(color = Color.LightGray, fontSize = 20.sp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text
                            )

                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Row(

                        ) {
                            Button(onClick = {
                                if (textFieldState.lowercase(Locale.getDefault()) == word.en.lowercase(
                                        Locale.getDefault()) ||
                                    (word.ita == "da" &&
                                            (textFieldState.lowercase(Locale.getDefault()) == "from" || textFieldState.lowercase(Locale.getDefault()) == "by")) ||
                                    (word.ita == "come" &&
                                            (textFieldState.lowercase(Locale.getDefault()) == "how" || textFieldState.lowercase(Locale.getDefault()) == "as")) ||
                                    (word.ita == "grande" &&
                                            (textFieldState.lowercase(Locale.getDefault()) == "large" || textFieldState.lowercase(Locale.getDefault()) == "big")) ||
                                    (word.ita == "noi" &&
                                            (textFieldState.lowercase(Locale.getDefault()) == "us" || textFieldState.lowercase(Locale.getDefault()) == "we")) ||
                                    (word.ita == "terra" &&
                                            (textFieldState.lowercase(Locale.getDefault()) == "earth" || textFieldState.lowercase(Locale.getDefault()) == "land"))){
                                    scope.launch {
                                        word = GetRandomWord(wordsList = wordsList).randomWord()
                                        textFieldState = ""
                                        if (percentage < 100){
                                            percentage += 0.01F
                                        }
                                    }
                                }else{
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar("Try again")
                                        textFieldState = ""
                                        if (percentage > 0){
                                            percentage -= 0.01F
                                        }
                                    }
                                }
                            },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.Transparent,
                                    contentColor = Color.LightGray

                                ),
                                modifier = Modifier
                                    .border(
                                        width = 5.dp,
                                        brush = Brush.horizontalGradient(
                                            listOf(
                                                Color.Magenta,
                                                Color.Yellow
                                            )
                                        ),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .width(100.dp)
                                    .background(
                                        Brush.horizontalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                Color.Transparent
                                            ),
                                            startX = 150f
                                        )
                                    )
                            ) {
                                Text(text = "Check")
                            }

                            Spacer(modifier = Modifier.width(32.dp))

                            Button(onClick = {
                                    scope.launch {
                                        word = GetRandomWord(wordsList = wordsList).randomWord()
                                        textFieldState = ""
                                        percentage = 0F
                                    }
                            },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.Transparent,
                                    contentColor = Color.LightGray

                                ),
                                modifier = Modifier
                                    .border(
                                        width = 5.dp,
                                        brush = Brush.horizontalGradient(
                                            listOf(
                                                Color.Magenta,
                                                Color.Yellow
                                            )
                                        ),
                                        shape = RoundedCornerShape(15.dp)
                                    )
                                    .width(100.dp)
                                    .background(
                                        Brush.horizontalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                Color.Transparent
                                            ),
                                            startX = 150f
                                        )
                                    )
                            ) {
                                Text(text = "Skip")
                            }
                        }

                        Spacer(modifier = Modifier.size(64.dp))

                        CircularProgressBar(percentage = percentage, number = 100)

                    }

                }

            }
        }
    }
}

class GetRandomWord(val wordsList: ArrayList<LanguageData>){
    fun randomWord(): LanguageData{
        return wordsList.random()
    }
}

@Composable
fun CircularProgressBar(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = Color.Yellow,
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
){
    var animationPlayed by remember{
        mutableStateOf(false)
    }

    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed){
            percentage
        }else{
            0f
        },
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )

    LaunchedEffect(key1 = true){
        animationPlayed = true
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2f)
    ){
        Canvas(modifier = Modifier.size(radius * 2f)){
            drawArc(
                color = color,
                -90f,
                360 * curPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = (curPercentage.value * number).toInt().toString(),
            color = Color.Yellow,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}
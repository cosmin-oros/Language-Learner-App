package com.example.languagelearnerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.languagelearnerapp.ui.theme.LanguageLearnerAppTheme
import com.example.languagelearnerapp.ui.theme.ListOfWords
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
                                    }
                                }else{
                                    scope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar("Try again")
                                        textFieldState = ""
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
package com.github.jahongk.week2_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import coil.annotation.ExperimentalCoilApi
import com.github.jahongk.week2_1.ui.theme.Week21Theme

@ExperimentalCoilApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week21Theme {
                //LayoutCodelab()
                //SimpleList()
                //lazyList()
                //ScrollingList()
                BodyContent4()
            }
        }
    }
}



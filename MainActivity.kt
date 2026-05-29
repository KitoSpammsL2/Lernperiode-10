package com.example.rocket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SpaceClickerGame()
        }
    }
}

@Composable
fun SpaceClickerGame() {

    var score by remember { mutableStateOf(0) }
    var pointsPerClick by remember { mutableStateOf(1) }
    var upgradeCost by remember { mutableStateOf(10) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101830)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "🚀 Space Clicker 🚀",
                fontSize = 32.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Punkte: $score",
                fontSize = 28.sp,
                color = Color.White
            )

            Text(
                text = "Pro Klick: $pointsPerClick",
                fontSize = 18.sp,
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    score += pointsPerClick
                }
            ) {
                Text("🚀 Rakete starten")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (score >= upgradeCost) {
                        score -= upgradeCost
                        pointsPerClick++
                        upgradeCost += 10
                    }
                }
            ) {
                Text("Upgrade kaufen ($upgradeCost)")
            }
        }
    }
}

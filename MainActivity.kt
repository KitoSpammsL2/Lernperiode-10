package com.example.rocket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

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
    var upgradeCost by remember { mutableStateOf(25) }

    var passiveIncome by remember { mutableStateOf(0) }
    var passiveCost by remember { mutableStateOf(50) }

    var clicked by remember { mutableStateOf(false) }

    LaunchedEffect(passiveIncome) {
        while (true) {
            delay(1000)
            score += passiveIncome
        }
    }

    val rocketScale by animateFloatAsState(
        targetValue = if (clicked) 1.18f else 1f,
        animationSpec = tween(durationMillis = 120),
        label = "rocketScale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF020617),
                        Color(0xFF111827),
                        Color(0xFF312E81),
                        Color(0xFF581C87)
                    )
                )
            )
    ) {
        StarField()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 45.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "SPACE CLICKER",
                fontSize = 34.sp,
                fontWeight = FontWeight.Black,
                color = Color.White,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Sammle Energie für deine Rakete",
                fontSize = 14.sp,
                color = Color(0xFFC4B5FD),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            GlassCard {
                Text(
                    text = "⚡ $score",
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "+$pointsPerClick pro Klick   •   +$passiveIncome pro Sekunde",
                    fontSize = 15.sp,
                    color = Color(0xFFD8B4FE),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(35.dp))

            Box(
                modifier = Modifier
                    .size(230.dp)
                    .scale(rocketScale)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF7C3AED),
                                Color(0xFF312E81),
                                Color.Transparent
                            )
                        ),
                        CircleShape
                    )
                    .border(
                        width = 2.dp,
                        brush = Brush.linearGradient(
                            listOf(
                                Color(0xFFA78BFA),
                                Color(0xFF22D3EE)
                            )
                        ),
                        shape = CircleShape
                    )
                    .clickable {
                        score += pointsPerClick
                        clicked = !clicked
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "🚀",
                        fontSize = 95.sp
                    )

                    Text(
                        text = "TAP",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 4.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(35.dp))

            UpgradeButton(
                title = "Klick-Power verbessern",
                subtitle = "+1 Energie pro Klick",
                price = upgradeCost,
                enabled = score >= upgradeCost,
                onClick = {
                    if (score >= upgradeCost) {
                        score -= upgradeCost
                        pointsPerClick += 1
                        upgradeCost += 25
                    }
                }
            )

            Spacer(modifier = Modifier.height(14.dp))

            UpgradeButton(
                title = "Auto-Miner kaufen",
                subtitle = "+1 Energie pro Sekunde",
                price = passiveCost,
                enabled = score >= passiveCost,
                onClick = {
                    if (score >= passiveCost) {
                        score -= passiveCost
                        passiveIncome += 1
                        passiveCost += 50
                    }
                }
            )
        }
    }
}

@Composable
fun StarField() {
    Box(modifier = Modifier.fillMaxSize()) {
        Star("✦", 35.dp, 70.dp, 18.sp)
        Star("✧", 290.dp, 95.dp, 14.sp)
        Star("✦", 80.dp, 180.dp, 12.sp)
        Star("✧", 330.dp, 250.dp, 18.sp)
        Star("✦", 45.dp, 410.dp, 14.sp)
        Star("✧", 305.dp, 520.dp, 12.sp)
        Star("✦", 160.dp, 610.dp, 16.sp)
        Star("✧", 250.dp, 700.dp, 14.sp)
    }
}

@Composable
fun Star(symbol: String, x: androidx.compose.ui.unit.Dp, y: androidx.compose.ui.unit.Dp, size: androidx.compose.ui.unit.TextUnit) {
    Text(
        text = symbol,
        color = Color.White.copy(alpha = 0.55f),
        fontSize = size,
        modifier = Modifier
            .offset(x = x, y = y)
    )
}

@Composable
fun GlassCard(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White.copy(alpha = 0.10f),
                shape = RoundedCornerShape(28.dp)
            )
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.18f),
                shape = RoundedCornerShape(28.dp)
            )
            .padding(22.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}

@Composable
fun UpgradeButton(
    title: String,
    subtitle: String,
    price: Int,
    enabled: Boolean,
    onClick: () -> Unit
) {
    val background = if (enabled) {
        Brush.horizontalGradient(
            listOf(
                Color(0xFF7C3AED),
                Color(0xFF06B6D4)
            )
        )
    } else {
        Brush.horizontalGradient(
            listOf(
                Color(0xFF374151),
                Color(0xFF4B5563)
            )
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(background, RoundedCornerShape(22.dp))
            .clickable(enabled = enabled) {
                onClick()
            }
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = subtitle,
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.78f)
            )
        }

        Text(
            text = "⚡ $price",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

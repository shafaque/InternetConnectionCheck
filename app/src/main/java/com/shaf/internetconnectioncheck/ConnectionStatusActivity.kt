package com.shaf.internetconnectioncheck

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shaf.internetconnectioncheck.ui.theme.InternetConnectionCheckTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InternetConnectionCheckTheme {
                val viewModel = viewModel<ConnectivityViewModel> {
                    ConnectivityViewModel(
                        connectivityObserver = AndroidConnectivityObserver(
                            context = applicationContext
                        )
                    )
                }
                val isConnected by viewModel.isConnected.collectAsStateWithLifecycle()
                Scaffold(modifier = Modifier.fillMaxSize()) {_ ->
                   ConnectionStatus(isConnected)
                }
            }
        }
    }
}

@Composable
fun ConnectionStatus(isConnected: Boolean) {
    // State for animation
    val pulseAnim = rememberInfiniteTransition(label = "anim")
    val circleSize by pulseAnim.animateFloat(
        initialValue = 50f,
        targetValue = 70f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    // State for circle color
    val circleColor by animateColorAsState(
        targetValue = if (isConnected) Color.Green else Color.Red,
        animationSpec = tween(durationMillis = 500), label = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Or use innerPadding if necessary
        contentAlignment = Alignment.Center
    ) {
        // Animated Canvas for Pulsating Circle
        Canvas(modifier = Modifier.size(120.dp)) {
            drawCircle(
                color = circleColor,
                radius = circleSize,
                style = Stroke(width = 4.dp.toPx()) // Use stroke for a hollow effect
            )
        }

        // Text in the Center
        Text(
            text = "$isConnected",
            modifier = Modifier
                .padding(8.dp),
            textAlign = TextAlign.Center
        )
    }
}



@Preview(showBackground = true)
@Composable
fun ConnectionStatusPreview() {
    InternetConnectionCheckTheme {
        ConnectionStatus(isConnected = false)
    }
}
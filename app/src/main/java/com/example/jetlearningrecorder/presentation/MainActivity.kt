package com.example.jetlearningrecorder.presentation

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetlearningrecorder.ui.theme.JetLearningRecorderTheme


class MainActivity : ComponentActivity() {
    private var permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO)
    private var permissioGranted = false

    companion object {
        private const val REQUEST_CODE = 123
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetLearningRecorderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }

        permissioGranted = ActivityCompat.checkSelfPermission(
            this,
            permissions[0]
        ) == PackageManager.PERMISSION_GRANTED
        if (!permissioGranted)
            ActivityCompat.requestPermissions(this@MainActivity, permissions, REQUEST_CODE)
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun MainApp() {

    val viewModel: MainViewModel = hiltViewModel()
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(16.dp)

        ) {
            RoundButton(onClick = {
                viewModel.startRecording(context)
                Toast.makeText(context, "recording started", Toast.LENGTH_LONG).show()
            })

            Button(onClick = {
                viewModel.stoppedRecording()
                Toast.makeText(context, "STOPPED", Toast.LENGTH_LONG).show()

            }) {
                Text(text = "stop recording")

            }

        }


    }

}


@Composable
fun RoundButton(onClick: () -> Unit) {
    Canvas(
        modifier = Modifier
            .size(100.dp)
            .clickable(onClick = onClick),

        onDraw = { drawCircle(color = Color.Red) })
}



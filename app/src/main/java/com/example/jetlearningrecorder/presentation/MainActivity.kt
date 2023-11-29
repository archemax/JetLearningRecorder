package com.example.jetlearningrecorder.presentation

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jetlearningrecorder.R
import com.example.jetlearningrecorder.navigation.AppNavGraph
import com.example.jetlearningrecorder.navigation.Screen
import com.example.jetlearningrecorder.ui.theme.JetLearningRecorderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navHostController = rememberNavController()
                    StartScreen(navHostController)
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

@Composable
fun StartScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    AppNavGraph(
        navHostController = navHostController,
        homeScreenContent = { MainApp(navHostController) },
        listScreenContent = { RecordsListScreen() })

}


@SuppressLint("SuspiciousIndentation")
@Composable
fun MainApp(navController: NavHostController) {

    val viewModel: MainViewModel = hiltViewModel()
    val context = LocalContext.current
    var isRecording by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(22.dp)
                .background(color = Color.Green)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center


        ) {
            if (isRecording) {
                StopButton(onClick = {
                    viewModel.stoppedRecording()
                    viewModel.insertAudioFileToDb()
                    viewModel.playLastRecordedAudioFile()
                    isRecording = false

                })
            } else {
                RecordButton(onClick = {
                    isRecording = true
                    viewModel.startRecording(context)
                    Toast.makeText(
                        context, "recording started", Toast.LENGTH_LONG
                    ).show()
                })
            }
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp, top = 16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Top
    ) {
        Image(painter = painterResource(id = R.drawable.menu_menu_44),
            contentDescription = null,
            modifier = Modifier.clickable{
                navController.navigate(Screen.ListScreen.route)
            } )

    }

}




@Composable
fun RoundButton(onClick: () -> Unit, buttonColor: Color) {
    Canvas(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(50))
            .clickable(onClick = onClick),
        onDraw = { drawCircle(color = buttonColor) })
}

@Composable
fun RecordButton(onClick: () -> Unit) {
    RoundButton(onClick = onClick, buttonColor = Color.Red)
}

@Composable
fun StopButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .clickable(onClick = onClick)
            .size(100.dp)
            .background(Color.Gray, shape = CircleShape),

        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.stop_square_24),
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
    }

}
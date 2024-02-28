@file:OptIn(
    ExperimentalTime::class, ExperimentalTime::class, ExperimentalTime::class,
    ExperimentalTime::class
)

package com.arche.jetlearningrecorder.presentation


import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.arche.jetlearningrecorder.R
import com.arche.jetlearningrecorder.navigation.AppNavGraph
import com.arche.jetlearningrecorder.navigation.Screen
import com.arche.jetlearningrecorder.ui.theme.JetLearningRecorderTheme
import com.example.jetlearningrecorder.presentation.RecordsListScreen
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint
import kotlin.time.ExperimentalTime

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO)
    private var permissioGranted = false

    companion object {
        private const val REQUEST_CODE = 123
    }

    @OptIn(ExperimentalTime::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetLearningRecorderTheme {


                
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navHostController = rememberNavController()
                    val viewModel: MainViewModel = hiltViewModel()
                    StartScreen(navHostController, viewModel)

                    MobileAds.initialize(this)
                    //ShowAds(modifier = Modifier.fillMaxSize(), adId ="ca-app-pub-3940256099942544/6300978111" )
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

@OptIn(ExperimentalTime::class)
@Composable
fun StartScreen(navHostController: NavHostController, viewModel: MainViewModel) {
    val context = LocalContext.current
    AppNavGraph(
        navHostController = navHostController,
        homeScreenContent = { MainApp(navHostController, viewModel) },
        listScreenContent = { RecordsListScreen(navController = navHostController) })

}

@OptIn(ExperimentalTime::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun MainApp(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    var isRecording by remember { mutableStateOf(false) }


    val isPlayingLastRecordedAudio = viewModel.isPlayingLastRecordedAudio.observeAsState(false)
    //Log.d("Ma_state", "${isPlayingLastRecordedAudio.value}")
    var recorderCurrentMode by remember { mutableStateOf("") }



    LaunchedEffect(isPlayingLastRecordedAudio.value) {
        // Reset recorderCurrentMode when isPlayingLastRecordedAudio changes
        if (!isPlayingLastRecordedAudio.value) {
            recorderCurrentMode = ""
        }
    }

    Row (
        modifier = Modifier.fillMaxWidth()
            ){
        ShowAds(modifier = Modifier, adId = "ca-app-pub-4187563943965256/9435520160")

    }


    Column(
        // the main column
        modifier = Modifier
            .fillMaxSize()
        //    .background(Color.Green)
        ,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .padding(bottom = 200.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(viewModel.hours, fontSize = 30.sp)
            Text(":", fontSize = 30.sp)
            Text(viewModel.minutes, fontSize = 30.sp)
            Text(":", fontSize = 30.sp)
            Text(viewModel.seconds, fontSize = 30.sp)


        }


        Text(

            text = recorderCurrentMode,
            style = TextStyle(Color.Gray),
            fontSize = 20.sp
        )

        Row(
            modifier = Modifier
                .padding(22.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center

        ) {
            if (isRecording) {
                StopButton(
                    onClick = {

                        viewModel.insertFileToDbAndPlayIt()
                        isRecording = false
                        viewModel.stoppedRecording()
                        viewModel.stopTimer()
                        recorderCurrentMode = "playing back..."

                    },

                    )
            } else {
                RecordButton(
                    onClick = {
                        isRecording = true
                        viewModel.startRecording(context)
                        recorderCurrentMode = "recording..."
                        viewModel.startTimer()
                    },
                    enabled = !isPlayingLastRecordedAudio.value,
                    contentLabel = "record button"


                )


            }
        }








        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 22.dp, end = 22.dp),
                //.background(Color.Red)

            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top
        ) {

            Image(painter = painterResource(id = R.drawable.menu_menu_44),
                contentDescription = null,
                modifier = Modifier.clickable {
                    navController.navigate(Screen.ListScreen.route)
                })

        }

    }


}
//////////////////////////////////////////////////////////////////////////////////////////////////


@Composable
fun ShowAds(
    modifier: Modifier,
    adId: String
) {
    Column() {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = {context->
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
                    adUnitId = adId
                    loadAd(AdRequest.Builder().build())
                }

            }
        )
    }

}

@Composable
fun RoundButton(
    onClick: () -> Unit,
    buttonColor: Color,
    enabled: Boolean,
    contentLabel: String


) {
    Canvas(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(50))
            .clickable(onClick = onClick, enabled = enabled),
        onDraw = { drawCircle(color = if (enabled) buttonColor else Color.Gray) })

}

@Composable
fun RecordButton(
    onClick: () -> Unit,
    buttonColor: Color = Color.Red,
    enabled: Boolean,
    contentLabel: String

) {
    RoundButton(
        onClick = onClick,
        buttonColor = buttonColor,
        enabled = enabled,
        contentLabel = contentLabel

    )
}


@Composable
fun StopButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .clickable {
                onClick()
            }
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



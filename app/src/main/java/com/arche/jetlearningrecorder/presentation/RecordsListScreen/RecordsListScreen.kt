package com.example.jetlearningrecorder.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.arche.jetlearningrecorder.domain.model.AudioFile
import com.arche.jetlearningrecorder.presentation.RecordsListScreen.RecordsListScreenViewModel


@Composable
fun RecordsListScreen(
    viewModel: RecordsListScreenViewModel = hiltViewModel(),

    ) {
    val audioFilesState = viewModel.audioFilesState.collectAsState()
    val openDialog = remember { mutableStateOf(false) }

    LaunchedEffect(viewModel) {
        viewModel.getAudioFiles()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "List of Records: ",
                modifier = Modifier.padding(8.dp),
                fontSize = 22.sp
            )
            Button(onClick = { openDialog.value = true }) {
                Text(text = "Clear all")
            }
        }
        RecordList(audioFilesState.value, viewModel = viewModel)

        if (openDialog.value) {
            OpenDeleteAllDialog(onConfirm = {
                viewModel.deleteAllAudioFiles()
                openDialog.value = false
            },
                onDismiss = { openDialog.value = false }
            )


        }
    }
}

@Composable
fun RecordList(
    audioFiles: List<AudioFile>,
    viewModel: RecordsListScreenViewModel
) {
    LazyColumn {
        items(audioFiles.reversed()) { myFile ->
            ListItem(myFile, viewModel = viewModel)
        }
    }
}

@Composable
fun ListItem(
    audioFile: AudioFile,
    viewModel: RecordsListScreenViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { viewModel.playSelectedAudioFile(audioFile.id!!) }
    ) {
        Text(text = "File: ${audioFile.id} - ")
        Text(text = "Title: ${audioFile.title}")


    }
    Divider(color = Color.Gray, modifier = Modifier.height(2.dp))
}

@Composable
fun OpenDeleteAllDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Delete all files?") },
        text = { Text(text = "this will delete all your files") },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()

            }) {
                Text("Delete All Files?")

            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "Cancel")
            }
        }


    )


}


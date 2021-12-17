package kz.gvsx.fullscreendialogbugsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kz.gvsx.fullscreendialogbugsample.ui.theme.FullScreenDialogBugSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FullScreenDialogBugSampleTheme {
                var showFullScreenDialog by rememberSaveable { mutableStateOf(false) }

                if (showFullScreenDialog)
                    FullScreenDialog(onDismissRequest = { showFullScreenDialog = false })

                App(onClick = { showFullScreenDialog = true })
            }
        }
    }
}

@Composable
fun App(onClick: () -> Unit) = Surface(
    modifier = Modifier.fillMaxSize(),
    color = Color.Green
) {
    Box {
        Button(onClick = onClick) {
            Text("Open Full Screen Dialog")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FullScreenDialog(onDismissRequest: () -> Unit) = Dialog(
    onDismissRequest = onDismissRequest,
    properties = DialogProperties(usePlatformDefaultWidth = false)
) {
    var width by rememberSaveable { mutableStateOf(0) }
    var height by rememberSaveable { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .onGloballyPositioned {
                width = it.size.width
                height = it.size.height
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Full Screen Dialog\nwidth:$width\nheight:$height",
            color = Color.White
        )
    }
}

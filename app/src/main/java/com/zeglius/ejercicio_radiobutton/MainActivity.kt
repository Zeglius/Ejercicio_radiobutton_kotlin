package com.zeglius.ejercicio_radiobutton

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zeglius.ejercicio_radiobutton.ui.theme.Ejercicio_radiobuttonTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ejercicio_radiobuttonTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun MainScreen() {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {

        MySpacer()
        PermanentIcon()
        MySpacer()
        Fragmento1()
        MySpacer()
        Fragmento2()
        MySpacer()
        Fragment3()
        MySpacer()
        Fragment4()

    }
}

@Composable
private fun MySpacer() {
    Spacer(modifier = Modifier.height(20.dp))
}

/**
 * 4. Un icono de tu elección que se mostrará siempre en la interfaz
 */
@Composable
fun PermanentIcon() {
    Surface(
        Modifier
    ) {
        Icon(painterResource(R.drawable.baseline_android_24), contentDescription = "")
    }
}

/**
 * 1. Un botón con el texto 'Presionar' que, al hacer clic, actualizará el mensaje en el campo
 * de textoc con 'Botón presionado' y mostrará un `CircularProgressIndicator` durante 5 segundos.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Fragmento1() {
    var isProgressVisible by rememberSaveable { mutableStateOf(false) }



    LaunchedEffect(isProgressVisible) {
        delay(5000L)
        isProgressVisible = false
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (isProgressVisible) CircularProgressIndicator()
        val text = run { if (isProgressVisible) "Botón presionado" else "" }

        TextField(value = text, readOnly = true, onValueChange = { })
        MySpacer()
        Button(onClick = { isProgressVisible = true }, enabled = !isProgressVisible) {
            Text(text = "Presionar")
        }
    }

}

/**
 * 2. Un campo de texto que mostrará un mensaje, inicialmente no visible.
 * 3. Una casilla de verificación (checkbox) con el texto 'Activar' que, al marcarla, mostrará el
 * Text anterior
 */
@Composable
fun Fragmento2() {
    val (isVisible, onChange) = rememberSaveable { mutableStateOf(false) }
    val alpha = run {
        when (isVisible) {
            true -> 1f
            false -> 0f
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isVisible, onCheckedChange = { onChange(it) })
            Text(text = "Activar")
        }

        MySpacer()
        Text(text = "Soy un texto oculto", Modifier.alpha(alpha))
    }

}

/**
 * 5. Un interruptor (switch) que mostrará en grupo de botones siguiente(punto 6).
 * 6. Un grupo de botones de radio (radiobutton) con al menos tres opciones distintas que permitirá
 * al usuario seleccionar una opción y actualizar el mensaje del campo de texto en consecuencia.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Fragment3() {
    var isVisible by rememberSaveable { mutableStateOf(false) }

    val options = listOf("Hola", "Adios", "Muy buenas")

    val (selected, onRadioButtonClick) = rememberSaveable { mutableStateOf(options[0]) }

    Column(horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Mostrar opciones")
            Spacer(modifier = Modifier.width(10.dp))
            Switch(checked = isVisible, onCheckedChange = { isVisible = !isVisible })
        }

        if (isVisible) {
            options.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .selectable(
                            selected = (selected == text),
                            onClick = { onRadioButtonClick(text) },
                            role = Role.RadioButton
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(selected = (selected == text), onClick = null)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = text)
                }
            }
        }
        TextField(modifier = Modifier.fillMaxWidth(),
            value = selected,
            readOnly = true,
            onValueChange = { })
    }
}

/**
 * 7. Una imagen que se actualizará al hacer clic en el botón. La imagen puede cambiar entre al
 * menos tres imágenes diferentes.
 */
@Composable
fun Fragment4() {
    val ctx = LocalContext.current

    val myImagesOptions = listOf(

        R.drawable.mountains to "https://pixabay.com/photos/mountains-sun-clouds-peak-summit-190055/",

        R.drawable.beach to "https://pixabay.com/photos/beach-sea-sunset-sun-sunlight-1751455/",


        R.drawable.alley to "https://pixabay.com/photos/alley-street-night-evening-city-89197/",
    )

    val (@DrawableRes selectedImgIndex, indexSetter) = rememberSaveable {
        mutableStateOf(0)
    }

    TextButton(onClick = { indexSetter((myImagesOptions.indices).random()) }) {
        Text(text = "Cambia a imagen atleatoria")
    }
    MySpacer()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(myImagesOptions[selectedImgIndex].first),
            contentDescription = ""
        )
        TextButton(onClick = {
            ctx.startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse(myImagesOptions[selectedImgIndex].second)
                )
            )
        }) {
            Text(text = "Ir a fuente", fontStyle = FontStyle.Italic)
        }
    }
}
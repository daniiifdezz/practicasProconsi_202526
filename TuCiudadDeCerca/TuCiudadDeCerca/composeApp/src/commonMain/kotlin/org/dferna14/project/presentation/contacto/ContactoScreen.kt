package org.dferna14.project.presentation.contacto

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.dferna14.project.presentation.utils.FondoLeon
import org.jetbrains.compose.resources.painterResource
import tuciudaddecerca.composeapp.generated.resources.Res
import tuciudaddecerca.composeapp.generated.resources.menu_icon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactoScreen(onVolverAtras: () -> Unit) {
    FondoLeon {
        Scaffold(
            containerColor = Color.Transparent,

            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Información de Contacto",
                        textDecoration = TextDecoration.Underline,
                        color = Color.White) },
                    actions = {
                        IconButton(
                            onClick = onVolverAtras,
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.menu_icon),
                                contentDescription = "Volver menú principal",
                                modifier = Modifier.size(24.dp),
                                tint = Color.White,
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Black.copy(alpha = 0.4f),
                        titleContentColor = Color.White,
                        actionIconContentColor = Color.White
                    )
                )
            }
        )
        {
                paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Desarrollado por:",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Daniel Fernández-Muñiz Arribas",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Correo Electrónico:",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )

                Spacer(Modifier.height(8.dp))
                SelectionContainer {
                    Text(
                        text = "dferna14@estudiantes.unileon.es",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.White
                    )
                }


                Spacer(Modifier.height(16.dp))

                Text(
                    text = "LinkedIn:",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )

                Spacer(Modifier.height(8.dp))


                HyperlinkText(
                    text = "linkedin.com/dferna14",
                    url = "https://www.linkedin.com/in/daniel-fern%C3%A1ndez-mu%C3%B1iz-arribas-25593a317/",

                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "GitHub:",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White

                )

                Spacer(Modifier.height(8.dp))

                HyperlinkText(
                    text = "github.com/daniiifdezz",
                    url = "https://github.com/daniiifdezz",

                )





            }
        }
    }

}



@Composable
private fun HyperlinkText(text: String, url: String) {
    val uriHandler = LocalUriHandler.current

    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge.copy(
            color = Color.White,
            textDecoration = TextDecoration.Underline
        ),
        modifier = Modifier.clickable {
            uriHandler.openUri(url)
        }
    )
}

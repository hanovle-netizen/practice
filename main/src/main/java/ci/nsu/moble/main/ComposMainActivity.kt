package ci.nsu.moble.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val colorMap = mapOf(
    "Red"     to Color(0xFFF44336),
    "Orange"  to Color(0xFFFF9800),
    "Yellow"  to Color(0xFFFFEB3B),
    "Green"   to Color(0xFF4CAF50),
    "Blue"    to Color(0xFF2196F3),
    "Indigo"  to Color(0xFF3F51B5),
    "Violet"  to Color(0xFF9C27B0),
    "Purple"  to Color(0xFF673AB7),
    "Pink"    to Color(0xFFE91E63)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ColorChooserScreen()
            }
        }
    }
}

@Composable
fun ColorChooserScreen() {

    var input by remember { mutableStateOf("") }
    var buttonColor by remember { mutableStateOf(Color(0xFF4CAF50)) }  // начальный — зелёный

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Выбери цвет",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Название цвета") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = {
                val colorName = input.trim()
                val found = colorMap[colorName]

                if (found != null) {
                    buttonColor = found
                } else {
                    Log.w("ColorDemo", "Цвет \"$colorName\" не найден")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                contentColor = Color.White
            )
        ) {
            Text("Применить цвет", fontSize = 18.sp)
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Доступные цвета:",
            style = MaterialTheme.typography.titleMedium
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(colorMap.entries.toList()) { (name, color) ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = color
                ) {
                    Box(contentAlignment = androidx.compose.ui.Alignment.Center) {
                        Text(
                            text = name,
                            color = if (isLight(color)) Color.Black else Color.White,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}


private fun isLight(color: Color): Boolean {
    val luminance = 0.299 * color.red + 0.587 * color.green + 0.114 * color.blue
    return luminance > 0.5f
}
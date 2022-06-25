package com.sunggil.flowandroidtest.ui.activity.choice

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sunggil.flowandroidtest.ui.activity.main.ComposeMainActivity
import com.sunggil.flowandroidtest.ui.activity.main.XmlMainActivity

class ChoiceActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val onXmlClickCallback : () -> Unit = {
                startActivity(Intent(this, XmlMainActivity::class.java))
            }
            val onJetpackClickCallback : () -> Unit = {
                startActivity(Intent(this, ComposeMainActivity::class.java))
            }

            ChoiceXmlORCompose(onXmlClickCallback, onJetpackClickCallback)
        }
    }
}

@Composable
fun ChoiceXmlORCompose(
    onXmlClickCallback : () -> Unit,
    onJetpackClickCallback : () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
    ) {
        Button(
            onClick = onXmlClickCallback,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "XML 로 시작하기",
                modifier = Modifier.padding(vertical = 10.dp),
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onJetpackClickCallback,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Jetpack Compose 로 시작하기",
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChoiceActivity() {
    ChoiceXmlORCompose({}, {})
}
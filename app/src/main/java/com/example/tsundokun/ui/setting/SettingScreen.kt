package com.example.tsundokun.ui.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SettingScreen() {
    Surface(
        color = MaterialTheme.colorScheme.background,
    )  {
        Column {
            Text(text = "設定画面")
        }
    }
}

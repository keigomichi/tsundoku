package com.example.tsundokun.ui.home.component.tsundokunReport

import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.tsundokun.ui.home.HomeViewModel
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

/*
* 今週の積読した数の算出
*/
@Composable
@RequiresApi(VERSION_CODES.O)
fun RecentTsundokuData(viewModel: HomeViewModel = hiltViewModel()): Int {
    val tsundokunUiState by viewModel.uiState.collectAsState()
    val now = LocalDateTime.now()
    val oneWeekAgo = now.minus(1, ChronoUnit.WEEKS)
    val recentTsundokuData = tsundokunUiState.tsundoku.filter { tsundoku ->
        val date = tsundoku.createdAt.isEmpty()
            .let { if (it) now else LocalDateTime.parse(tsundoku.createdAt) }
//          LocalDateTime.parse(tsundoku.createdAt) FIXME: java.time.format.DateTimeParseException: Text '' could not be parsed at index 0
        date.isAfter(oneWeekAgo) && (date.isBefore(now) || date.isEqual(now))
    }
    return recentTsundokuData.size
}

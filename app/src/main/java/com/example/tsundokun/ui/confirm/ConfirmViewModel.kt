package com.example.tsundokun.ui.confirm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tsundokun.data.local.dao.TsundokuDao
import com.example.tsundokun.data.local.entities.TsundokuEntity
import com.example.tsundokun.data.local.repository.TsundokuRepository
import com.example.tsundokun.domain.usecases.GetTsundokuUseCase
import com.example.tsundokun.ui.home.HomeViewModel.TsundokuUiState
import com.example.tsundokun.ui.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ConfirmViewModel @Inject constructor(
    private val tsundokuRepository: TsundokuRepository,
    private val savedStateHandle: SavedStateHandle,
    private val tsundokuUseCase: GetTsundokuUseCase
) : ViewModel() {
    val navArgs: ConfirmScreenNavArgs = savedStateHandle.navArgs()
    private val _uiState = MutableStateFlow(TsundokuUiState())
    init {
        try {
            val categoryState = tsundokuUseCase.observeAllCategory
            viewModelScope.launch {
                categoryState.collect { _uiState.value = TsundokuUiState(category = it) }
            }
        } catch (_: Exception) {
        }
    }



    fun addTsundoku() {
        viewModelScope.launch {
            tsundokuRepository.addTsundoku(
                TsundokuEntity(
                    id = UUID.randomUUID().toString(),
                    link = navArgs.link,
                    isRead = false,
                    isFavorite = false,
                    createdAt = navArgs.createdAt,
                    updatedAt = "",
                    deletedAt = "",
                    categoryId = navArgs.categoryId,
                ),
            )
        }
    }
}

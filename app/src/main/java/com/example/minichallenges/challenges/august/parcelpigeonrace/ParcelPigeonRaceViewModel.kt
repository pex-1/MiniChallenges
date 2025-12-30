package com.example.minichallenges.challenges.august.parcelpigeonrace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.File

class ParcelPigeonRaceViewModel(
    private val repository: ParcelPigeonRaceRepository
) : ViewModel() {

    class Factory(
        private val repository: ParcelPigeonRaceRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ParcelPigeonRaceViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ParcelPigeonRaceViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
    private val _state = MutableStateFlow(ParcelPigeonRaceState())
    val state = _state.asStateFlow()

    var jobs: MutableList<Job> = (0..5).map { Job() }.toMutableList()

    private val mutex = Mutex()

    fun runOrRunAgain(cacheDir: File) {
        _state.update {
            it.copy(
                status = Status.RUNNING,
                images = (0..5).map { PigeonImage(it) }.toMutableList()
            )
        }
        viewModelScope.launch {
            state.value.images.forEachIndexed { index, item ->
                jobs[index] = launch {
                    val result = repository.getPigeonImage(item, cacheDir)
                    if (result != null) {
                        mutex.withLock {
                            val newImages = state.value.images.map { oldItem ->
                                if (oldItem.position == result.position) {
                                    PigeonImage(
                                        position = oldItem.position,
                                        file = result.file,
                                        size = result.size,
                                        durationInMilSeconds = result.durationInMilSeconds,
                                        isLoading = result.isLoading
                                    )
                                } else {
                                    PigeonImage(
                                        position = oldItem.position,
                                        file = oldItem.file,
                                        size = oldItem.size,
                                        durationInMilSeconds = oldItem.durationInMilSeconds,
                                        isLoading = oldItem.isLoading
                                    )
                                }
                            }
                            _state.update {
                                it.copy(images = newImages.toMutableList())
                            }
                        }
                    }
                }
            }
            for (job in jobs) {
                job.join()
            }
            _state.update {
                it.copy(status = Status.CAN_RUN_AGAIN)
            }
        }
    }

}
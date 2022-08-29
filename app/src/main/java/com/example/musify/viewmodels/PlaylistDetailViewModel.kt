package com.example.musify.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.example.musify.data.repositories.tracksrepository.TracksRepository
import com.example.musify.data.utils.MapperImageSize
import com.example.musify.ui.navigation.MusifyNavigationDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistDetailViewModel @Inject constructor(
    application: Application,
    savedStateHandle: SavedStateHandle,
    tracksRepository: TracksRepository
) : AndroidViewModel(application) {
    private val playlistId =
        savedStateHandle.get<String>(MusifyNavigationDestinations.PlaylistDetailScreen.NAV_ARG_PLAYLIST_ID)!!
    val tracks = tracksRepository.getPaginatedStreamForPlaylistTracks(
        playlistId = playlistId,
        countryCode = getCountryCode(),
        imageSize = MapperImageSize.MEDIUM
    )

}
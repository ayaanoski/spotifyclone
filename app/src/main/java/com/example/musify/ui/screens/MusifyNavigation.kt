package com.example.musify.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.musify.domain.SearchResult
import com.example.musify.ui.navigation.MusifyNavigationDestinations
import com.example.musify.ui.navigation.artistDetailScreen
import com.example.musify.ui.navigation.searchScreen

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun MusifyNavigation(
    playTrack: (SearchResult.TrackSearchResult) -> Unit,
    currentlyPlayingTrack: SearchResult.TrackSearchResult?,
    isPlaybackLoading: Boolean
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MusifyNavigationDestinations.SearchScreen.route
    ) {
        searchScreen(
            route = MusifyNavigationDestinations.SearchScreen.route,
            currentlyPlayingTrack = currentlyPlayingTrack,
            isPlaybackLoading = isPlaybackLoading,
            onArtistSearchResultClicked = {
                navController.navigate(MusifyNavigationDestinations.ArtistDetailScreen.buildRoute(it))
            },
            onPlayTrack = playTrack
        )
        artistDetailScreen(
            route = MusifyNavigationDestinations.ArtistDetailScreen.route,
            arguments = listOf(
                navArgument(MusifyNavigationDestinations.ArtistDetailScreen.NAV_ARG_ENCODED_IMAGE_URL_STRING) {
                    nullable = true
                }
            ),
            onBackButtonClicked = { navController.popBackStack() },
            onAlbumClicked = {},
            onPlayTrack = playTrack,
            currentlyPlayingTrack = currentlyPlayingTrack,
            isPlaybackLoading = isPlaybackLoading,
        )
    }
}
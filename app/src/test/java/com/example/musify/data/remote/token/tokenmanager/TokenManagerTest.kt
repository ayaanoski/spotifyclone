package com.example.musify.data.remote.token.tokenmanager


import com.example.musify.BuildConfig
import com.example.musify.data.remote.musicservice.SpotifyService
import com.example.musify.data.remote.token.toBearerToken
import com.example.musify.utils.defaultMusifyJacksonConverterFactory
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import java.util.*

/**
 * This is a test value that is equivalent to the [SPOTIFY_CLIENT_SECRET_BASE64].
 * Since the [SPOTIFY_CLIENT_SECRET_BASE64] uses [android.util.Base64] it can't
 * be used in tests without mocking. This value uses [java.util.Base64] as a
 * substitute for it.
 * Note: The value generated by [android.util.Base64.encodeToString] is
 * equivalent to [java.util.Base64] only when [android.util.Base64.NO_WRAP]
 * is used in conjunction with [android.util.Base64.encodeToString].
 */
private val TEST_SPOTIFY_CLIENT_SECRET_BASE64: String
    get() {
        val encodedClientSecret = Base64
            .getEncoder()
            .encodeToString("${BuildConfig.SPOTIFY_CLIENT_ID}:${BuildConfig.SPOTIFY_CLIENT_SECRET}".toByteArray())
        return "Basic $encodedClientSecret"
    }


class TokenManagerTest {
    // Anirudh Ravichander
    private val validArtistId = "4zCH9qm4R2DADamUHMCa6O"
    private lateinit var tokenManager: TokenManager

    @Before
    fun setup() {
        tokenManager = Retrofit.Builder()
            .baseUrl("https://accounts.spotify.com/")
            .addConverterFactory(defaultMusifyJacksonConverterFactory)
            .build()
            .create(TokenManager::class.java)
    }

    @Test
    fun getAccessTokenTest_validClientSecret_returnsAccessToken() {
        // given a valid client secret
        val clientSecret = TEST_SPOTIFY_CLIENT_SECRET_BASE64
        // the access token must be fetched without an exceptions
        runBlocking { tokenManager.getNewAccessToken(clientSecret) }
    }

    @Test
    fun useAccessTokenTest_newAccessToken_isAbleToSuccessfullyMakeRequest() {
        // given a valid client secret
        val clientSecret = TEST_SPOTIFY_CLIENT_SECRET_BASE64
        // when requesting an access token
        val accessTokenResponse = runBlocking {
            // the access token must be fetched without an exceptions
            tokenManager.getNewAccessToken(clientSecret)
        }
        // when using the newly acquired access token to get an artist
        val spotifyService = Retrofit.Builder()
            .baseUrl("https://api.spotify.com/")
            .addConverterFactory(defaultMusifyJacksonConverterFactory)
            .build()
            .create(SpotifyService::class.java)
        // the artist must be fetched successfully
        runBlocking {
            spotifyService.getArtistInfoWithId(
                validArtistId,
                accessTokenResponse.toBearerToken()
            )
        }
    }
}
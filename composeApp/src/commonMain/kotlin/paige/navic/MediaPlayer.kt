package paige.navic

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import paige.subsonic.api.model.AnyTracks

interface MediaPlayer {
	var tracks: AnyTracks?
	val progress: State<Float>
	val currentIndex: State<Int>
	val isPaused: State<Boolean>

	fun play(tracks: AnyTracks, songIndex: Int)
	fun pause()
	fun resume()
	fun seek(normalized: Float)

	fun next()
	fun previous()
}

@Composable
expect fun rememberMediaPlayer(): MediaPlayer

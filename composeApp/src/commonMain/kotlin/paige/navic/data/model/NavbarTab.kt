package paige.navic.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NavbarTab(
	val id: Id,
	val visible: Boolean
) {
	@Serializable
	enum class Id {
		LIBRARY,
		PLAYLISTS,
		ARTISTS
	}
}

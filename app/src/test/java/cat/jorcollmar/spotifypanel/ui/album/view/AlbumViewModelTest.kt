package cat.jorcollmar.spotifypanel.ui.album.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import cat.jorcollmar.domain.usecase.albums.GetAlbumDetails
import cat.jorcollmar.domain.usecase.albums.GetAlbums
import cat.jorcollmar.spotifypanel.ui.album.mapper.AlbumMapper
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule


class AlbumViewModelTest {
    private val getAlbums: GetAlbums = mockk(relaxed = true)
    private val getAlbumDetails: GetAlbumDetails = mockk(relaxed = true)
    private val albumMapper: AlbumMapper = mockk(relaxed = true)

    private lateinit var viewModel: AlbumViewModel

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = AlbumViewModel(getAlbums, getAlbumDetails, albumMapper)
    }

    @Test
    fun `When setSelectedAlbum is executed, Then _selectedAlbumId is assigned with value given by parameter`() {
        val albumId = "albumId"
        viewModel.setSelectedAlbum(albumId)
        Assert.assertEquals(albumId, viewModel.getSelectedAlbumId())
    }

    @Test
    fun `When changeDetailLoaded is executed, Then _albumDetailsLoaded is assigned with value given by parameter`() {
        val isLoaded = true
        viewModel.changeDetailLoaded(isLoaded)
        Assert.assertEquals(isLoaded, viewModel.albumDetailsLoaded)
    }
}
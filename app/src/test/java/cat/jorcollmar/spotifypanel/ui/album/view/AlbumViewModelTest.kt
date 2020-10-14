package cat.jorcollmar.spotifypanel.ui.album.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import cat.jorcollmar.domain.model.AlbumDomain
import cat.jorcollmar.domain.model.ArtistDomain
import cat.jorcollmar.domain.usecase.albums.GetAlbumDetails
import cat.jorcollmar.domain.usecase.albums.GetAlbums
import cat.jorcollmar.spotifypanel.ui.album.mapper.AlbumMapper
import cat.jorcollmar.spotifypanel.ui.album.view.AlbumViewModel.Companion.GENERAL_ERROR_ALBUM_DETAILS
import cat.jorcollmar.spotifypanel.ui.album.view.AlbumViewModel.Companion.HTTP_ERROR_CODE_401
import cat.jorcollmar.spotifypanel.ui.album.view.AlbumViewModel.Companion.TOKEN_ERROR
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import io.reactivex.functions.Consumer
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.HttpException
import retrofit2.Response


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
    fun `When setAlbumsNextPage is executed, Then _nextPage value is assigned with value given by parameter`() {
        val nextPageValue = "nextPageValue"
        viewModel.setAlbumsNextPage(nextPageValue)
        Assert.assertEquals(nextPageValue, viewModel.getNextAlbumsPage())
    }

    @Test
    fun `When setSelectedAlbumId is executed, Then _selectedAlbumId is assigned with value given by parameter`() {
        val albumId = "albumId"
        viewModel.setSelectedAlbumId(albumId)
        Assert.assertEquals(albumId, viewModel.getSelectedAlbumId())
    }

    @Test
    fun `When changeDetailLoaded is executed, Then _albumDetailsLoaded is assigned with value given by parameter`() {
        val isLoaded = true
        viewModel.changeDetailLoaded(isLoaded)
        Assert.assertEquals(isLoaded, viewModel.albumDetailsLoaded)
    }

    @Test
    fun `When getAlbumDetails is executed, Then _loading value is assigned with true`() {
        val albumId = "albumId"
        viewModel.setSelectedAlbumId(albumId)

        viewModel.getAlbumDetails()
        Assert.assertEquals(true, viewModel.loading.value)
    }

    @Test
    fun `When getAlbumDetails is executed, Then getAlbumDetails use case is executed`() {
        val albumId = "albumId"
        viewModel.setSelectedAlbumId(albumId)

        viewModel.getAlbumDetails()
        verify { getAlbumDetails.execute(any(), any(), any()) }
    }

    @Test
    fun `When getAlbumDetails is executed satisfactory, Then _loading value is assigned with false And _selectedAlbum value is assigned with the result after being mapped`() {
        val albumId = "albumId"
        val albumDomain = AlbumDomain("", "", listOf(), "", mapOf(), ArtistDomain("", ""), "", 0)
        viewModel.setSelectedAlbumId(albumId)
        viewModel.getAlbumDetails()

        val slot = slot<Consumer<AlbumDomain>>()
        verify { getAlbumDetails.execute(capture(slot), any(), any()) }
        slot.captured.accept(albumDomain)

        Assert.assertEquals(false, viewModel.loading.value)
        Assert.assertEquals(albumMapper.map(albumDomain), viewModel.selectedAlbum.value)
    }

    @Test
    fun `When getAlbumDetails is executed And returns a HttpException with code 401, Then _loading value is assigned with false And _error value is assigned with TOKEN_ERROR constant value`() {
        val albumId = "albumId"
        val httpException401: Response<AlbumDomain> =
            Response.error(HTTP_ERROR_CODE_401, ResponseBody.create(MediaType.parse(""), ""))

        val httpException = HttpException(httpException401)
        viewModel.setSelectedAlbumId(albumId)
        viewModel.getAlbumDetails()

        val slot = slot<Consumer<Throwable>>()
        verify { getAlbumDetails.execute(any(), capture(slot), any()) }
        slot.captured.accept(httpException)

        Assert.assertEquals(false, viewModel.loading.value)
        Assert.assertEquals(TOKEN_ERROR, viewModel.error.value)
    }

    @Test
    fun `When getAlbumDetails is executed And returns an error different from HttpException with code 401, Then _loading value is assigned with false And _error value is assigned with GENERAL_ERROR_ALBUM_DETAILS constant value`() {
        val albumId = "albumId"
        val throwable = Throwable("GetAlbumDetailsThrowable")

        viewModel.setSelectedAlbumId(albumId)
        viewModel.getAlbumDetails()

        val slot = slot<Consumer<Throwable>>()
        verify { getAlbumDetails.execute(any(), capture(slot), any()) }
        slot.captured.accept(throwable)

        Assert.assertEquals(false, viewModel.loading.value)
        Assert.assertEquals(GENERAL_ERROR_ALBUM_DETAILS, viewModel.error.value)
    }
}
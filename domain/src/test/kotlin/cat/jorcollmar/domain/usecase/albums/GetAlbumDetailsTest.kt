package cat.jorcollmar.domain.usecase.albums

import cat.jorcollmar.domain.BaseUseCaseTest
import cat.jorcollmar.domain.model.AlbumDomain
import cat.jorcollmar.domain.model.ArtistDomain
import cat.jorcollmar.domain.repository.SpotifyAlbumsRepositoryContract
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetAlbumDetailsTest : BaseUseCaseTest() {

    private val spotifyAlbumsRepository: SpotifyAlbumsRepositoryContract = mockk(relaxed = true)
    private lateinit var getAlbumDetails: GetAlbumDetails

    private val passedAlbumId = "albumId"

    @Before
    fun setUpTest() {
        getAlbumDetails = GetAlbumDetails(schedulersFacade, spotifyAlbumsRepository)
    }

    @Test
    fun `Given GetAlbumDetails execution, When album id is passed, Then getAlbumDetails from repository is invoked with passed id`() {
        val albumDomainMockk: AlbumDomain = mockk()

        every { spotifyAlbumsRepository.getAlbumDetails(passedAlbumId) } returns Single.just(
            albumDomainMockk
        )

        captureResultForUseCase(
            singleUseCase = getAlbumDetails,
            params = GetAlbumDetails.Params(passedAlbumId)
        )

        verify { spotifyAlbumsRepository.getAlbumDetails(passedAlbumId) }
    }

    @Test
    fun `Given GetAlbumDetails execution, When an album is returned by the repository, Then same album is returned by the useCase`() {
        val albumDomain = AlbumDomain("", "", listOf(), "", mapOf(), ArtistDomain("", ""), "", 0)

        every { spotifyAlbumsRepository.getAlbumDetails(passedAlbumId) } returns Single.just(
            albumDomain
        )

        assertEquals(
            albumDomain,
            captureResultForUseCase(
                singleUseCase = getAlbumDetails,
                params = GetAlbumDetails.Params(passedAlbumId)
            )
        )
    }

    @Test
    fun `Given GetAlbumDetails execution, When error is returned by the repository, Then same error is returned by the useCase`() {
        val throwable = Throwable("GetAlbumDetailsThrowable")

        every { spotifyAlbumsRepository.getAlbumDetails(passedAlbumId) } returns Single.error(
            throwable
        )

        Assert.assertEquals(
            throwable,
            captureErrorForUseCase(
                singleUseCase = getAlbumDetails,
                params = GetAlbumDetails.Params(passedAlbumId)
            )
        )
    }
}
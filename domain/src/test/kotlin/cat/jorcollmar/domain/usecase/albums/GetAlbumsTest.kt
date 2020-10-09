package cat.jorcollmar.domain.usecase.albums

import cat.jorcollmar.domain.BaseUseCaseTest
import cat.jorcollmar.domain.model.AlbumDomain
import cat.jorcollmar.domain.model.ArtistDomain
import cat.jorcollmar.domain.model.PagedAlbumsDomain
import cat.jorcollmar.domain.repository.SpotifyAlbumsRepositoryContract
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetAlbumsTest : BaseUseCaseTest() {

    private val spotifyAlbumsRepository: SpotifyAlbumsRepositoryContract = mockk(relaxed = true)
    private lateinit var getAlbums: GetAlbums

    private val passedOffset = 0

    @Before
    fun setUpTest() {
        getAlbums = GetAlbums(schedulersFacade, spotifyAlbumsRepository)
    }

    @Test
    fun `Given GetAlbums execution, GetAlbums from repository is invoked`() {
        val pagedAlbumsDomainMockk: PagedAlbumsDomain = mockk()

        every {
            spotifyAlbumsRepository.getAlbums(passedOffset)
        } returns Single.just(pagedAlbumsDomainMockk).toObservable()

        captureResultForUseCase(
            observableUseCase = getAlbums,
            params = GetAlbums.Params(passedOffset)
        )

        verify {
            spotifyAlbumsRepository.getAlbums(passedOffset)
        }
    }

    @Test
    fun `Given GetAlbums execution, When albums paged result is returned by the repository, Then same albums paged result is returned by the useCase`() {
        val pagedAlbumsDomain = PagedAlbumsDomain("", listOf(), 0, "", 0, "", 0)

        every {
            spotifyAlbumsRepository.getAlbums(passedOffset)
        } returns Single.just(pagedAlbumsDomain).toObservable()

        Assert.assertEquals(
            pagedAlbumsDomain,
            captureResultForUseCase(
                observableUseCase = getAlbums,
                params = GetAlbums.Params(passedOffset)
            )
        )
    }

    @Test
    fun `Given GetAlbums execution, When error is returned by the repository, Then same error is returned by the useCase`() {
        val throwable = Throwable("GetAlbumsThrowable")

        every {
            spotifyAlbumsRepository.getAlbums(passedOffset)
        } returns Observable.error(throwable)

        Assert.assertEquals(
            throwable,
            captureErrorForUseCase(
                observableUseCase = getAlbums,
                params = GetAlbums.Params(passedOffset)
            )
        )
    }

}
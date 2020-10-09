package cat.jorcollmar.data.repository

import cat.jorcollmar.data.mapper.AlbumDataMapper
import cat.jorcollmar.data.mapper.PagedAlbumsDataMapper
import cat.jorcollmar.data.model.AlbumData
import cat.jorcollmar.data.model.ArtistData
import cat.jorcollmar.data.model.PagedAlbumsData
import cat.jorcollmar.data.source.spotify.SpotifyApiDataSource
import cat.jorcollmar.domain.model.AlbumDomain
import cat.jorcollmar.domain.model.ArtistDomain
import cat.jorcollmar.domain.model.PagedAlbumsDomain
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SpotifyAlbumsRepositoryTest {
    private val spotifyApiDataSource: SpotifyApiDataSource = mockk(relaxed = true)
    private val albumDataMapper: AlbumDataMapper = mockk(relaxed = true)
    private val pagedAlbumsDataMapper: PagedAlbumsDataMapper = mockk(relaxed = true)

    private lateinit var spotifyAlbumsRepository: SpotifyAlbumsRepository

    private val passedAlbumId = "albumId"
    private val passedOffset = 0

    private val pagedAlbumsDomain = PagedAlbumsDomain("", listOf(), 0, "", 0, "", 0)

    private val albumDomain =
        AlbumDomain("", "", listOf(), "", mapOf(), ArtistDomain("", ""), "", 0)

    private val pagedAlbumsData = PagedAlbumsData("", listOf(), 0, "", 0, "", 0)

    private val albumData = AlbumData("", "", listOf(), "", mapOf(), ArtistData("", ""), "", 0)

    @Before
    fun setUp() {
        spotifyAlbumsRepository =
            SpotifyAlbumsRepository(spotifyApiDataSource, albumDataMapper, pagedAlbumsDataMapper)
    }

    @Test
    fun `When getAlbums is called And getAlbums from repository returns an albums paged result, Then return it`() {
        every {
            spotifyApiDataSource.getAlbums(passedOffset)
        } returns Single.just(pagedAlbumsData).toObservable()

        every { pagedAlbumsDataMapper.map(any<PagedAlbumsData>()) } returns pagedAlbumsDomain

        val observable = spotifyAlbumsRepository.getAlbums(passedOffset)
        val testObserver = TestObserver<PagedAlbumsDomain>()
        observable.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        verify {
            spotifyApiDataSource.getAlbums(passedOffset)
        }

        Assert.assertEquals(pagedAlbumsDomain, testObserver.values()[0])
    }

    @Test
    fun `When getAlbums is called And getAlbums from repository returns an error, Then the error is returned `() {
        val error = Throwable("GetAlbumsThrowable")

        every {
            spotifyApiDataSource.getAlbums(passedOffset)
        } returns Observable.error(error)

        val observable = spotifyAlbumsRepository.getAlbums(passedOffset)

        val testObserver = TestObserver<PagedAlbumsDomain>()
        observable.subscribe(testObserver)

        testObserver.assertNotComplete()
        testObserver.assertError(error)
        verify {
            spotifyApiDataSource.getAlbums(passedOffset)
        }
    }

    @Test
    fun `When getAlbumDetails is called And getAlbumDetails from repository returns an album, Then return it`() {
        every { spotifyApiDataSource.getAlbumDetails(passedAlbumId) } returns Single.just(albumData)
        every { albumDataMapper.map(any<AlbumData>()) } returns albumDomain

        val observable = spotifyAlbumsRepository.getAlbumDetails(passedAlbumId)
        val testObserver = TestObserver<AlbumDomain>()
        observable.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        verify { spotifyApiDataSource.getAlbumDetails(passedAlbumId) }
        Assert.assertEquals(albumDomain, testObserver.values()[0])
    }

    @Test
    fun `When getAlbumDetails is called And getAlbumDetails from repository returns an error, Then the error is returned `() {
        val error = Throwable("GetAlbumsThrowable")

        every {
            spotifyApiDataSource.getAlbumDetails(passedAlbumId)
        } returns Single.error(error)

        val observable = spotifyAlbumsRepository.getAlbumDetails(passedAlbumId)
        val testObserver = TestObserver<AlbumDomain>()
        observable.subscribe(testObserver)

        testObserver.assertNotComplete()
        testObserver.assertError(error)
        verify { spotifyApiDataSource.getAlbumDetails(passedAlbumId) }
    }
}
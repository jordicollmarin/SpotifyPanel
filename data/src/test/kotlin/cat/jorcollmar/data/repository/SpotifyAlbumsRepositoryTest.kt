package cat.jorcollmar.data.repository

import cat.jorcollmar.data.mapper.AlbumDataMapper
import cat.jorcollmar.data.model.AlbumData
import cat.jorcollmar.data.model.ArtistData
import cat.jorcollmar.data.source.spotify.SpotifyApiDataSource
import cat.jorcollmar.domain.model.AlbumDomain
import cat.jorcollmar.domain.model.ArtistDomain
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

    private lateinit var spotifyAlbumsRepository: SpotifyAlbumsRepository

    private val passedAlbumId = "albumId"

    private val albumDomain =
        AlbumDomain("", "", listOf(), "", mapOf(), ArtistDomain("", ""), "", 0)

    private val albumsDomain: List<AlbumDomain> = mutableListOf<AlbumDomain>().apply {
        add(
            AlbumDomain("", "", listOf(), "", mapOf(), ArtistDomain("", ""), "", 0)
        )
    }

    private val albumData = AlbumData("", "", listOf(), "", mapOf(), ArtistData("", ""), "", 0)

    private val albumsData: List<AlbumData> = mutableListOf<AlbumData>().apply {
        add(
            AlbumData("", "", listOf(), "", mapOf(), ArtistData("", ""), "", 0)
        )
    }

    @Before
    fun setUp() {
        spotifyAlbumsRepository = SpotifyAlbumsRepository(spotifyApiDataSource, albumDataMapper)
    }

    @Test
    fun `When getAlbums is called And getAlbums from repository returns a list of albums, Then return it`() {
        every {
            spotifyApiDataSource.getAlbums()
        } returns Single.just(
            albumsData
        ).toObservable()

        every { albumDataMapper.map(any<List<AlbumData>>()) } returns albumsDomain

        val observable = spotifyAlbumsRepository.getAlbums()
        val testObserver = TestObserver<List<AlbumDomain>>()
        observable.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        verify {
            spotifyApiDataSource.getAlbums()
        }
        Assert.assertEquals(albumsDomain, testObserver.values()[0])
    }

    @Test
    fun `When getAlbums is called And getAlbums from repository returns an error, Then the error is returned `() {
        val error = Throwable("GetAlbumsThrowable")

        every {
            spotifyApiDataSource.getAlbums()
        } returns Observable.error(error)

        val observable = spotifyAlbumsRepository.getAlbums()

        val testObserver = TestObserver<List<AlbumDomain>>()
        observable.subscribe(testObserver)

        testObserver.assertNotComplete()
        testObserver.assertError(error)
        verify {
            spotifyApiDataSource.getAlbums()
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
package cat.jorcollmar.spotifypanel.di

import cat.jorcollmar.data.repository.SpotifyAlbumsRepository
import cat.jorcollmar.data.source.SpotifyWebservice
import cat.jorcollmar.domain.common.SchedulersFacade
import cat.jorcollmar.domain.repository.SpotifyAlbumsRepositoryContract
import cat.jorcollmar.spotifypanel.BuildConfig
import cat.jorcollmar.spotifypanel.ui.SpotifyPanelActivity
import cat.jorcollmar.spotifypanel.ui.album.view.AlbumDetailsFragment
import cat.jorcollmar.spotifypanel.ui.album.view.AlbumsListFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
abstract class SpotifyPanelModule {

    @ContributesAndroidInjector
    abstract fun contributeSpotifyPanelActivity(): SpotifyPanelActivity

    @ContributesAndroidInjector
    abstract fun contributeAlbumsListFragment(): AlbumsListFragment

    @ContributesAndroidInjector
    abstract fun contributeAlbumDetailsFragment(): AlbumDetailsFragment

    @Binds
    abstract fun bindSpotifyAlbumsRepository(repository: SpotifyAlbumsRepository): SpotifyAlbumsRepositoryContract

    @Module
    companion object {

        @Provides
        @Singleton
        fun provideDomainSchedulersFacade(): SchedulersFacade {
            return object : SchedulersFacade {
                override fun getIo(): Scheduler {
                    return Schedulers.io()
                }

                override fun getAndroidMainThread(): Scheduler {
                    return AndroidSchedulers.mainThread()
                }
            }
        }

        @Provides
        @Singleton
        fun provideOkHttp(): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor { chain ->
                    var request: Request = chain.request()
                    val builder: Request.Builder = request.newBuilder()
                        .addHeader("Authorization", "Bearer ${BuildConfig.SPOTIFY_API_KEY}")
                    request = builder.build()
                    chain.proceed(request)
                }.build()


        @JvmStatic
        @Provides
        fun provideSpotifyWebservice(okHttp: OkHttpClient): SpotifyWebservice {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.SPOTIFY_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttp)
                .build()
                .create(SpotifyWebservice::class.java)
        }
    }
}
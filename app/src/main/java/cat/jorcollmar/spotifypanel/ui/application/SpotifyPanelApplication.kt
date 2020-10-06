package cat.jorcollmar.spotifypanel.ui.application

import android.app.Application
import cat.jorcollmar.spotifypanel.di.DaggerSpotifyPanelComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class SpotifyPanelApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = androidInjector

    override fun onCreate() {
        super.onCreate()
        DaggerSpotifyPanelComponent.create().inject(this)
    }
}
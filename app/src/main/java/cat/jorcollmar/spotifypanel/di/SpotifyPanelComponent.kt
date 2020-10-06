package cat.jorcollmar.spotifypanel.di

import cat.jorcollmar.spotifypanel.ui.application.SpotifyPanelApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, SpotifyPanelModule::class])
interface SpotifyPanelComponent : AndroidInjector<SpotifyPanelApplication>
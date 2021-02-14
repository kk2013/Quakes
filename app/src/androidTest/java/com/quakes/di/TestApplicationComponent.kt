package com.quakes.di

import android.content.Context
import com.quakes.QuakesApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, TestNetworkModule::class, QuakesModule::class])
interface TestApplicationComponent : ApplicationComponent {

    fun getMockWebserver(): MockWebServer

    override fun inject(app: QuakesApplication)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): TestApplicationComponent
    }
}
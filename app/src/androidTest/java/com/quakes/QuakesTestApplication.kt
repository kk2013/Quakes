package com.quakes

import com.quakes.di.DaggerTestApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class QuakesTestApplication : QuakesApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerTestApplicationComponent.factory().create(applicationContext)
    }
}
package com.quakes.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.quakes.quakeslist.QuakesListFragment
import com.quakes.quakemap.QuakeMapFragment
import com.quakes.quakeslist.QuakesListViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class QuakesModule {

    @Binds
    internal abstract fun bindsViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @ContributesAndroidInjector
    internal abstract fun quakesListFragment(): QuakesListFragment

    @ContributesAndroidInjector
    internal abstract fun quakesMapFragment(): QuakeMapFragment

    @Binds
    @IntoMap
    @ViewModelKey(QuakesListViewModel::class)
    abstract fun bindsQuakesViewModel(viewModel: QuakesListViewModel): ViewModel
}

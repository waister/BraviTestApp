package br.com.waister.bravitestapp.di

import br.com.waister.bravitestapp.features.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::HomeViewModel)

}

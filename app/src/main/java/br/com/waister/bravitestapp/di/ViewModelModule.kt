package br.com.waister.bravitestapp.di

import br.com.waister.bravitestapp.features.home.HomeViewModel
import br.com.waister.bravitestapp.features.history.HistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {

    viewModelOf(::HomeViewModel)

    viewModelOf(::HistoryViewModel)

}

package com.example.reliatest.di

import com.example.reliatest.viewmodel.HomeViewModel
import com.example.reliatest.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}
package com.example.reliatest.di

import com.example.reliatest.viewmodel.HomeViewModel
import com.example.reliatest.viewmodel.LoginDemoViewModel
import com.example.reliatest.viewmodel.LoginViewModel
import com.example.reliatest.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginDemoViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { LoginViewModel(get()) }
}
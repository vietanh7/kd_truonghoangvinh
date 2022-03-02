package com.example.reliatest.di

import com.example.reliatest.repository.*
import com.example.reliatest.repository.impl.*
import org.koin.dsl.module

val repositoryModule = module {
    single<LoginDemoRepository> { LoginDemoRepositoryImpl(get()) }
    single<HomeRepository> { HomeRepositoryImpl(get()) }
    single<RegisterRepository> { RegisterRepositoryImpl(get()) }
    single<LoginRepository> { LoginRepositoryImpl(get()) }
    single<ProductRepository> { ProductRepositoryImpl(get()) }
}
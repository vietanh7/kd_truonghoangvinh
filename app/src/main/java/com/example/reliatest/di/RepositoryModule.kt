package com.example.reliatest.di

import com.example.reliatest.repository.HomeRepository
import com.example.reliatest.repository.LoginRepository
import com.example.reliatest.repository.impl.HomeRepositoryImpl
import com.example.reliatest.repository.impl.LoginRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<LoginRepository> { LoginRepositoryImpl(get()) }
    single<HomeRepository> { HomeRepositoryImpl(get()) }
}
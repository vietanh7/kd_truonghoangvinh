package com.example.reliatest.di

import com.example.reliatest.repository.HomeRepository
import com.example.reliatest.repository.LoginDemoRepository
import com.example.reliatest.repository.LoginRepository
import com.example.reliatest.repository.RegisterRepository
import com.example.reliatest.repository.impl.HomeRepositoryImpl
import com.example.reliatest.repository.impl.LoginDemoRepositoryImpl
import com.example.reliatest.repository.impl.LoginRepositoryImpl
import com.example.reliatest.repository.impl.RegisterRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<LoginDemoRepository> { LoginDemoRepositoryImpl(get()) }
    single<HomeRepository> { HomeRepositoryImpl(get()) }
    single<RegisterRepository> { RegisterRepositoryImpl(get()) }
    single<LoginRepository> { LoginRepositoryImpl(get()) }
}
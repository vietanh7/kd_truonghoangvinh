package com.example.reliatest.di

import android.content.Context
import android.content.SharedPreferences
import com.example.reliatest.constant.Constant.PREFS_FILENAME
import com.example.reliatest.utils.PrefUtil
import com.google.gson.Gson
import org.koin.dsl.module

val localModule = module {
    single { Gson() }
    single { provideSharedPreference(get()) }
    single { providePreferenceHelper(get(), get(), get()) }
}

fun providePreferenceHelper(
    context: Context,
    sharedPreferences: SharedPreferences,
    gson: Gson
) = PrefUtil(context, sharedPreferences, gson)

fun provideSharedPreference(context: Context): SharedPreferences =
    context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
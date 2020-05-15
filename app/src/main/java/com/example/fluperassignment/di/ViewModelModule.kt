package com.leopold.mvvm.di

import com.example.fluperassignmet.ui.view_model.ProductViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

/**
 * @author Leopold
 */
val viewModelModule = module {
    viewModel { ProductViewModel(get()) }
}
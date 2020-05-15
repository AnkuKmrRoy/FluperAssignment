package com.leopold.mvvm.di

import androidx.room.Room
import com.example.fluperassignmet.data.db.FluperAssignmentDatabase
import com.example.fluperassignmet.data.repositories.ProductsRepositories
import com.example.fluperassignmet.ui.view_model.ProductViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

/**
 * @author Leopold
 */
val roomModule = module {
    single { FluperAssignmentDatabase.getInstance(androidApplication()) }
    single(createOnStart = false) { get<FluperAssignmentDatabase>().getProductsDao() }
    single { ProductsRepositories(get()) }

}



package com.example.fluperassignmet

import android.app.Application
import com.leopold.mvvm.di.roomModule
import com.leopold.mvvm.di.viewModelModule
import org.koin.android.ext.android.startKoin

class FluperAssignmentApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(
            roomModule,
            viewModelModule
        ))
    }

}
package br.com.bookscapecompose.application

import android.app.Application
import br.com.bookscapecompose.di.modules.bookScapeModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AppApplication)
            modules(bookScapeModules)
        }

    }
}
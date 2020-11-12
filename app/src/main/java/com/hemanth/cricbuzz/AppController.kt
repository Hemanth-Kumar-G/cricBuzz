package com.hemanth.cricbuzz

import android.content.Context
import android.os.StrictMode
import com.facebook.drawee.backends.pipeline.Fresco
import com.hemanth.cricbuzz.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class AppController : DaggerApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        appComponent.inject(this)
        return appComponent
    }

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}
package org.dferna14.project

import android.app.Application
import android.content.Context

class AndroidApp : Application() {
    override fun onCreate() {
    super.onCreate()
    AppContext.set(this)
}
}

//uso de patron singelton, accesible desde cualquier clase
object AppContext {
    private lateinit var context: Context
    fun set(context: Context) {
        this.context = context
    }
    fun get(): Context {
        return context
    }
}
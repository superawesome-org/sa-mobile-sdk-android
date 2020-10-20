package tv.superawesome.sdk.publisher.common.di

import android.util.Log
import org.koin.core.module.Module

private const val initMethodName = "init"
private const val createMethodName = "create"

interface ProxyModule {
    fun init(vararg args: Any)
    fun create(): Module
}

class ProxyModuleInjector {
    fun createModule(className: String, vararg args: Any): Module? = try {
        val classReference = Class.forName(className)
        val instance = classReference.newInstance()

        val initMethod = instance.javaClass.getMethod(initMethodName, Array<Any>::class.java)
        initMethod.invoke(instance, args)

        val createMethod = instance.javaClass.getMethod(createMethodName)
        Log.i("SuperAwesome", "Module is loaded for `$className`")

        createMethod.invoke(instance) as? Module
    } catch (exception: Exception) {
        Log.i("SuperAwesome", "Module is not available for `$className`")
        null
    }
}
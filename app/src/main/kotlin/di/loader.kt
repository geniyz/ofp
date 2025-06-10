package site.geniyz.ofp.di

import org.reflections.Reflections
import org.reflections.util.ConfigurationBuilder
import java.io.File
import java.net.URLClassLoader
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.kotlinFunction

object Loader {

    inline fun load(
        dir: String,
        vararg ifaces: Class<*>,
        crossinline transform: (Class<*>, kotlinFunction: KFunction<Any>?)->Unit
    ){
        println(" plugins absolute path: "+File(dir).absolutePath)
        val files = File(dir).listFiles { file -> file.extension == "jar" } ?: emptyArray()

        files.forEach { jarFile ->
            val reflections = Reflections(
                ConfigurationBuilder()
                    .setClassLoaders(arrayOf(
                        URLClassLoader(
                            arrayOf(jarFile.toURI().toURL()),
                            this::class.java.classLoader
                        )
                    ))
                    .setUrls(jarFile.toURI().toURL())
            )

            ifaces.forEach { iface ->
                // классы, реализующие нужный интерфейс
                val classes = reflections.getSubTypesOf(iface)

                classes.forEach { k ->
                    try {
                        transform(k, k.constructors.first().kotlinFunction)
                    } catch (e: Exception) {
                        println("Failed to load: ${e.message}")
                    }
                }
            }
        }
    }

}

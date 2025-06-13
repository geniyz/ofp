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
        println("""
            КАТАЛОГ РАСШИРЕНИЙ: ${File(dir).absolutePath }
        """.trimIndent())
        (File(dir).listFiles { file -> file.extension == "jar" } ?: emptyArray())
            .map { jarFile ->
                Reflections(
                    ConfigurationBuilder()
                        .setClassLoaders(arrayOf(URLClassLoader(arrayOf(jarFile.toURI().toURL()), this::class.java.classLoader)))
                        .setUrls(jarFile.toURI().toURL())
                ).let { reflections ->
                    ifaces.map { iface -> // классы, реализующие нужный интерфейс
                        val classes = reflections.getSubTypesOf(iface)
                        classes.map { k ->
                            try {
                                transform(k, k.constructors.first().kotlinFunction)
                                println("""
                                    ЗАГРУЖЕНО: ${k} (${k.constructors.first().kotlinFunction})
                                """.trimIndent())
                            } catch (t: Throwable) {
                                println("Failed to load «${k.canonicalName}»: ${t.localizedMessage}")
                            } }
                    }
                }
            }
    }

}

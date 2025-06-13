@file:OptIn(ExperimentalUuidApi::class)

package site.geniyz.ofp


import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.error.NoDefinitionFoundException
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import site.geniyz.ofp.cntxt.Context
import site.geniyz.ofp.core.UObject
import site.geniyz.ofp.di.Loader
import site.geniyz.ofp.di.appModule
import site.geniyz.ofp.rule.IRule
import kotlin.test.assertEquals
import kotlin.uuid.ExperimentalUuidApi

class LoaderTest: AutoCloseKoinTest() {

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `ByLangRule сначала отсутствует, а потом норм загружается с использованием Loader`() {
        startKoin { modules(appModule) }

        assertThrows<NoDefinitionFoundException> {
            val r: IRule = get(named("site.geniyz.ofp.rule.ByLangRule")) {
                parametersOf(
                    Context.NONE,
                    Context.NONE
                )
            }
        }

        Loader.load("../addons", IRule::class.java) { clazz, constructor ->
            getKoin().loadModules( listOf(
                module { // имя/код правила — это полное наименование Класса
                    factory(named(clazz.canonicalName)) { (c: Context, p: UObject) ->
                        constructor!!.call(
                            c,
                            p
                        ) as IRule
                    } // вызвать конструктор и передать ему необходимые значения, и привести это всё к IRule
                })
            )
        }

            val r: IRule = get(named("site.geniyz.ofp.rule.ByLangRule")) {
                parametersOf(
                    Context.NONE,
                    Context.NONE
                )
            }

        assertEquals("site.geniyz.ofp.rule.ByLangRule", r::class.qualifiedName)

    }
}

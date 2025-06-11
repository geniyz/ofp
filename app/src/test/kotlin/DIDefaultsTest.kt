@file:OptIn(ExperimentalUuidApi::class)

package site.geniyz.ofp

import org.junit.Test
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.koin.test.inject
import site.geniyz.ofp.cntxt.Context
import site.geniyz.ofp.core.UObject
import site.geniyz.ofp.di.appModule
import site.geniyz.ofp.repo.IRepository
import site.geniyz.ofp.repo.MemoRepo
import site.geniyz.ofp.rule.AbsoluteRule
import site.geniyz.ofp.rule.ByHourRule
import site.geniyz.ofp.rule.IRule
import site.geniyz.ofp.service.ILinksService
import site.geniyz.ofp.service.SimpleLinksService
import kotlin.uuid.ExperimentalUuidApi

class DIDefaultsTest: AutoCloseKoinTest() {

    @OptIn(ExperimentalUuidApi::class)
    @Test
    fun `Проверка корректности дефолтных определений`() {
        startKoin { modules(appModule) }

        val repo: IRepository = get()
        assert( repo is MemoRepo )

        val service: ILinksService = get()
        assert( service is SimpleLinksService )

        val ruleAbs: IRule by inject(named("site.geniyz.ofp.rule.AbsoluteRule")){ parametersOf(Context.NONE, Context.NONE) }
        assert( ruleAbs is AbsoluteRule )

        val ruleHour: IRule by inject(named("site.geniyz.ofp.rule.ByHourRule")){ parametersOf(Context.NONE, Context.NONE) }
        assert( ruleHour is ByHourRule )

    }
}

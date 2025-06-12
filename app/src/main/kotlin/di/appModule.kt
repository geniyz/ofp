package site.geniyz.ofp.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import site.geniyz.ofp.cntxt.Context
import site.geniyz.ofp.core.UObject
import site.geniyz.ofp.repo.*
import site.geniyz.ofp.service.*
import site.geniyz.ofp.rule.*

val appModule = module { // дефолтные реализации

    single(named("content404")) { Content("NOTFOUND") }

    // дефолтная реализация хранилища
    single { MemoRepo() as IRepository } // singleOf(::MemoRepo) { bind<IRepository>() }

    // дефолтная реализация «Сервиса»
    single { SimpleLinksService() as ILinksService } // singleOf(::SimpleLinksService) { bind<ILinksService>() }

    // вшитые правила
    factory( named(AbsoluteRule::class.qualifiedName!!) ){ (c: Context, p: UObject) -> AbsoluteRule(c, p) as IRule }
    factory( named(ByHourRule::class.qualifiedName!!)   ){ (c: Context, p: UObject) -> ByHourRule  (c, p) as IRule }

}

package site.geniyz.ofp

import org.junit.Test
import site.geniyz.ofp.di.appModule
import org.koin.test.verify.verify

class CheckModulesTest {

    @Test
    fun `check modules`(){
        appModule.verify()
    }
}
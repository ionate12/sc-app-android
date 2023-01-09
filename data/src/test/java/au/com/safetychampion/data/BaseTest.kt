package au.com.safetychampion.data

import au.com.safetychampion.data.util.IDispatchers
import org.junit.Rule
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

abstract class BaseTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            module {
                single<IDispatchers> { TestDispatchers() }
                declareModules().invoke(this)
            }
        )
    }

    abstract fun declareModules(): ModuleDeclaration
}

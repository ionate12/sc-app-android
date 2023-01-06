package au.com.safetychampion.di

import au.com.safetychampion.data.di.dataModule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.check.checkModules

class AppModules : KoinTest {

    // Verify the all module's dependencies is implemented.
    // This test runs before our app runs
    @Test
    fun checkAllModules() = checkModules {
        modules(
            appModules + dataModule
        )
    }
}

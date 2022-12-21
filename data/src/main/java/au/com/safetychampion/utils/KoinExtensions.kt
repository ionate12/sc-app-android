package au.com.safetychampion.utils

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

inline fun <reified T : Any> getKoinInstance(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): T {
    return object : KoinComponent {
        val value = get<T>(qualifier, parameters)
    }.value
}

inline fun <reified T : Any> injectKoin(
    qualifier: Qualifier? = null,
    mode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED,
    noinline parameters: ParametersDefinition? = null
) = lazy(mode) {
    getKoinInstance<T>()
}

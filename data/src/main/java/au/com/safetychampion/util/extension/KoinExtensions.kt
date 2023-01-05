package au.com.safetychampion.util

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

inline fun <reified T : Any> koinGet(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null
): T {
    return object : KoinComponent {
        val value = get<T>(qualifier, parameters)
    }.value
}

inline fun <reified T : Any> koinInject(
    qualifier: Qualifier? = null,
    mode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED,
    noinline parameters: ParametersDefinition? = null
) = lazy(mode) {
    object : KoinComponent {
        val value = get<T>(qualifier, parameters)
    }.value
}

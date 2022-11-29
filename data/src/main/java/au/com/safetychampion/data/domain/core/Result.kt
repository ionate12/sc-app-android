package au.com.safetychampion.data.domain.core

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val err: SCError) : Result<Nothing>()
}

package au.com.safetychampion.data.domain.usecase.pushnotification

import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.errorOf
import com.google.firebase.messaging.FirebaseMessaging
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class GetFirebaseTokenUseCase {
    suspend operator fun invoke(): Result<String> {
        // Using suspendCoroutine for bridging between firebase callback and coroutine!!
        return suspendCoroutine { cont ->
            FirebaseMessaging
                .getInstance().token
                .addOnCompleteListener { task ->
                    cont.resume(
                        if (task.isSuccessful) {
                            Result.Success(task.result)
                        } else {
                            errorOf("Failed in get firebase token due to: ${task.exception?.message}")
                        }
                    )
                }
        }
    }
}

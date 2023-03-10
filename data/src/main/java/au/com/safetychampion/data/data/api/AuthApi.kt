package au.com.safetychampion.data.data.api

import au.com.safetychampion.data.data.local.ISyncable
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.models.auth.LoginPL
import au.com.safetychampion.data.domain.models.auth.MfaVerifyPL
import au.com.safetychampion.data.domain.models.auth.MorphPL
import au.com.safetychampion.data.domain.models.auth.MultiLoginPL
import java.util.UUID

interface AuthApi {
    class Login(body: LoginPL) : NetworkAPI.Post("users/authenticate", body)
    class MfaVerify(body: MfaVerifyPL) : NetworkAPI.Post("users/mfa/verify", body)
    class MultiUserAuth(body: MultiLoginPL) : NetworkAPI.Post("users/multiuser/authorize", body)
    class Morph(override val body: MorphPL) : NetworkAPI.Post("users/morph", body), ISyncable {
        override fun customKey(): String {
            return "users/morph/${body.target._id}"
        }
    }
    class UnMorph(body: BasePL = BasePL.empty()) : NetworkAPI.Post("users/unmorph", body), ISyncable {
        override fun customKey(): String {
            return "users/unmorph/${UUID.randomUUID()}}"
        }
    }
    class GetWhoAmI() : NetworkAPI.Get("users/whoami")
}

package au.com.safetychampion.data.domain.models.auth

import au.com.safetychampion.data.data.BaseRepository
import au.com.safetychampion.data.data.api.AuthApi
import au.com.safetychampion.data.data.local.BaseAppDataStore
import au.com.safetychampion.data.data.local.StoreKey
import au.com.safetychampion.data.domain.core.Result
import au.com.safetychampion.data.domain.core.doOnSucceed
import au.com.safetychampion.data.domain.core.map
import au.com.safetychampion.data.domain.manager.IGsonManager
import au.com.safetychampion.data.domain.manager.ITokenManager
import au.com.safetychampion.data.domain.manager.IUserInfoManager
import au.com.safetychampion.data.domain.models.WhoAmI
import au.com.safetychampion.data.domain.uncategory.AppToken
import au.com.safetychampion.data.util.extension.koinGet
import au.com.safetychampion.data.util.extension.koinInject
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

interface IAuthRepository {
    suspend fun login(loginPL: LoginPL): Result<LoginResponse>
    suspend fun multiLogin(multiLoginPL: MultiLoginPL, loginPL: LoginPL): Result<LoginResponse.Single>
    suspend fun mfaVerify(mfaVerifyPL: MfaVerifyPL, loginPL: LoginPL): Result<LoginResponse>
    suspend fun morph(morphPL: MorphPL): Result<LoginResponse.Single>
    suspend fun unmorph(): Result<LoginResponse.Single>
    suspend fun whoami(): Result<WhoAmI>
}

class AuthRepository : BaseRepository(), IAuthRepository {

    private val gsonManager by koinInject<IGsonManager>()
    private val tokenManager by koinInject<ITokenManager>()
    private val appDataStore by koinInject<BaseAppDataStore>()
    private val userInfoManager by koinInject<IUserInfoManager>()

    private val customGson by lazy {
        gsonManager.gsonBuilder
            .registerTypeAdapter(LoginResponse::class.java, LoginResponseDeserializer())
            .create()
    }

    override suspend fun login(loginPL: LoginPL): Result<LoginResponse> {
        val jsonResult: Result<JsonObject> = AuthApi.Login(loginPL).call(objName = "")
        return jsonResult.map { customGson.fromJson(it, LoginResponse::class.java) }
            .doOnSucceed {
                updateToken(it)
                if (it is LoginResponse.Single) {
                    storeUserCredential(loginPL)
                    storeUserInfo(it.data.user)
                }
            }
    }

    override suspend fun multiLogin(
        multiLoginPL: MultiLoginPL,
        loginPL: LoginPL
    ): Result<LoginResponse.Single> {
        return AuthApi.MultiUserAuth(multiLoginPL).call<LoginResponse.Single>(objName = "")
            .doOnSucceed {
                updateToken(it)
                storeUserCredential(loginPL)
                storeUserInfo(it.data.user)
            }
    }

    override suspend fun mfaVerify(mfaVerifyPL: MfaVerifyPL, loginPL: LoginPL): Result<LoginResponse> {
        val jsonResult: Result<JsonObject> = AuthApi.MfaVerify(mfaVerifyPL).call(objName = "")
        return jsonResult.map { customGson.fromJson(it, LoginResponse::class.java) }
            .doOnSucceed {
                updateToken(it)
                if (it is LoginResponse.Single) {
                    storeUserCredential(loginPL)
                    storeUserInfo(it.data.user)
                }
            }
    }

    override suspend fun morph(morphPL: MorphPL): Result<LoginResponse.Single> {
        return AuthApi.Morph(morphPL).call<LoginEnv>(objName = "").map { LoginResponse.Single(it) }
            .doOnSucceed {
                updateToken(it)
                storeUserInfo(it.data.user)
            }
    }

    override suspend fun unmorph(): Result<LoginResponse.Single> {
        return AuthApi.UnMorph().call<LoginEnv>(objName = "").map { LoginResponse.Single(it) }
            .doOnSucceed {
                updateToken(it)
                storeUserInfo(it.data.user)
            }
    }

    override suspend fun whoami(): Result<WhoAmI> {
        return AuthApi.GetWhoAmI().call(objName = "")
    }

    private suspend fun updateToken(response: LoginResponse) {
        when (response) {
            is LoginResponse.Single -> {
                tokenManager.updateToken(AppToken.Authed(response.data.token))
            }
            is LoginResponse.Multi -> {
                tokenManager.updateToken(AppToken.MultiUser(response.data.token))
            }
            else -> Unit
        }
    }

    private suspend fun storeUserCredential(loginPL: LoginPL) {
        // store user credential
        appDataStore.store(StoreKey.UserCredential, loginPL)
    }

    private suspend fun storeUserInfo(user: LoginUser) {
        userInfoManager.storeUser(user)
    }
}

private class LoginResponseDeserializer : JsonDeserializer<LoginResponse> {
    private val gson: Gson by lazy { koinGet<IGsonManager>().gson }
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LoginResponse {
        val jsonObject = json!!.asJsonObject
        return when {
            jsonObject.has("mfa") -> {
                val data = gson.fromJson(jsonObject, MfaEnv::class.java)
                LoginResponse.MFA(data)
            }
            jsonObject["item"]?.asJsonObject?.has("multiuser") == true -> {
                val data = gson.fromJson(jsonObject, MultiLoginEnv::class.java)
                LoginResponse.Multi(data)
            }
            else -> {
                val data = gson.fromJson(jsonObject, LoginEnv::class.java)
                LoginResponse.Single(data)
            }
        }
    }
}

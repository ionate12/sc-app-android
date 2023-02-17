package au.com.safetychampion

sealed interface UiMessage {
    object RefreshUserInfo : UiMessage
}

package au.com.safetychampion.data.visitor.domain.models

import au.com.safetychampion.data.domain.models.SCBaseMessage
import com.google.gson.annotations.SerializedName

data class VisitorMessages(
    var pre: SCBaseMessage? = null,
    @SerializedName("in")
    var _in: SCBaseMessage? = null,
    var post: SCBaseMessage? = null
)

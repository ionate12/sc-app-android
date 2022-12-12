package au.com.safetychampion.data.domain.models.incidents

import com.google.gson.annotations.SerializedName

class IncidentSignOffPojo {
    @SerializedName("body")
    var review: IncidentReviewPojo = IncidentReviewPojo()
    var task: IncidentSignOffTaskPojo = IncidentSignOffTaskPojo()

    var errorMsg: String? = null

    constructor()
    constructor(msg: String) {
        errorMsg = msg
    }
}

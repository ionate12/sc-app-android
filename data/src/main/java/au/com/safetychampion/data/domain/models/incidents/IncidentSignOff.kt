package au.com.safetychampion.data.domain.models.incidents

data class IncidentSignOff(
    var review: IncidentReviewPojo = IncidentReviewPojo(),
    var task: IncidentSignOffTask = IncidentSignOffTask()
)

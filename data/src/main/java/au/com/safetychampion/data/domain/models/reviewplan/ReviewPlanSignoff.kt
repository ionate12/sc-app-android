package au.com.safetychampion.data.domain.models.reviewplan

data class ReviewPlanSignoff(
    var body: ReviewPlanReview = ReviewPlanReview(),
    var task: ReviewPlanSignoffTask = ReviewPlanSignoffTask(),
    var taskId: String? = null,
    var moduleId: String? = null,
    var sessionId: String? = null
) {
    constructor(body: ReviewPlanReview, task: ReviewPlanSignoffTask) : this(body = body, task = task, taskId = null)
}

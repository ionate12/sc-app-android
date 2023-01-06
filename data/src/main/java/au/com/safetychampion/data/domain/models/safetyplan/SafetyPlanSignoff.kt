package au.com.safetychampion.data.domain.models.safetyplan

data class SafetyPlanSignoff(
    val body: SafetyPlanReviewPojo,
    val task: SafetyPlanSignoffTask
)

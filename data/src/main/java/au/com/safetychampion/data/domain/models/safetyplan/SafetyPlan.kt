package au.com.safetychampion.data.domain.models.safetyplan

import au.com.safetychampion.data.domain.models.AllocationOf
import au.com.safetychampion.data.domain.models.CreatedBy
import au.com.safetychampion.data.domain.models.Tier

data class SafetyPlan(
    val _id: String,
    val tier: Tier,
    val name: String,
    val description: String,
    val frequencyKey: String,
    val frequencyValue: Long,
    val createdBy: CreatedBy,
    val tzDateCreated: String,
    val dateCreated: String,
    val allocationOf: AllocationOf
)

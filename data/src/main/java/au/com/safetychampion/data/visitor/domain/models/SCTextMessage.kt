package au.com.safetychampion.data.visitor.domain.models

import au.com.safetychampion.data.domain.models.SCBaseMessage

class SCTextMessage(
    override val type: String,
    override val value: String
) : SCBaseMessage

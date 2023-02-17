package au.com.safetychampion.data.domain.models

import au.com.safetychampion.data.domain.Attachment
import au.com.safetychampion.data.domain.base.BasePL
import au.com.safetychampion.data.domain.core.Signature
import au.com.safetychampion.data.domain.models.action.ActionLink
import au.com.safetychampion.data.domain.models.action.network.PendingActionPL
import au.com.safetychampion.data.domain.models.customvalues.CustomValue

/**
 * Model Components
 * All add-on properties of an object can be made.
 * Each of a components will be resolve separately before transforming to requestBody
 */

interface IBaseCusval

interface ICusval : IBaseCusval {
    var cusvals: MutableList<CustomValue>
}

interface ICategoryCusval : IBaseCusval {
    var categoryCusvals: MutableList<CustomValue>
}

interface ISubcategoryCusval : IBaseCusval {
    var subcategoryCusvals: MutableList<CustomValue>
}

interface IEnvCusval : IBaseCusval {
    var propOrEnvDamageCusvals: MutableList<CustomValue>
}

interface IForceNullValues {
    val forceNullKeys: List<String>
}

interface ISignature {
    var signatures: MutableList<Signature>
}

interface IAttachment {
    var attachments: MutableList<Attachment>
}

// Pending Actions
interface IPendingActionPL : IPendingAction {

    fun shouldIgnorePendingActionError(): Boolean = true

    fun onPendingActionsCreated(links: List<ActionLink>): BasePL
}

interface IPendingAction {
    var pendingActions: MutableList<PendingActionPL>
}

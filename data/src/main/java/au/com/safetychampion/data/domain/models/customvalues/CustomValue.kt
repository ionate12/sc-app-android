package au.com.safetychampion.data.domain.models.customvalues

import au.com.safetychampion.data.domain.models.TimeField

typealias CustomValue = BaseCustomValue<*>

sealed class BaseCustomValue<T : Any>(
    var _id: String,
    var type: CusvalType,
    var title: String,
    mValue: T? = null,
    var description: String? = null,
    var required: Boolean = false,
    var options: List<CustomValueOption> = listOf()
) {
    var value: T? = mValue
        protected set

    fun trySet(value: Any?): Boolean {
        return try {
            updateValue(value as? T)
            true
        } catch (e: Exception) {
            false
        }
    }
    protected abstract fun updateValue(value: T?)

    abstract fun isNullOrEmpty(): Boolean

    internal fun toPayload(): CustomValuePL {
        val mValue = if (isNullOrEmpty()) null else value
        return CustomValuePL(_id, type.value, title, mValue)
    }
}

class CustomValueString(
    _id: String,
    type: CusvalType,
    title: String,
    value: String? = null,
    description: String? = null,
    required: Boolean = false
) : BaseCustomValue<String>(_id, type, title, value, description, required) {
    public override fun updateValue(value: String?) {
        this.value = value
    }
    override fun isNullOrEmpty(): Boolean = value.isNullOrEmpty()
}

class CustomValueInt(
    _id: String,
    type: CusvalType,
    title: String,
    value: Int? = null,
    description: String? = null,
    required: Boolean = false
) : BaseCustomValue<Int>(_id, type, title, value, description, required) {
    public override fun updateValue(value: Int?) {
        this.value = value
    }
    override fun isNullOrEmpty(): Boolean = value == null
}

class CustomValueTime(
    _id: String,
    title: String,
    value: TimeField? = null,
    description: String? = null,
    required: Boolean = false
) : BaseCustomValue<TimeField>(_id, CusvalType.Time, title, value, description, required) {
    public override fun updateValue(value: TimeField?) {
        this.value = value
    }
    override fun isNullOrEmpty(): Boolean = value == null
}

class CustomValueDropdown(
    _id: String,
    type: CusvalType,
    title: String,
    value: List<List<String>>? = null,
    description: String? = null,
    required: Boolean = false
) : BaseCustomValue<List<List<String>>>(_id, type, title, value, description, required) {
    public override fun updateValue(value: List<List<String>>?) {
        this.value = value
    }

    override fun isNullOrEmpty(): Boolean = value.isNullOrEmpty()
}

class CustomValueCurrency(
    _id: String,
    type: CusvalType,
    title: String,
    value: CurrencyValue? = null,
    description: String? = null,
    required: Boolean = false
) : BaseCustomValue<CurrencyValue>(_id, type, title, value, description, required) {
    override fun updateValue(value: CurrencyValue?) {
        this.value = value
    }
    override fun isNullOrEmpty(): Boolean = value == null
}

internal data class CustomValuePL(
    var _id: String,
    val type: String,
    val title: String,
    val value: Any?
)

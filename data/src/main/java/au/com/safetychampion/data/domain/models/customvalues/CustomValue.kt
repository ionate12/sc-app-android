package au.com.safetychampion.data.domain.models.customvalues

import au.com.safetychampion.data.domain.models.TimeField

typealias CustomValue = BaseCustomValue<*>
typealias CustomValuePL = BaseCustomValuePL<*>

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
        try {
            this.value = value as T?
            return true
        } catch (e: Exception) {
            return false
        }
//        when (this) {
//            is CustomValueCurrency -> this.value = value as CurrencyValue?
//            is CustomValueDropdown -> this.value = value as List<List<String>>?
//            is CustomValueInt -> this.value = value as Int?
//            is CustomValueString -> this.value = value as String?
//            is CustomValueTime -> this.value = value as TimeField?
//        }
    }
    protected abstract fun updateValue(value: T?)

    fun toPayload(): BaseCustomValuePL<*> = when (this) {
        is CustomValueDropdown -> CustomValueDropdownPL(_id, type, title, value)
        is CustomValueInt -> CustomValueIntPL(_id, type, title, value)
        is CustomValueString -> CustomValueStringPL(_id, type, title, value)
        is CustomValueTime -> CustomValueTimePL(_id, type, title, value)
        is CustomValueCurrency -> CustomValueCurrencyPL(_id, type, title, value)
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
}

sealed class BaseCustomValuePL<T : Any>(
    var _id: String,
    val type: String,
    val title: String,
    val value: T?
) {
    abstract fun isNullOrEmpty(): Boolean
}

class CustomValueStringPL(
    _id: String,
    type: CusvalType,
    title: String,
    value: String? = null
) : BaseCustomValuePL<String>(_id, type.value, title, value) {
    override fun isNullOrEmpty(): Boolean = value.isNullOrEmpty()
}

class CustomValueIntPL(
    _id: String,
    type: CusvalType,
    title: String,
    value: Int? = null
) : BaseCustomValuePL<Int>(_id, type.value, title, value) {
    override fun isNullOrEmpty(): Boolean = value == null
}

class CustomValueTimePL(
    _id: String,
    type: CusvalType,
    title: String,
    value: TimeField? = null
) : BaseCustomValuePL<TimeField>(_id, type.value, title, value) {
    override fun isNullOrEmpty(): Boolean = value == null
}

class CustomValueDropdownPL(
    _id: String,
    type: CusvalType,
    title: String,
    value: List<List<String>>? = null
) : BaseCustomValuePL<List<List<String>>>(_id, type.value, title, value) {
    override fun isNullOrEmpty(): Boolean = value.isNullOrEmpty()
}

class CustomValueCurrencyPL(
    _id: String,
    type: CusvalType,
    title: String,
    value: CurrencyValue? = null
) : BaseCustomValuePL<CurrencyValue>(_id, type.value, title, value) {
    override fun isNullOrEmpty(): Boolean = value == null
}

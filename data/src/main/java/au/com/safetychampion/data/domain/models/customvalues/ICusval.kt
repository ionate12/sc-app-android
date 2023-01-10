package au.com.safetychampion.data.domain.models.customvalues

interface IBaseCusval
interface ICusval : IBaseCusval {
    var cusvals: MutableList<CustomValue>
}

interface ICategoryCusval : IBaseCusval {
    var categoryCusvals: MutableList<CustomValue>
}

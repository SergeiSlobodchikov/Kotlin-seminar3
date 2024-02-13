data class Person(val name: String) {

    var phoneList: MutableSet<String>? = null
        get() = field
        set(value) {
            field = value
        }
    var emailList: MutableSet<String>? = null
        get() = field
        set(value) {
            field = value
        }

    override fun toString(): String {
        return "Имя: $name Телефон: $phoneList email: $emailList"
    }
}
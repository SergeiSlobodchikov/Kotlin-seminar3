data class Person(val name: String) {

    var phoneList: MutableList<String>? = null
    var emailList: MutableList<String>? = null

    override fun toString(): String {
        return "Имя: $name Телефон: $phoneList email: $emailList"
    }

    fun addPhone(phone: String) {
        if (phoneList == null) {
            phoneList = mutableListOf(phone)
        } else {
            phoneList?.add(phone)
        }
    }

    fun addEmail(email: String) {
        if (emailList == null) {
            emailList = mutableListOf(email)
        } else {
            emailList?.add(email)
        }
    }

}
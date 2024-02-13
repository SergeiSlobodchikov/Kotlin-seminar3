class FindCommand(val value: String) : Command() {
    override fun isValid(): Boolean {
        return when (value) {
            "phone" -> value.matches("^\\+?\\d+$".toRegex())
            "email" -> value.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex())
            else -> false
        }
    }
}
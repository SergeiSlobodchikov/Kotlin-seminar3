import java.io.File
import java.util.*


fun main() {
    val contacts = mutableListOf<Person>()
    while (true) {
        println("Введите команду:")
        val command = readCommand()
        when {
            command is AddCommand -> {
                if (command.isValid()) {
                    val contact = contacts.find { it.name == command.name }
                    if (contact == null) {
                        val newContact = Person(command.name)
                        personAdd(command,newContact)
                        contacts.add(newContact)
                    } else {
                        personAdd(command,contact)
                        contacts.add(contact)
                    }
                } else {
                    printHelp()
                }
            }
            command is ShowCommand -> {

                if (contacts.isNotEmpty()) {
                    val contact = contacts.find { it.name == command.name }
                    if (contact != null) {
                        personShow(contact)
                    }
                } else {
                    println("Not initialized")
                    printHelp()
                }
            }

            command is FindCommand -> {
                if (contacts.isNotEmpty()) {
                    println(command.value)
                    printResult(find(command.value, contacts))
                } else {
                    printHelp()
                }
            }
            command is ExportCommand -> {
                if (contacts.isNotEmpty()) {
                    val `string-json`:String = totResult(contacts)
                    File("C:/Users/Acer/Desktop/Kotlin-seminar3/myfile.json").writeText(`string-json`.toJson())
                    println("  ")
                } else {
                    println("Not initialized")
                    printHelp()
                }
            }
            command is HelpCommand -> {
                printHelp()
            }
            command is ExitCommand -> {
                break
            }
            else -> {
                println("Ошибка: неизвестная команда $command")
            }
        }
    }
}


fun printHelp() {
    println("Команды:")
    println("add <Имя> phone <Номер телефона> - добавить номер телефона")
    println("add <Имя> email <Адрес электронной почты> - добавить адрес электронной почты")
    println("show <Имя> - показать контакт с именем <Имя>")
    println("find <Номер или email> - показать контакт c <Номер или email>")
    println("export - экспортирует внесенные данные")
    println("help - отобразить эту помощь")
    println("exit - выход из программы")
}

fun readCommand(): Command {
    val scanner = Scanner(System.`in`)
    val command = scanner.nextLine()
    val words = command.split(" ").toList().map { it.trim() }.filter { it.isNotEmpty() }
    val commandWord = words.first()
    val remainingWords = words.drop(1)

    return when (commandWord) {
        "add" -> {
            val (name, type, value) = remainingWords.take(3).toTypedArray()
            AddCommand(name, type, value)
        }
        "show" -> {
            val (name) = remainingWords.take(1).toTypedArray()
            ShowCommand(name)
        }
        "find" -> {
            val (value ) = remainingWords.take(1).toTypedArray()
            FindCommand(value)
        }
        "export" -> {
            ExportCommand()
        }
        "help" -> {
            HelpCommand()
        }
        "exit" -> {
            ExitCommand()
        }
        else -> {
            println("Ошибка: неизвестная команда $command")
            HelpCommand()
        }
    }
}

fun personAdd(addCommand: AddCommand, person: Person) {
    if(addCommand.type == "phone"){
        if (person.phoneList == null) {
            person.phoneList = mutableSetOf(addCommand.value)
            println("added1 ${person.name} phone ${person.phoneList}")
        } else {
            person.phoneList?.add(addCommand.value)
            println("added2 ${person.name} phone ${person.phoneList}")
        }
    } else if (addCommand.type == "email"){
        if (person.emailList == null) {
            person.emailList = mutableSetOf(addCommand.value)
            println("added1 ${person.name} email ${person.emailList}")
        } else {
            person.emailList?.add(addCommand.value)
            println("added2 ${person.name} email ${person.emailList}")
        }
    }
}



fun personShow(person: Person) {
    if (person.phoneList != null || person.emailList != null) {
        println("Name: ${person.name} Phone: ${person.phoneList} email: ${person.emailList}e")
    }
}


fun find(query: String, contacts: MutableList<Person>): List<Person> {
    val result = mutableListOf<Person>()
    if (query.matches("^\\+[0-9]+$".toRegex())) {
        for (person in contacts) {
            if ( person.phoneList?.contains(query) == true) {
                result.add(person)
            }
        }
    } else if (query.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{3,}$".toRegex())) {
        for (person in contacts) {
            if (person.emailList?.contains(query) == true) {
                result.add(person)
            }
        }
    }
    return result
}

fun printResult(result: List<Person>) {
    for (person in result) {
        println("Name: ${person.name} Phone: ${person.phoneList} email: ${person.emailList}")
    }
}

fun totResult(result: List<Person>): String {
    val list = result.toSet().map { person ->  ("Name: ${person.name} Phone: ${person.phoneList} email: ${person.emailList}")
    }
    return list.joinToString("\n")
}
fun Any.toJson(): String {
    return when (this) {
        is String -> "\"$this\""
        is Int -> this.toString()
        is Long -> this.toString()
        is Double -> this.toString()
        is Float -> this.toString()
        is Boolean -> this.toString()
        else -> throw IllegalArgumentException("Не поддерживаемый тип данных")
    }
}

fun main() {
    val userName = readLine()!!

    fun printUser(userName: String = "secret user") =
        println("Hello, $userName!")

    if (userName == "HIDDEN") printUser() else printUser(userName)
}
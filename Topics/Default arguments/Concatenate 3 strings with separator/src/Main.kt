fun main() {
    fun concat(s: Array<String>, sep: String = " ") = s.joinToString(sep)

    val arr = Array(3) { readLine()!! }
    val sep = readLine()!!

    println(if (sep == "NO SEPARATOR") concat(arr) else concat(arr, sep))
}
import kotlin.math.max
fun main() {
    var n = 0
    for (i in 1..4) n = max(readLine()!!.toInt(), n)
    println(n)
}
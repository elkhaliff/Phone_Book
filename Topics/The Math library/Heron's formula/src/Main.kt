import kotlin.math.sqrt

fun main() {
    val(a, b, c) = Array(3) { readLine()!!.toDouble() }
    val p: Double = (a + b + c) / 2
    println(sqrt(p * (p - a) * (p - b) * (p - c)))
}
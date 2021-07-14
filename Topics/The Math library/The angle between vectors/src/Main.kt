import kotlin.math.acos
import kotlin.math.sqrt

fun main() {
    val a = readLine()!!.split(' ').map { it.toDouble() }
    val b = readLine()!!.split(' ').map { it.toDouble() }

    val ma = sqrt(a[0] * a[0] + a[1] * a[1])

    val mb = sqrt(b[0] * b[0] + b[1] * b[1])

    val sc = a[0] * b[0] + a[1] * b[1]
    val res = acos(sc / ma / mb) * (180 / 3.14)

    println(res.toInt())
}
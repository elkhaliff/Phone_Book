package phonebook

import java.io.File
import kotlin.math.floor
import kotlin.math.min
import kotlin.math.sqrt

fun bubbleSort(strings: Array<Array<String>>, timeLimit: Long): Boolean {
    var start = System.currentTimeMillis()
    var stop: Long
    var swap = true
    var shift = 0
//    for (shift in 0 until (strings.size - 1))
    while (swap) {
        if (shift % 1000 == 0) {
            stop = System.currentTimeMillis()
            println("$shift - ${formatDate(stop - start)}")
            start = stop // !!! delete this
//            if (timeCurr > timeLimit) return false
        }
        swap = false
        for (currPos in 0 until (strings.size - shift - 1)) {
            if (strings[currPos][1] > strings[currPos + 1][1]) {
                strings[currPos] = strings[currPos + 1].also { strings[currPos + 1] = strings[currPos] }
                swap = true
            }
        }
        shift++
//        if (!step) return true
    }
    return true
}

fun quicksort(strings: List<Array<String>>): List<Array<String>>{
    if (strings.size < 2){
        return strings
    }
    val pivot = strings[strings.size/2][1]

    val equal = strings.filter { it[1] == pivot }

    val less = strings.filter { it[1] < pivot }

    val greater = strings.filter { it[1] > pivot }

    return quicksort(less) + equal + quicksort(greater)
}

fun linearSearch(listFind: List<String>, arrDirect: Array<Array<String>>): Int{
    var found = 0
    for (line in listFind) {
        for (dir in arrDirect) {
            if (line == dir[1]) {
                found++
                continue
            }
        }
    }
    return found
}

fun backwardSearch(findStr: String, arrDirect: Array<Array<String>>, currPos: Int, prevPos: Int): Int {
    for (i in currPos downTo prevPos) {
        if (findStr == arrDirect[i][1])  return 1
    }
    return 0
}

fun jumpSearch(listFind: List<String>, arrDirect: Array<Array<String>>): Int{
    var found = 0
    val blockSize = floor(sqrt(arrDirect.size.toDouble())).toInt()
    for (line in listFind) {
        var currBlock = 0
        if (line == arrDirect[currBlock][1]) {
            found++
            continue
        }
        while (currBlock < arrDirect.size - 1) {
            currBlock = min(arrDirect.size - 1, currBlock + blockSize)
            if (line <= arrDirect[currBlock][1])
                break
        }
        found += if (currBlock == arrDirect.size - 1 && line > arrDirect[currBlock][1]) 0
        else backwardSearch(line, arrDirect, currBlock, currBlock - blockSize)
    }
    return found
}

fun formatDate(dt: Long): String = String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", dt)


fun main() {
    val fileFind = "D:/test/find.txt"
    val fileDir = "D:/test/directory.txt"
//    val fileDir = "D:/test/directory_out.txt"
//    val fileFind = "D:/test/small_find.txt"
//    val fileDir = "D:/test/small_directory.txt"
    val listFind = File(fileFind).readLines()
    val listDirect = File(fileDir).readLines()
    val arrTemp = listDirect.toTypedArray()
    val arrDirect: Array<Array<String>> = Array(arrTemp.size) { arrayOf() }
    var i = 0
    for (str in arrTemp) { arrDirect[i++] = arrayOf(str.substringBefore(' '), str.substringAfter(' ')) }

    println("Start searching (linear search)...")
    var time = System.currentTimeMillis()
    var found = linearSearch(listFind, arrDirect)
    var timeSearch = System.currentTimeMillis() - time
    var timeTaken = formatDate(timeSearch)
    println("Found $found / ${listFind.size} entries. Time taken: $timeTaken")
    println()

    println("Start searching (bubble sort + jump search)...")
    time = System.currentTimeMillis()

    //val sortOK = bubbleSort(arrDirect, timeSearch * 10)
    val sortOK = true
    arrDirect = quicksort(arrDirect)
    val timeSort = System.currentTimeMillis() - time

    found = if (!sortOK) linearSearch(listFind, arrDirect)
    else jumpSearch(listFind, arrDirect)

    timeTaken = formatDate(System.currentTimeMillis() - time)
    print("Sorting time: $timeTaken")
    println(if (!sortOK) " - STOPPED, moved to linear search"  else "")

    timeSearch = System.currentTimeMillis() - time - timeSort

    timeTaken = formatDate(timeSearch + timeSort)
    println("Found $found / ${listFind.size} entries. Time taken: $timeTaken")

    timeTaken = formatDate(timeSort)
    println("Sorting time: $timeTaken")

    timeTaken = formatDate(timeSearch)
    println("Searching time: $timeTaken")
}
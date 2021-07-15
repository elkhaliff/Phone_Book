package phonebook

import java.io.File
import kotlin.math.floor
import kotlin.math.min
import kotlin.math.sqrt

fun bubbleSort(strings: Array<Array<String>>) {
    var swap = true
    var shift = 0
    while (swap) {
        swap = false
        for (currPos in 0 until (strings.size - shift - 1)) {
            if (strings[currPos][1] > strings[currPos + 1][1]) {
                strings[currPos] = strings[currPos + 1].also { strings[currPos + 1] = strings[currPos] }
                swap = true
            }
        }
        shift++
    }
}

fun quicksort(strings: Array<Array<String>>): Array<Array<String>>{
    if (strings.size < 2)
        return strings
    val pivot = strings[strings.size/2][1]
    val equal = (strings.filter { it[1] == pivot } ).toTypedArray()
    val less = (strings.filter { it[1] < pivot } ).toTypedArray()
    val greater = (strings.filter { it[1] > pivot } ).toTypedArray()
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
    for (i in currPos downTo prevPos)
        if (findStr == arrDirect[i][1])  return 1
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

fun binarySearch(array: Array<Array<String>>, elem: String, left: Int, right: Int): Int {
    if (left > right) return 0
    val mid = left + (right - left) / 2
    return when {
        elem == array[mid][1] -> 1 // mid  (if need index)
        elem < array[mid][1] -> binarySearch(array, elem, left, mid - 1)
        else -> binarySearch(array, elem, mid + 1, right)
    }
}

fun binarySearchAll(listFind: List<String>, arrDirect: Array<Array<String>>): Int {
    var found = 0
    for (line in listFind)
        found += binarySearch(arrDirect, line, 0, arrDirect.lastIndex)
    return found
}

fun hashMapSearch(listFind: List<String>, hashMap: HashMap<String, Int>): Int {
    var found = 0
    for (line in listFind)
        if (hashMap.contains(line)) found++
    return found
}

fun printResult(found: Int, findSize: Int, time: Long, timeSort: Long, isHashMap: Boolean = false) {
    val timeSearch = System.currentTimeMillis() - time - timeSort
    var timeTaken = formatDate(timeSearch + timeSort)
    println("Found $found / $findSize entries. Time taken: $timeTaken")
    timeTaken = formatDate(timeSort)
    println("${if (isHashMap) "Creating" else "Sorting"} time: $timeTaken")
    timeTaken = formatDate(timeSearch)
    println("Searching time: $timeTaken")
}

fun formatDate(dt: Long): String = String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", dt)

fun toArrArr(listDirect: List<String>): Array<Array<String>> {
    val arrTemp = listDirect.toTypedArray()
    val arrDirect: Array<Array<String>> = Array(arrTemp.size) { arrayOf() }
    var i = 0
    for (str in arrTemp) { arrDirect[i++] = arrayOf(str.substringBefore(' '), str.substringAfter(' ')) }
    return arrDirect
}

fun toHashMap(listDirect: List<String>): HashMap<String, Int> {
    val arrTemp = listDirect.toTypedArray()
    val hashMap: HashMap<String, Int> = hashMapOf()
    for (str in arrTemp) { hashMap.put(str.substringAfter(' '), str.substringBefore(' ').toInt()) }
    return hashMap
}

fun main() {
    val fileFind = "D:/test/find.txt"
    val fileDir = "D:/test/directory.txt"
    val fileDirOut = "D:/test/directory_out.txt"
    val listFind = File(fileFind).readLines()
    val arrDirectOut = toArrArr(File(fileDirOut).readLines())

    println("Start searching (linear search)...")
    var time = System.currentTimeMillis()
    var found = linearSearch(listFind, arrDirectOut)
    val timeTaken = formatDate(System.currentTimeMillis() - time)
    println("Found $found / ${listFind.size} entries. Time taken: $timeTaken")
    println()

    println("Start searching (bubble sort + jump search)...")
    time = System.currentTimeMillis()
    bubbleSort(arrDirectOut)
    var timeSort = System.currentTimeMillis() - time
    found = jumpSearch(listFind, arrDirectOut)
    printResult(found, listFind.size, time, timeSort)
    println()

    println("Start searching (quick sort + binary search)...")
    time = System.currentTimeMillis()
    var arrDirect = toArrArr(File(fileDir).readLines())
    arrDirect = quicksort(arrDirect)
    timeSort = System.currentTimeMillis() - time
    found = binarySearchAll(listFind, arrDirect)
    printResult(found, listFind.size, time, timeSort)
    println()

    println("Start searching (hash table)...")
    time = System.currentTimeMillis()
    var hashMap = toHashMap(File(fileDir).readLines())
    timeSort = System.currentTimeMillis() - time
    found = hashMapSearch(listFind, hashMap)
    printResult(found, listFind.size, time, timeSort, true)
}
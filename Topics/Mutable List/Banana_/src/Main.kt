fun solution(strings: MutableList<String>, str: String): MutableList<String> {
    for ((ind, value) in strings.withIndex()) {
        if (value == str) strings[ind] = "Banana"
    }
    return strings
}
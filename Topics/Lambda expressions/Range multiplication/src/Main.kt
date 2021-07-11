val lambda: (Long, Long) -> Long = { a, b ->
    var sum = 1L
    for (i in a..b)
        sum *= i
    sum
}

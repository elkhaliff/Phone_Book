fun generate(functionName: String): (Int) -> Int =
    when (functionName) {
        "identity" -> { x: Int -> x }
        "half" -> { x: Int -> x / 2 }
        else -> { _: Int -> 0 }
    }
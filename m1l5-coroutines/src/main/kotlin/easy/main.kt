package easy

import kotlinx.coroutines.*

fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    println("START $startTime")

    val numbers = generateNumbers()
    val toFind = 10
    val toFindOther = 1000

    val toFindResult = async { findNumberInList(toFind, numbers) }
    val toFindOtherResult = async { findNumberInList(toFindOther, numbers) }

    val foundNumbers = listOf(toFindResult.await(), toFindOtherResult.await())

    foundNumbers.forEach {
        if (it != -1) {
            println("Your number $it found!")
        } else {
            println("Not found number $toFind || $toFindOther")
        }
    }

    val finishTime = System.currentTimeMillis()
    println("FINISH $finishTime; delta: ${finishTime - startTime}")
}
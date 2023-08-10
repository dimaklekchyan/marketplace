package hard

import hard.dto.Dictionary
import kotlinx.coroutines.*
import mu.KotlinLogging
import java.io.File

private val log = KotlinLogging.logger {}

suspend fun main() {
    val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        log.info("${context[CoroutineName]}: $exception")
    }
    val scope = CoroutineScope(Dispatchers.IO + exceptionHandler + SupervisorJob())

    val dictionaryApi = DictionaryApi()
    val words = FileReader.readFile().split(" ", "\n").toSet()

    scope.launch (CoroutineName("1")) {
        val dictionaries = findWords(dictionaryApi, words, Locale.EN)
        dictionaries.map { dictionary ->
            print("For word ${dictionary.word} i found examples: ")
            println(dictionary.meanings.map { definition -> definition.definitions.map { it.example } })
        }
    }.join()
}

private suspend fun findWords(dictionaryApi: DictionaryApi, words: Set<String>, locale: Locale): List<Dictionary> {
    return withContext(Dispatchers.IO) {
        val deferreds = words.map {
            async (SupervisorJob()) {
                dictionaryApi.findWord(locale, it)
            }
        }
        deferreds.mapNotNull {
            try {
                it.await()
            } catch (ex: RuntimeException) {
                null
            }
        }
    }
}


object FileReader {
    fun readFile(): String =
        File(this::class.java.classLoader.getResource("words.txt")?.toURI() ?: throw RuntimeException("Can't read file")).readText()
}

package hard

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import hard.dto.Dictionary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.Response

class DictionaryApi(
    private val objectMapper: ObjectMapper = jacksonObjectMapper()
) {

    suspend fun findWord(locale: Locale, word: String): Dictionary {
        return withContext(Dispatchers.IO) {
            val url = "$DICTIONARY_API/${locale.code}/$word"
            println("Searching $url")
            getBody(HttpClient.get(url).execute()).first()
        }
    }


    private suspend fun getBody(response: Response): List<Dictionary> {
        return withContext(Dispatchers.Default) {
            if (!response.isSuccessful) {
                throw RuntimeException("Not found word")
            }

            response.body?.let {
                objectMapper.readValue(it.string())
            } ?: throw RuntimeException("Body is null by some reason")
        }
    }
}
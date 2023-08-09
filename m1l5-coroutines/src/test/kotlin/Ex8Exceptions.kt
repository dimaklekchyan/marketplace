package ru.otus.m1l5

import kotlinx.coroutines.*
import mu.KotlinLogging
import org.junit.Test
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class Ex8Exceptions {
    private val log = KotlinLogging.logger {}

    @Test
    fun invalid() {
        try {
            val scope = CoroutineScope(Dispatchers.Default)
            scope.launch {
                Integer.parseInt("a")
            }
        } catch (e: Exception) {
            log.info("CAUGHT!")
        }

        Thread.sleep(2000)
        log.info("COMPLETED!")
    }

    @Test
    fun invalid2() {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            try {
                launch {
                    Integer.parseInt("a")
                }
            } catch (e: Exception) {
                log.info("CAUGHT!")
            }
        }

        Thread.sleep(2000)
        log.info("COMPLETED!")
    }

    private fun handler(where: String) = CoroutineExceptionHandler { context, exception ->
        log.info("CAUGHT at $where ${context[CoroutineName]}: $exception")
    }

    @Test
    fun handler() {
        val scope = CoroutineScope(Dispatchers.Default + handler("top"))
        scope.launch(handler("launch")) {
            Integer.parseInt("a")
        }

        Thread.sleep(2000)
        log.info("COMPLETED!")
    }

    @Test
    fun handler2() {
        val scope = CoroutineScope(Dispatchers.Default + handler("top"))
        scope.launch(CoroutineName("1")) {
            launch(handler("child") + CoroutineName("1.1")) {
                Integer.parseInt("a")
            }
        }

        Thread.sleep(2000)
        log.info("COMPLETED!")
    }

    @Test
    fun cancel() {
        val scope = CoroutineScope(Dispatchers.Default + handler("top"))
        scope.launch {

            launch { // 3 если сюда добавить handler(), то ничего не изменится
                launch {
                    delay(100) // 1 дочерние отменены
                    log.info("cor1")
                }
                launch {
                    delay(100)
                    log.info("cor2")
                }

                Integer.parseInt("a")
            }
            launch { // 2 сиблинг тоже отменен
                delay(100)
                log.info("cor3")
            }
        }

        Thread.sleep(2000)

        scope.launch {
            log.info("No chance") // 4 scope отменен, в нем больше ничего не запустить
        }

        val scope2 = CoroutineScope(Dispatchers.Default)
        scope2.launch { // другой скоуп никак не затронут
            log.info("I am alive")
        }

        Thread.sleep(500)

        log.info("COMPLETED!")
    }

    @Test
    fun supervisorJob() {
        val scope = CoroutineScope(Dispatchers.Default + handler("top") + SupervisorJob())
        scope.launch {
            launch {
                delay(100) // 1 сиблинги отменены
                log.info("cor1")
            }
            launch {
                delay(100)
                log.info("cor2")
            }
            launch {
                delay(50)
                Integer.parseInt("a")
            }

            delay(100)
            log.info("super") // 1 сама джоба отменена
        }

        Thread.sleep(2000)

        scope.launch {
            log.info("I am alive") // 3 жив
        }


        Thread.sleep(500)

        log.info("COMPLETED!")
    }

    @Test
    fun supervisorJob2() {
        val scope = CoroutineScope(Dispatchers.Default + handler("top") + SupervisorJob())
        scope.launch() {// ***
            launch {
                delay(100)
                log.info("cor1")
            }
            val deferred = async(SupervisorJob()) {
                delay(50)
                Integer.parseInt("a") // 1 - комментируем
                log.info("cor2") // 1 - ops
            }
            deferred.await()

            delay(100)
            log.info("super")
        }

        // scope.cancel() // 1 - раскомментируем

        Thread.sleep(2000)

        log.info("COMPLETED!")
    }

    @Test
    fun supervisorJob3() {
        val scope = CoroutineScope(Dispatchers.Default + handler("top"))
        scope.launch {// ***
            launch {
                delay(100)
                log.info("cor1")
            }
            launch(SupervisorJob(coroutineContext[Job])) {
                launch {
                    delay(10)
                    Integer.parseInt("a") // 1 - комментируем
                }
                launch {
                    delay(50)
                    log.info("cor2") // отменится
                }
                delay(50)
                log.info("cor3") // отменится
            }

            delay(100)
            log.info("super")
        }

        // scope.cancel() // 1 - раскомментируем

        Thread.sleep(2000)

        log.info("COMPLETED!")
    }

    @Test
    fun handler3() {
        val scope = CoroutineScope(Dispatchers.Default + handler("top"))
        scope.launch(CoroutineName("1")) {
            launch(handler("child") + CoroutineName("1.1") + SupervisorJob(coroutineContext[Job])) {
                Integer.parseInt("a")
            }
        }

        Thread.sleep(2000)
        log.info("COMPLETED!")
    }

    @Test
    fun async1() {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            launch {
                delay(100)
                log.info("cor1")
            }
            val x = async {
                Integer.parseInt("a")
            }

            delay(100)

            log.info("1")
            try {
                x.await()
            } catch (e: Exception) {
                log.info("CAUGHT!")
            }
        }

        Thread.sleep(2000)

        log.info("COMPLETED!")
    }

    @Test
    fun async2() {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            launch {
                delay(100)
                log.info("cor1")
            }
            val x = async(SupervisorJob(coroutineContext[Job]) + handler("async")) {
                Integer.parseInt("a")
            }

            delay(100)

            log.info("1")
            try {
                x.await()
            } catch (e: Exception) {
                log.info("CAUGHT!")
            }
        }

        Thread.sleep(2000)

        log.info("COMPLETED!")
    }

    private suspend fun doSomething(): Int {
        return coroutineScope {
            val result = async {
                Integer.parseInt("a")
            }
            result.await()
        }
    }

    @Test
    fun myFun() {
        val scope = CoroutineScope(
            Dispatchers.IO
                    + handler("scope")
                    + SupervisorJob()
        )
        log.info("FUN start")

        scope.launch(CoroutineName("1")) {
            coroutineScope {
                val deferred = List(5) { index ->
                    async(CoroutineName("1_$index") + SupervisorJob()) {
                        log.info("${this.coroutineContext[CoroutineName]?.name} start")
                        if(index == 1) Integer.parseInt("a")
                        delay(1100)
                        log.info("${this.coroutineContext[CoroutineName]?.name} end")
                    }
                }.toTypedArray()
                awaitAll(*deferred)
            }
//            async(CoroutineName("1_1") + SupervisorJob()) {
//                log.info("${this.coroutineContext[CoroutineName]?.name} start")
//                Integer.parseInt("a")
//                log.info("${this.coroutineContext[CoroutineName]?.name} end")
//            }.await()
//            launch(CoroutineName("1_2")) {
//                log.info("${this.coroutineContext[CoroutineName]?.name} start")
//                delay(1000)
//                log.info("${this.coroutineContext[CoroutineName]?.name} end")
//            }
        }

        scope.launch(CoroutineName("2")) {
            launch(CoroutineName("2_1")) {
                log.info("${this.coroutineContext[CoroutineName]?.name} start")
                delay(1000)
                log.info("${this.coroutineContext[CoroutineName]?.name} end")
            }
            launch(CoroutineName("2_2")) {
                log.info("${this.coroutineContext[CoroutineName]?.name} start")
                delay(1000)
                log.info("${this.coroutineContext[CoroutineName]?.name} end")
            }
        }
        Thread.sleep(5000)
        log.info("FUN end")
    }
}
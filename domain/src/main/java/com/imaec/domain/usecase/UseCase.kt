package com.imaec.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class UseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(): R {
        return withContext(coroutineDispatcher) {
            execute()
        }
    }

    suspend operator fun invoke(parameters: P): R {
        return withContext(coroutineDispatcher) {
            execute(parameters)
        }
    }

    suspend operator fun invoke(parameters: P, onError: (e: Exception) -> Unit = {}): R? {
        return try {
            withContext(coroutineDispatcher) {
                execute(parameters)
            }
        } catch (e: Exception) {
            onError(e)
            null
        }
    }

    @Throws(RuntimeException::class)
    protected open suspend fun execute(): R {
        throw NotImplementedError("execute() is not implemented")
    }

    @Throws(RuntimeException::class)
    protected open suspend fun execute(parameters: P): R {
        throw NotImplementedError("execute() is not implemented")
    }
}

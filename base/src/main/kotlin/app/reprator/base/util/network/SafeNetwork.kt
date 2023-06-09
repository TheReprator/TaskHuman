/*
 * Copyright 2021
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app.reprator.base.util.network

import app.reprator.base.useCases.AppError
import app.reprator.base.useCases.AppResult

suspend fun <T : Any> safeApiCall(
    call: suspend () -> AppResult<T>,
    errorMessage: String? = null
): AppResult<T> {
    return try {
        call()
    } catch (e: Exception) {
        AppError(message = errorMessage ?: e.message)
    }
}

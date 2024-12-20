package com.evg.api.data.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.exception.ApolloNetworkException
import com.evg.api.GetTestsQuery
import com.evg.api.LoginUserMutation
import com.evg.api.PasswordResetMutation
import com.evg.api.RegisterUserMutation
import com.evg.api.domain.repository.ApiRepository
import com.evg.api.domain.utils.CombinedLoginError
import com.evg.api.domain.utils.CombinedPasswordResetError
import com.evg.api.domain.utils.CombinedRegistrationError
import com.evg.api.domain.utils.LoginError
import com.evg.api.domain.utils.NetworkError
import com.evg.api.domain.utils.PasswordResetError
import com.evg.api.domain.utils.RegistrationError
import com.evg.api.domain.utils.ServerResult
import com.evg.api.type.PasswordResetDTO
import com.evg.api.type.UserLoginDTO
import com.evg.api.type.UserRegistrationDTO
import okio.ProtocolException
import java.net.ConnectException
import java.net.SocketTimeoutException

class ApiRepositoryImpl(
    private val apolloClient: ApolloClient,
): ApiRepository {
    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): ServerResult<T, NetworkError> {
        return try {
            ServerResult.Success(apiCall())
        } catch (e: ApolloHttpException) {
            when (e.statusCode) {
                404 -> ServerResult.Error(NetworkError.NOT_FOUND)
                408 -> ServerResult.Error(NetworkError.REQUEST_TIMEOUT)
                429 -> ServerResult.Error(NetworkError.TOO_MANY_REQUESTS)
                in 500..599 -> ServerResult.Error(NetworkError.SERVER_ERROR)
                else -> ServerResult.Error(NetworkError.UNKNOWN) /*throw e*/
            }
        } catch (e: ApolloNetworkException) {
            ServerResult.Error(NetworkError.CONNECT_EXCEPTION)
        } catch (e: SocketTimeoutException) {
            ServerResult.Error(NetworkError.REQUEST_TIMEOUT)
        } catch (e: ConnectException) {
            ServerResult.Error(NetworkError.CONNECT_EXCEPTION)
        } catch (e: ProtocolException) {
            ServerResult.Error(NetworkError.PROTOCOL_EXCEPTION)
        } catch (e: Exception) {
            println(e)  //TODO NoDataException?
            ServerResult.Error(NetworkError.UNKNOWN)
        }
    }

    override suspend fun registrationUser(user: UserRegistrationDTO): ServerResult<Unit, CombinedRegistrationError> { //RegisterUserMutation.RegisterUser
        val response = safeApiCall {
            apolloClient
                .mutation(RegisterUserMutation(data = user))
                .execute()
                .dataOrThrow()
                .registerUser
        }

        return when (response) {
            is ServerResult.Error -> ServerResult.Error(CombinedRegistrationError.Network(response.error))
            is ServerResult.Success -> {
                val resultError = when (response.data.code) {
                    200 -> null
                    409 -> CombinedRegistrationError.Registration(RegistrationError.EMAIL_EXIST)
                    else -> CombinedRegistrationError.Network(NetworkError.UNKNOWN)
                }
                resultError?.let { ServerResult.Error(it) } ?: ServerResult.Success(Unit)
            }
        }
    }

    override suspend fun loginUser(user: UserLoginDTO): ServerResult<String, CombinedLoginError> {
        val response = safeApiCall {
            apolloClient
                .mutation(LoginUserMutation(data = user))
                .execute()
                .dataOrThrow()
                .loginUser
        }

        return when (response) {
            is ServerResult.Error -> ServerResult.Error(CombinedLoginError.Network(response.error))
            is ServerResult.Success -> {
                val resultError = when (response.data.code) {
                    200 -> null
                    409 -> CombinedLoginError.Login(LoginError.WRONG_EMAIL_OR_PASS)
                    else -> CombinedLoginError.Network(NetworkError.UNKNOWN)
                }
                resultError?.let { ServerResult.Error(it) } ?: ServerResult.Success(response.data.token)
            }
        }
    }

    override suspend fun passwordReset(passwordReset: PasswordResetDTO): ServerResult<Unit, CombinedPasswordResetError> {
        val response = safeApiCall {
            apolloClient
                .mutation(PasswordResetMutation(data = passwordReset))
                .execute()
                .dataOrThrow()
                .passwordReset
        }

        return when (response) {
            is ServerResult.Error -> ServerResult.Error(CombinedPasswordResetError.Network(response.error))
            is ServerResult.Success -> {
                val resultError = when (response.data.code) {
                    200 -> null
                    409 -> CombinedPasswordResetError.PasswordReset(PasswordResetError.UNKNOWN_EMAIL)
                    else -> CombinedPasswordResetError.Network(NetworkError.UNKNOWN)
                }
                resultError?.let { ServerResult.Error(it) } ?: ServerResult.Success(Unit)
            }
        }
    }

    override suspend fun getAllTestsByPage(page: Int): ServerResult<GetTestsQuery.GetTests, NetworkError> {
        val response = safeApiCall {
            apolloClient
                .query(GetTestsQuery(page = page))
                .execute()
                .dataOrThrow()
                .getTests
        }
        return response
    }
}
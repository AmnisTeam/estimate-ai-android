package com.evg.api.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.exception.ApolloNetworkException
import com.apollographql.apollo.exception.NoDataException
import com.evg.api.GetTestsQuery
import com.evg.api.LoginUserMutation
import com.evg.api.OnTestProgressSubscription
import com.evg.api.PasswordResetMutation
import com.evg.api.RegisterUserMutation
import com.evg.api.domain.mapper.toOnTestProgressResponse
import com.evg.api.domain.mapper.toTestResponses
import com.evg.api.domain.model.GetTestsResponse
import com.evg.api.domain.model.OnTestProgressResponse
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okio.ProtocolException
import java.net.ConnectException
import java.net.SocketTimeoutException

class ApiRepositoryImpl(
    private val context: Context,
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
        } catch (e: NoDataException) {
            ServerResult.Error(NetworkError.UNKNOWN) //TODO NoDataException?
        } catch (e: Exception) {
            println(e)
            ServerResult.Error(NetworkError.UNKNOWN)
        }
    }

    override suspend fun registrationUser(user: UserRegistrationDTO): ServerResult<Unit, CombinedRegistrationError> { //RegisterUserMutation.RegisterUser
        val response = safeApiCall {
            apolloClient
                .mutation(RegisterUserMutation(data = user))
                .execute()
                .dataOrThrow()
                .registerUserResponse
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
                .loginUserResponse
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
                .passwordResetResponse
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

    override suspend fun getAllTestsByPage(page: Int): ServerResult<GetTestsResponse, NetworkError> {
        val response = safeApiCall {
            apolloClient
                .query(GetTestsQuery(page = page))
                .execute()
                .dataOrThrow()
                .getTestsResponse
                .toTestResponses()
        }
        return response
    }

    override suspend fun onTestProgress(): ServerResult<Flow<OnTestProgressResponse>, NetworkError> {
        return try {
            val response = safeApiCall {
                apolloClient
                    .subscription(OnTestProgressSubscription())
                    .toFlow()
                    .map {
                        it.dataOrThrow()
                            .onTestProgressResponse
                            .toOnTestProgressResponse()
                    }
            }
            response
        } catch (e: NoDataException) {
            ServerResult.Error(NetworkError.UNKNOWN)
        }
    }

    override fun isInternetAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}
package com.evg.api.data.repository

import com.apollographql.apollo.ApolloClient
import com.evg.api.GetTestsQuery
import com.evg.api.LoginUserMutation
import com.evg.api.PasswordResetMutation
import com.evg.api.RegisterUserMutation
import com.evg.api.domain.repository.ApiRepository
import com.evg.api.domain.utils.LoginError
import com.evg.api.domain.utils.PasswordResetError
import com.evg.api.domain.utils.RegistrationError
import com.evg.api.domain.utils.ServerResult
import com.evg.api.type.GetTestsResponse
import com.evg.api.type.PasswordResetDTO
import com.evg.api.type.UserLoginDTO
import com.evg.api.type.UserRegistrationDTO

class ApiRepositoryImpl(
    private val apolloClient: ApolloClient,
): ApiRepository {
    override suspend fun registrationUser(user: UserRegistrationDTO): ServerResult<Unit, RegistrationError> { //RegisterUserMutation.RegisterUser
        return try {
            val registrationResponse = apolloClient
                .mutation(RegisterUserMutation(data = user))
                .execute()
                .data
                ?.registerUser

            if (registrationResponse == null) {
                return ServerResult.Error(RegistrationError.UNKNOWN)
            }
            ServerResult.Success(Unit)
        } catch (e: Exception) {
            ServerResult.Error(RegistrationError.UNKNOWN)
        }
    }

    override suspend fun loginUser(user: UserLoginDTO): ServerResult<String, LoginError> {
        return try {
            val loginResponse = apolloClient
                .mutation(LoginUserMutation(data = user))
                .execute()
                .data
                ?.loginUser

            if (loginResponse == null) {
                return ServerResult.Error(LoginError.UNKNOWN)
            }
            ServerResult.Success(loginResponse.token)
        } catch (e: Exception) {
            ServerResult.Error(LoginError.UNKNOWN)
        }
    }

    override suspend fun passwordReset(passwordReset: PasswordResetDTO): ServerResult<Unit, PasswordResetError> {
        return try {
            val passwordResetResponse = apolloClient
                .mutation(PasswordResetMutation(data = passwordReset))
                .execute()
                .data
                ?.passwordReset

            if (passwordResetResponse == null) {
                return ServerResult.Error(PasswordResetError.UNKNOWN)
            }
            ServerResult.Success(Unit)
        } catch (e: Exception) {
            ServerResult.Error(PasswordResetError.UNKNOWN)
        }
    }

    override suspend fun getAllTestsByPage(page: Int): ServerResult<GetTestsQuery.GetTests, PasswordResetError> {
        return try {
            val testsResponse = apolloClient
                .query(GetTestsQuery(page = page))
                .execute()
                .data
                ?.getTests

            if (testsResponse == null) {
                return ServerResult.Error(PasswordResetError.UNKNOWN)
            }
            ServerResult.Success(testsResponse)
        } catch (e: Exception) {
            ServerResult.Error(PasswordResetError.UNKNOWN)
        }
    }
}
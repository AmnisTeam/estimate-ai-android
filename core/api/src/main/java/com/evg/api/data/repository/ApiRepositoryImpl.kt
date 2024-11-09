package com.evg.api.data.repository

import com.apollographql.apollo.ApolloClient
import com.evg.api.RegisterUserMutation
import com.evg.api.domain.repository.ApiRepository
import com.evg.api.domain.utils.RegistrationError
import com.evg.api.domain.utils.ServerResult
import com.evg.api.type.UserRegistration

class ApiRepositoryImpl(
    private val apolloClient: ApolloClient,
): ApiRepository {
    override suspend fun registrationUser(user: UserRegistration): ServerResult<RegisterUserMutation.RegisterUser, RegistrationError> {
        return try {
            val userResponse = apolloClient
                .mutation(RegisterUserMutation(data = user))
                .execute()
                .data
                ?.registerUser

            return ServerResult.Success(userResponse!!)
        } catch (e: Exception) {
            ServerResult.Error(RegistrationError.UNKNOWN)
        }
    }
}
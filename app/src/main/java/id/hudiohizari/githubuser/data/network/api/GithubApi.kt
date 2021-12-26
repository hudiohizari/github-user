package id.hudiohizari.githubuser.data.network.api

import id.hudiohizari.githubuser.data.model.user.detail.DetailResponse
import id.hudiohizari.githubuser.data.model.user.repo.RepoResponse
import id.hudiohizari.githubuser.data.model.user.search.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @GET("search/users")
    suspend fun getSearchUsers(
        @Query("page") page: Int?,
        @Query("per_page") limit: Int?,
        @Query("q") query: String?
    ): Response<SearchResponse>

    @GET("users/{username}")
    suspend fun getUser(@Path("username") username: String?): Response<DetailResponse>

    @GET("users/{username}/repos")
    suspend fun getUserRepo(
        @Path("username") username: String?,
        @Query("sort") sort: String?
    ): Response<RepoResponse>

}
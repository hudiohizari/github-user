package id.hudiohizari.githubuser.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import id.hudiohizari.githubuser.data.model.user.search.SearchResponse
import id.hudiohizari.githubuser.data.network.SafeApiRequest
import id.hudiohizari.githubuser.data.network.api.GithubApi
import javax.inject.Inject

class GithubRepository @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val githubApi: GithubApi
): SafeApiRequest(context) {

    companion object {
        const val DEFAULT_LIMIT = 30
    }

    suspend fun getUsers(
        query: String?,
        page: Int,
        limit: Int = DEFAULT_LIMIT
    ): SearchResponse? {
        return apiRequest { githubApi.getSearchUsers(page, limit, query) }
    }

}
package id.hudiohizari.githubuser.data.repository

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import id.hudiohizari.githubuser.data.db.UserDetailDao
import id.hudiohizari.githubuser.data.model.user.detail.DetailResponse
import id.hudiohizari.githubuser.data.network.SafeApiRequest
import id.hudiohizari.githubuser.data.network.api.GithubApi
import javax.inject.Inject

class GithubRepository @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val githubApi: GithubApi,
    private val userDetailDao: UserDetailDao
): SafeApiRequest(context) {

    companion object {
        const val DEFAULT_LIMIT = 20
    }

    suspend fun getUsers(
        query: String?,
        page: Int,
        limit: Int = DEFAULT_LIMIT
    ) = apiRequest { githubApi.getSearchUsers(page, limit, query) }

    suspend fun getUser(username: String?) = apiRequest {
        githubApi.getUser(username)
    }

    suspend fun getUserRepo(username: String) = apiRequest {
        githubApi.getUserRepo(username)
    }

    suspend fun getLocalUser(id: Int?): DetailResponse? {
        return userDetailDao.getUserDetail(id)
    }

    suspend fun insertLocalUser(detailResponse: DetailResponse?) {
        return userDetailDao.insert(detailResponse)
    }

    suspend fun deleteLocalUser() {
        return userDetailDao.deleteAll()
    }

}
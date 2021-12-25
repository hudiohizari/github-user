package id.hudiohizari.githubuser.ui.user.search

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hudiohizari.githubuser.data.model.user.detail.DetailResponse
import id.hudiohizari.githubuser.data.model.user.search.SearchResponse
import id.hudiohizari.githubuser.data.network.ApiException
import id.hudiohizari.githubuser.data.network.ConnectionException
import id.hudiohizari.githubuser.data.repository.GithubRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSearchViewModel @Inject constructor(
    private val repository: GithubRepository
): ViewModel() {
    val search = MutableLiveData<String>("")
    val response = MutableLiveData<SearchResponse>()

    fun loadUser(page: Int) {
        if (page == 1) listener?.showUserLoading(true)
        viewModelScope.launch {
            try {
                val response = repository.getUsers(search.value, page)
                response?.let { this@UserSearchViewModel.response.postValue(it) }
            } catch (e: ApiException) {
                listener?.showSnackbar(e.message)
            } catch (e: ConnectionException) {
                listener?.showSnackbarLong(e.message)
            } finally {
                listener?.showUserLoading(false)
            }
        }
    }

    suspend fun requestLocalUser(id: Int?): DetailResponse? {
        return repository.getLocalUser(id)
    }

    suspend fun requestUser(username: String?): DetailResponse? {
        return repository.getUser(username)
    }

    suspend fun insertLocalUser(user: DetailResponse?) {
        repository.insertLocalUser(user)
    }

    @Suppress("unused")
    fun View.onClickedReload() {
        loadUser(1)
    }

    private var listener: Listener? = null

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener {
        fun showSnackbar(message: String?)
        fun showSnackbarLong(message: String?)
        fun showUserLoading(isLoading: Boolean)
    }

}

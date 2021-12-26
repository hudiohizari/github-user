package id.hudiohizari.githubuser.ui.user.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hudiohizari.githubuser.data.model.user.detail.DetailResponse
import id.hudiohizari.githubuser.data.model.user.repo.RepoResponse
import id.hudiohizari.githubuser.data.network.ApiException
import id.hudiohizari.githubuser.data.network.ConnectionException
import id.hudiohizari.githubuser.data.repository.GithubRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val repository: GithubRepository
): ViewModel() {

    val userDetail = MutableLiveData<DetailResponse>()
    val response = MutableLiveData<RepoResponse>()

    fun loadUserRepo() {
        viewModelScope.launch {
            listener?.showRepoLoading(true)
            try {
                val response = repository.getUserRepo(userDetail.value?.login)
                response?.let { this@UserDetailViewModel.response.postValue(it) }
            } catch (e: ApiException) {
                listener?.showSnackbar(e.message)
            } catch (e: ConnectionException) {
                listener?.showSnackbarLong(e.message)
            } finally {
                listener?.showRepoLoading(false)
            }
        }
    }

    private var listener: Listener? = null

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    interface Listener {
        fun showSnackbar(message: String?)
        fun showSnackbarLong(message: String?)
        fun showRepoLoading(isLoading: Boolean)
    }

}

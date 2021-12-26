package id.hudiohizari.githubuser.ui.user.detail

import androidx.lifecycle.MutableLiveData
import id.hudiohizari.githubuser.data.model.user.detail.DetailResponse
import id.hudiohizari.githubuser.data.network.ApiException
import id.hudiohizari.githubuser.data.network.ConnectionException
import id.hudiohizari.githubuser.data.repository.GithubRepository
import id.hudiohizari.githubuser.util.MainCoroutineRule
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserDetailViewModelTest : TestCase() {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var repository: GithubRepository
    @Mock
    private lateinit var listener: UserDetailViewModel.Listener
    @Mock
    private lateinit var throwableApiException: ApiException
    @Mock
    private lateinit var throwableConnectionException: ConnectionException
    @Mock
    private lateinit var detailResponse: MutableLiveData<DetailResponse>

    private lateinit var viewModel: UserDetailViewModel

    @Before
    public override fun setUp() {
        viewModel = UserDetailViewModel(repository)
        viewModel.setListener(listener)
    }

    @Test
    fun checkLoadUserRepo_SuccessResponse() = runBlocking {
        val inOrder = Mockito.inOrder(listener)

        viewModel.loadUserRepo()
        inOrder.verify(listener).showRepoLoading(true)
        inOrder.verify(listener, never()).showSnackbar(throwableApiException.message)
        inOrder.verify(listener, never()).showSnackbarLong(throwableConnectionException.message)
        inOrder.verify(listener).showRepoLoading(false)
    }

    @Test
    fun checkLoadUserRepo_ThrowApiException() = runBlocking {
        val inOrder = Mockito.inOrder(listener)

        Mockito.`when`(repository.getUserRepo(detailResponse.value?.login))
            .thenAnswer { throw throwableApiException }

        viewModel.loadUserRepo()
        inOrder.verify(listener).showRepoLoading(true)
        inOrder.verify(listener).showSnackbar(throwableApiException.message)
        inOrder.verify(listener, never()).showSnackbarLong(throwableConnectionException.message)
        inOrder.verify(listener).showRepoLoading(false)
    }

    @Test
    fun checkLoadUserRepo_ThrowConnectionException() = runBlocking {
        val inOrder = Mockito.inOrder(listener)

        Mockito.`when`(repository.getUserRepo(detailResponse.value?.login))
            .thenAnswer { throw throwableConnectionException }

        viewModel.loadUserRepo()
        inOrder.verify(listener).showRepoLoading(true)
        inOrder.verify(listener, never()).showSnackbar(throwableApiException.message)
        inOrder.verify(listener).showSnackbarLong(throwableConnectionException.message)
        inOrder.verify(listener).showRepoLoading(false)
    }

}
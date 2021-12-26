package id.hudiohizari.githubuser.ui.user.search

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
class UserSearchViewModelTest : TestCase() {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var repository: GithubRepository
    @Mock
    private lateinit var listener: UserSearchViewModel.Listener
    @Mock
    private lateinit var throwableApiException: ApiException
    @Mock
    private lateinit var throwableConnectionException: ConnectionException

    private lateinit var viewModel: UserSearchViewModel

    @Before
    public override fun setUp() {
        viewModel = UserSearchViewModel(repository)
        viewModel.setListener(listener)
    }

    @Test
    fun checkLoadPage_PageOne_SuccessResponse() = runBlocking {
        val inOrder = Mockito.inOrder(listener)

        viewModel.loadUser(1)
        inOrder.verify(listener).showUserLoading(true)
        inOrder.verify(listener, never()).showSnackbar(throwableApiException.message)
        inOrder.verify(listener, never()).showSnackbarLong(throwableConnectionException.message)
        inOrder.verify(listener).showUserLoading(false)
    }

    @Test
    fun checkLoadPage_PageOne_ThrowApiException() = runBlocking {
        val search = ""
        val page = 1
        val inOrder = Mockito.inOrder(listener)

        Mockito.`when`(repository.getUsers(search, page))
            .thenAnswer { throw throwableApiException }

        viewModel.loadUser(page)
        inOrder.verify(listener).showUserLoading(true)
        inOrder.verify(listener).showSnackbar(throwableApiException.message)
        inOrder.verify(listener, never()).showSnackbarLong(throwableConnectionException.message)
        inOrder.verify(listener).showUserLoading(false)
    }

    @Test
    fun checkLoadPage_PageOne_ThrowConnectionException() = runBlocking {
        val search = ""
        val page = 1
        val inOrder = Mockito.inOrder(listener)

        Mockito.`when`(repository.getUsers(search, page))
            .thenAnswer { throw throwableConnectionException }

        viewModel.loadUser(page)
        inOrder.verify(listener).showUserLoading(true)
        inOrder.verify(listener, never()).showSnackbar(throwableApiException.message)
        inOrder.verify(listener).showSnackbarLong(throwableConnectionException.message)
        inOrder.verify(listener).showUserLoading(false)
    }

    @Test
    fun checkLoadPage_PageTwoAndAbove_SuccessResponse() = runBlocking {
        val inOrder = Mockito.inOrder(listener)

        viewModel.loadUser(2)
        inOrder.verify(listener, never()).showUserLoading(true)
        inOrder.verify(listener, never()).showSnackbar(throwableApiException.message)
        inOrder.verify(listener, never()).showSnackbarLong(throwableConnectionException.message)
        inOrder.verify(listener, never()).showUserLoading(false)
    }

    @Test
    fun checkLoadPage_PageTwoAndAbove_ThrowApiException() = runBlocking {
        val search = ""
        val page = 2
        val inOrder = Mockito.inOrder(listener)

        Mockito.`when`(repository.getUsers(search, page))
            .thenAnswer { throw throwableApiException }

        viewModel.loadUser(page)
        inOrder.verify(listener, never()).showUserLoading(true)
        inOrder.verify(listener).showSnackbar(throwableApiException.message)
        inOrder.verify(listener, never()).showSnackbarLong(throwableConnectionException.message)
        inOrder.verify(listener, never()).showUserLoading(false)
    }

    @Test
    fun checkLoadPage_PageTwoAndAbove_ThrowConnectionException() = runBlocking {
        val search = ""
        val page = 2
        val inOrder = Mockito.inOrder(listener)

        Mockito.`when`(repository.getUsers(search, page))
            .thenAnswer { throw throwableConnectionException }

        viewModel.loadUser(page)
        inOrder.verify(listener, never()).showUserLoading(true)
        inOrder.verify(listener, never()).showSnackbar(throwableApiException.message)
        inOrder.verify(listener).showSnackbarLong(throwableConnectionException.message)
        inOrder.verify(listener, never()).showUserLoading(false)
    }

}
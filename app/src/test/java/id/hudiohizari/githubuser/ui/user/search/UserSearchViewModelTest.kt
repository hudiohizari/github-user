package id.hudiohizari.githubuser.ui.user.search

import id.hudiohizari.githubuser.data.network.ApiException
import id.hudiohizari.githubuser.data.network.ConnectionException
import id.hudiohizari.githubuser.data.repository.GithubRepository
import id.hudiohizari.githubuser.util.MainCoroutineRule
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
class UserSearchViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var repository: GithubRepository
    @Mock
    private lateinit var listener: UserSearchViewModel.Listener

    private lateinit var viewModel: UserSearchViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        viewModel = UserSearchViewModel(repository)
        viewModel.setListener(listener)
    }

    @Test
    fun checkLoadPage_PageOne_SuccessResponse() = runBlocking {
        val inOrder = Mockito.inOrder(listener)

        viewModel.loadUser(1)
        inOrder.verify(listener).showUserLoading(true)
        inOrder.verify(listener).showUserLoading(false)
    }

    @Test
    fun checkLoadPage_PageOne_ThrowApiException() = runBlocking {
        val search = ""
        val page = 1
        val message = "Search cant be empty"

        val inOrder = Mockito.inOrder(listener)
        val throwable = ApiException(400, message)

        Mockito.`when`(repository.getUsers(search, page))
            .thenAnswer { throw throwable }

        viewModel.loadUser(page)
        inOrder.verify(listener).showUserLoading(true)
        inOrder.verify(listener).showSnackbar(throwable.message)
        inOrder.verify(listener).showUserLoading(false)
    }

    @Test
    fun checkLoadPage_PageOne_ThrowConnectionException() = runBlocking {
        val search = ""
        val page = 1
        val message = "Device not connected to the internet"

        val inOrder = Mockito.inOrder(listener)
        val throwable = ConnectionException(message)

        Mockito.`when`(repository.getUsers(search, page))
            .thenAnswer { throw throwable }

        viewModel.loadUser(page)
        inOrder.verify(listener).showUserLoading(true)
        inOrder.verify(listener).showSnackbarLong(throwable.message)
        inOrder.verify(listener).showUserLoading(false)
    }

    @Test
    fun checkLoadPage_PageTwoAndAbove_SuccessResponse() = runBlocking {
        val inOrder = Mockito.inOrder(listener)

        viewModel.loadUser(2)
        inOrder.verify(listener, never()).showUserLoading(true)
        inOrder.verify(listener, never()).showUserLoading(false)
    }

    @Test
    fun checkLoadPage_PageTwoAndAbove_ThrowApiException() = runBlocking {
        val search = ""
        val page = 2
        val message = "Search cant be empty"

        val inOrder = Mockito.inOrder(listener)
        val throwable = ApiException(400, message)

        Mockito.`when`(repository.getUsers(search, page))
            .thenAnswer { throw throwable }

        viewModel.loadUser(page)
        inOrder.verify(listener, never()).showUserLoading(true)
        inOrder.verify(listener).showSnackbar(throwable.message)
        inOrder.verify(listener, never()).showUserLoading(false)
    }

    @Test
    fun checkLoadPage_PageTwoAndAbove_ThrowConnectionException() = runBlocking {
        val search = ""
        val page = 2
        val message = "Device not connected to the internet"

        val inOrder = Mockito.inOrder(listener)
        val throwable = ConnectionException(message)

        Mockito.`when`(repository.getUsers(search, page))
            .thenAnswer { throw throwable }

        viewModel.loadUser(page)
        inOrder.verify(listener, never()).showUserLoading(true)
        inOrder.verify(listener).showSnackbarLong(throwable.message)
        inOrder.verify(listener, never()).showUserLoading(false)
    }

}
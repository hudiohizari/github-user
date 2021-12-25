package id.hudiohizari.githubuser.ui.user.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import id.hudiohizari.githubuser.R
import id.hudiohizari.githubuser.data.adapter.base.UnspecifiedTypeItem
import id.hudiohizari.githubuser.data.adapter.base.performUpdates
import id.hudiohizari.githubuser.data.adapter.user.UserListItem
import id.hudiohizari.githubuser.data.adapter.user.UserLoadMoreListItem
import id.hudiohizari.githubuser.data.adapter.base.DefaultLoadingListItem
import id.hudiohizari.githubuser.data.model.user.search.Item
import id.hudiohizari.githubuser.databinding.FragmentUserSearchBinding
import id.hudiohizari.githubuser.util.extention.observeDebounce
import id.hudiohizari.githubuser.util.extention.snackbar
import id.hudiohizari.githubuser.util.extention.snackbarLong
import androidx.recyclerview.widget.DividerItemDecoration
import id.hudiohizari.githubuser.data.adapter.base.DefaultEmptyListItem
import id.hudiohizari.githubuser.data.model.user.detail.DetailResponse
import id.hudiohizari.githubuser.data.model.user.search.SearchResponse

@AndroidEntryPoint
class UserSearchFragment : Fragment(), UserSearchViewModel.Listener {

    private lateinit var binding: FragmentUserSearchBinding
    private val viewModel: UserSearchViewModel by viewModels()

    private val userList: MutableList<Item> = mutableListOf()
    private var page = 1

    private lateinit var itemDecoration: DividerItemDecoration

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_user_search,
            container,
            false
        )
        viewModel.setListener(this)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemDecoration = DividerItemDecoration(
            context,
            DividerItemDecoration.VERTICAL
        )

        initObserver()
    }

    private fun initObserver() {
        viewModel.apply {
            search.observeDebounce(viewLifecycleOwner, {
                binding.rvUser.removeItemDecoration(itemDecoration)

                userList.clear()
                val items: MutableList<UnspecifiedTypeItem> = mutableListOf()
                items.add(DefaultEmptyListItem(getString(R.string.insertSearchText)))
                getUserAdapter().performUpdates(items)

                if (it != null && it.isNotEmpty()) loadUser(page)
            })
            response.observe(viewLifecycleOwner, {
                processUserListData(it)
            })
        }
    }

    private fun processUserListData(searchResponse: SearchResponse?) {
        searchResponse?.items?.let { users ->
            val items: MutableList<UnspecifiedTypeItem> = mutableListOf()
            userList.addAll(users)
            userList.forEach { user ->
                items.add(UserListItem(user, object : UserListItem.EventListener {
                    override fun onClick(item: Item) {
                        val action = UserSearchFragmentDirections
                            .actionSearchUserFragmentToUserDetailFragment(item.id ?: -1, item.login)
                        binding.root.findNavController().navigate(action)
                    }

                    override suspend fun requestLocalUser(id: Int?): DetailResponse? {
                        return viewModel.requestLocalUser(id)
                    }

                    override suspend fun requestUser(username: String?): DetailResponse? {
                        return viewModel.requestUser(username)
                    }

                    override suspend fun insertLocalUser(user: DetailResponse?) {
                        viewModel.insertLocalUser(user)
                    }
                }))
            }

            if (users.size > 0) {
                page++
                items.add(UserLoadMoreListItem(object : UserLoadMoreListItem.EventListener {
                    override fun onLoadMore(isLoadMore: Boolean) {
                        if (isLoadMore) { viewModel.loadUser(page) }
                    }
                }))
            }

            if (userList.isEmpty()) {
                items.add(DefaultEmptyListItem(getString(R.string.thereIsNoMatches)))
            } else {
                binding.rvUser.addItemDecoration(itemDecoration)
            }

            getUserAdapter().performUpdates(items)
        }
    }

    private fun getUserAdapter(): FastItemAdapter<UnspecifiedTypeItem> {
        if (binding.adapter == null) {
            binding.adapter = FastItemAdapter()
        }
        return binding.adapter as FastItemAdapter
    }

    override fun showSnackbar(message: String?) {
        binding.root.snackbar(message)
    }

    override fun showSnackbarLong(message: String?) {
        binding.root.snackbarLong(message)
    }

    override fun showUserLoading(isLoading: Boolean) {
        if (isLoading) {
            val items: MutableList<UnspecifiedTypeItem> = mutableListOf()
            items.add(DefaultLoadingListItem())
            items.add(DefaultLoadingListItem())
            items.add(DefaultLoadingListItem())
            getUserAdapter().performUpdates(items)
        }
    }

}
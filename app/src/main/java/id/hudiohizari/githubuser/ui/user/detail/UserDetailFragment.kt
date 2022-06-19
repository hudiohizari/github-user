package id.hudiohizari.githubuser.ui.user.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import dagger.hilt.android.AndroidEntryPoint
import id.hudiohizari.githubuser.R
import id.hudiohizari.githubuser.data.adapter.base.DefaultEmptyListItem
import id.hudiohizari.githubuser.data.adapter.base.DefaultReloadListItem
import id.hudiohizari.githubuser.data.adapter.base.UnspecifiedTypeItem
import id.hudiohizari.githubuser.data.adapter.base.performUpdates
import id.hudiohizari.githubuser.data.adapter.repo.RepoListItem
import id.hudiohizari.githubuser.data.adapter.repo.RepoLoadingListItem
import id.hudiohizari.githubuser.data.model.user.repo.RepoResponse
import id.hudiohizari.githubuser.databinding.FragmentUserDetailBinding
import id.hudiohizari.githubuser.util.extention.snackbar
import id.hudiohizari.githubuser.util.extention.snackbarLong

@AndroidEntryPoint
class UserDetailFragment : Fragment(), UserDetailViewModel.Listener {

    private lateinit var binding: FragmentUserDetailBinding
    private val viewModel: UserDetailViewModel by viewModels()
    private val args: UserDetailFragmentArgs by navArgs()

    private var v: View? = null

    private lateinit var itemDecoration: DividerItemDecoration

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (view == null) {
            binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_user_detail,
                container,
                false
            )
            viewModel.setListener(this)
            binding.viewModel = viewModel
            v = binding.root
        }

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initArguments()
        initObserver()
    }

    private fun initArguments() {
        viewModel.userDetail.value = args.detailResponse
    }

    private fun initObserver() {
        viewModel.apply {
            userDetail.observe(viewLifecycleOwner) {
                it?.let { loadUserRepo() }
            }
            response.observe(viewLifecycleOwner) {
                it?.let { processRepoListData(it) }
            }
        }
    }

    private fun processRepoListData(repoResponse: RepoResponse) {
        val items: MutableList<UnspecifiedTypeItem> = mutableListOf()
        repoResponse.forEach { repo ->
            items.add(RepoListItem(repo))
        }

        if (repoResponse.isEmpty()) {
            binding.rvRepo.removeItemDecoration(getItemDecoration())
            items.add(DefaultEmptyListItem(getString(R.string.repoEmpty)))
        } else {
            binding.rvRepo.addItemDecoration(getItemDecoration())
        }

        getUserAdapter().performUpdates(items)
    }

    private fun getItemDecoration(): DividerItemDecoration {
        if (!::itemDecoration.isInitialized) {
            itemDecoration = DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        }
        return itemDecoration
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
        val items: MutableList<UnspecifiedTypeItem> = mutableListOf()
        items.add(DefaultReloadListItem(object : DefaultReloadListItem.Listener {
            override fun reload() { viewModel.loadUserRepo() }
        }))
        getUserAdapter().performUpdates(items)
    }

    override fun showRepoLoading(isLoading: Boolean) {
        if (isLoading) {
            val items: MutableList<UnspecifiedTypeItem> = mutableListOf()
            items.add(RepoLoadingListItem())
            items.add(RepoLoadingListItem())
            items.add(RepoLoadingListItem())
            getUserAdapter().performUpdates(items)
        }
    }

}
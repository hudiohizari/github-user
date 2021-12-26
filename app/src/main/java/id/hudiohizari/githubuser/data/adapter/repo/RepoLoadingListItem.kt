package id.hudiohizari.githubuser.data.adapter.repo

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import id.hudiohizari.githubuser.data.adapter.base.DiffableListItemType
import id.hudiohizari.githubuser.databinding.ListItemLoadingRepoBinding
import id.hudiohizari.githubuser.util.extention.loadingAnimation

class RepoLoadingListItem :
    AbstractBindingItem<ListItemLoadingRepoBinding>(),
    DiffableListItemType {

    override fun itemIdentifier(): Any = this.hashCode()

    override fun comparableContents(): List<Any> = listOf()

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ListItemLoadingRepoBinding {
        return ListItemLoadingRepoBinding.inflate(inflater, parent, false)
    }

    override val type: Int get() = hashCode()

    override fun bindView(binding: ListItemLoadingRepoBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)
        binding.loading.loadingAnimation()
    }
}
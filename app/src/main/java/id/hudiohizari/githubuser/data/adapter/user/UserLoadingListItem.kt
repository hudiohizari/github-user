package id.hudiohizari.githubuser.data.adapter.user

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import id.hudiohizari.githubuser.data.adapter.base.DiffableListItemType
import id.hudiohizari.githubuser.databinding.ListItemLoadingUserBinding
import id.hudiohizari.githubuser.util.extention.loadingAnimation

class UserLoadingListItem :
    AbstractBindingItem<ListItemLoadingUserBinding>(),
    DiffableListItemType {

    override fun itemIdentifier(): Any = this.hashCode()

    override fun comparableContents(): List<Any> = listOf()

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ListItemLoadingUserBinding {
        return ListItemLoadingUserBinding.inflate(inflater, parent, false)
    }

    override val type: Int get() = hashCode()

    override fun bindView(binding: ListItemLoadingUserBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)
        binding.loading.loadingAnimation()
    }
}
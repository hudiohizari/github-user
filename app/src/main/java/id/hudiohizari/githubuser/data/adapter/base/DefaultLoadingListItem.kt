package id.hudiohizari.githubuser.data.adapter.base

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import id.hudiohizari.githubuser.databinding.ListItemLoadingDefaultBinding
import id.hudiohizari.githubuser.util.extention.loadingAnimation

class DefaultLoadingListItem :
    AbstractBindingItem<ListItemLoadingDefaultBinding>(),
    DiffableListItemType {

    override fun itemIdentifier(): Any = this.hashCode()

    override fun comparableContents(): List<Any> = listOf()

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ListItemLoadingDefaultBinding {
        return ListItemLoadingDefaultBinding.inflate(inflater, parent, false)
    }

    override val type: Int get() = hashCode()

    override fun bindView(binding: ListItemLoadingDefaultBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)
        binding.content.loadingAnimation()
    }
}
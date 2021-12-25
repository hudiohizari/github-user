package id.hudiohizari.githubuser.data.adapter.user

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import id.hudiohizari.githubuser.data.adapter.base.DiffableListItemType
import id.hudiohizari.githubuser.databinding.ListItemLoadMoreBinding

class UserLoadMoreListItem(
    val listener: EventListener
) : AbstractBindingItem<ListItemLoadMoreBinding>(),
    DiffableListItemType {

    override fun itemIdentifier(): Any = this.hashCode()

    override fun comparableContents(): List<Any> = listOf()

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ListItemLoadMoreBinding {
        return ListItemLoadMoreBinding.inflate(inflater, parent, false)
    }

    override val type: Int get() = hashCode()

    override fun bindView(binding: ListItemLoadMoreBinding, payloads: List<Any>) {
        super.bindView(binding, payloads)
        listener.onLoadMore(true)
    }

    override fun unbindView(binding: ListItemLoadMoreBinding) {
        super.unbindView(binding)
        listener.onLoadMore(false)
    }

    interface EventListener {
        fun onLoadMore(isLoadMore: Boolean)
    }
}
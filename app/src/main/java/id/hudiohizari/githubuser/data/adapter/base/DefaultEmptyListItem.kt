package id.hudiohizari.githubuser.data.adapter.base

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import id.hudiohizari.githubuser.databinding.ListItemEmptyDefaultBinding

class DefaultEmptyListItem(
    private val text: String
) : AbstractBindingItem<ListItemEmptyDefaultBinding>(), DiffableListItemType {

    override fun itemIdentifier(): Any = this.hashCode()

    override fun comparableContents(): List<Any> = listOf()

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): ListItemEmptyDefaultBinding {
        return ListItemEmptyDefaultBinding.inflate(inflater, parent, false)
    }

    override val type: Int get() = hashCode()

    override fun bindView(binding: ListItemEmptyDefaultBinding, payloads: List<Any>) {
        binding.text = text
    }
}
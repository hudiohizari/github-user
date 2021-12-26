package id.hudiohizari.githubuser.data.adapter.repo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import id.hudiohizari.githubuser.data.adapter.base.DiffableListItemType
import id.hudiohizari.githubuser.data.model.user.repo.RepoResponseItem
import id.hudiohizari.githubuser.databinding.ItemRepoBinding

class RepoListItem(
    private val model: RepoResponseItem
) : AbstractBindingItem<ItemRepoBinding>(), DiffableListItemType {

    override fun itemIdentifier(): Any? = model.id

    override fun comparableContents(): List<Any?> = listOf(model.id)

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemRepoBinding {
        return ItemRepoBinding.inflate(inflater, parent, false)
    }

    override val type: Int get() = hashCode()

    override fun bindView(binding: ItemRepoBinding, payloads: List<Any>) {
        binding.model = model
    }
}
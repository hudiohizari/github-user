package id.hudiohizari.githubuser.data.adapter.user

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import id.hudiohizari.githubuser.data.adapter.base.DiffableListItemType
import id.hudiohizari.githubuser.data.model.user.detail.DetailResponse
import id.hudiohizari.githubuser.data.model.user.search.Item
import id.hudiohizari.githubuser.databinding.ItemUserBinding
import id.hudiohizari.githubuser.util.extention.loadingAnimation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserListItem(
    private val model: Item,
    private val listener: Listener
) : AbstractBindingItem<ItemUserBinding>(), DiffableListItemType {

    private var uiModel: DetailResponse? = null

    override fun itemIdentifier(): Any? = model.id

    override fun comparableContents(): List<Any?> = listOf(model.id, model.login, uiModel?.id)

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemUserBinding {
        return ItemUserBinding.inflate(inflater, parent, false)
    }

    override val type: Int get() = hashCode()

    override fun bindView(binding: ItemUserBinding, payloads: List<Any>) {
        binding.onClick = null
        binding.onClickReload = View.OnClickListener { loadData(binding) }
        loadData(binding)
    }

    private fun loadData(binding: ItemUserBinding) {
        CoroutineScope(Dispatchers.IO).launch {
            uiModel = listener.requestLocalUser(model.id)
            if (uiModel == null) {
                binding.isFailed = false
                binding.isLoading = true
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.loading.loading.loadingAnimation()
                }, 1)
                try {
                    val user = listener.requestUser(model.login)
                    user?.let {
                        listener.insertLocalUser(it)
                        uiModel = it
                    }
                } catch (e: Exception) {
                    Log.e("UserListItem", "${e.message}")
                    binding.isFailed = true
                } finally {
                    binding.isLoading = false
                }
            }
            binding.model = uiModel
            binding.onClick = View.OnClickListener { listener.onClick(uiModel) }
        }
    }

    interface Listener {
        fun onClick(item: DetailResponse?)
        suspend fun requestLocalUser(id: Int?): DetailResponse?
        suspend fun requestUser(username: String?): DetailResponse?
        suspend fun insertLocalUser(user: DetailResponse?)
    }
}
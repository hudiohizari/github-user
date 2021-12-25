package id.hudiohizari.githubuser.data.adapter.base

import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil

typealias UnspecifiedTypeItem = IItem<*>

operator fun <Item : UnspecifiedTypeItem> FastItemAdapter<Item>.plusAssign(item: Item) {
    add(item)
    notifyAdapterDataSetChanged()
}

operator fun <Item : UnspecifiedTypeItem> FastItemAdapter<Item>.plusAssign(items: List<Item>) {
    items.forEach { add(it) }
    notifyAdapterDataSetChanged()
}

fun <Item : UnspecifiedTypeItem> FastItemAdapter<Item>.performUpdates(newItems: List<Item>) {
    val diffResult = FastAdapterDiffUtil.calculateDiff(this.itemAdapter, newItems, DiffableCallback())
    FastAdapterDiffUtil[this.itemAdapter] = diffResult
}
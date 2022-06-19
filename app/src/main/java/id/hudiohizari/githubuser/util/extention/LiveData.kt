package id.hudiohizari.githubuser.util.extention

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun <T> LiveData<T>.observeDebounce(
    owner: LifecycleOwner,
    observer: Observer<T>,
    delay: Long = 500
) {
    var value: T
    observe(owner) { t ->
        value = t
        CoroutineScope(Dispatchers.Main).launch {
            delay(delay)
            if (value != t) return@launch
            observer.onChanged(t)
        }
    }
}
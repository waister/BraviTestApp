package br.com.waister.bravitestapp.features.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.waister.bravitestapp.R
import br.com.waister.bravitestapp.data.local.ActivityItem
import br.com.waister.bravitestapp.databinding.ItemHistoryBinding
import br.com.waister.bravitestapp.utils.STATUS_ABORTED
import br.com.waister.bravitestapp.utils.STATUS_FINISHED
import br.com.waister.bravitestapp.utils.hide
import java.util.concurrent.TimeUnit

class HistoryAdapter : ListAdapter<ActivityItem, HistoryAdapter.ViewHolder>(HistoryCallback()) {

    inner class ViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context

        fun bind(item: ActivityItem) = with(binding) {
            key.text = context.getString(R.string.history_key, item.activityKey)
            title.text = item.activityTitle

            if (item.endMillis > 0)
                duration.text = formatTime(item.endMillis - item.startMillis)
            else
                duration.hide()

            status.run {
                text = context.getString(
                    when (item.status) {
                        STATUS_ABORTED -> R.string.status_aborted
                        STATUS_FINISHED -> R.string.status_finished
                        else -> R.string.status_started
                    }
                )
                background = ContextCompat.getDrawable(
                    context, when (item.status) {
                        STATUS_ABORTED -> R.color.status_aborted
                        STATUS_FINISHED -> R.color.status_finished
                        else -> R.color.status_started
                    }
                )
            }
        }

        private fun formatTime(duration: Long): String {
            context.resources.run {
                val hours: Int = TimeUnit.MILLISECONDS.toHours(duration).toInt()
                if (hours > 0)
                    return getQuantityString(R.plurals.time_in_hours, hours, hours)

                val minutes: Int = TimeUnit.MILLISECONDS.toMinutes(duration).toInt()
                if (minutes > 0)
                    return getQuantityString(R.plurals.time_in_minutes, minutes, minutes)

                val seconds: Int = TimeUnit.MILLISECONDS.toSeconds(duration).toInt()
                if (seconds > 0)
                    return getQuantityString(R.plurals.time_in_seconds, seconds, seconds)
            }

            return ""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))
}

class HistoryCallback : DiffUtil.ItemCallback<ActivityItem>() {
    override fun areItemsTheSame(oldItem: ActivityItem, newItem: ActivityItem): Boolean {
        return oldItem.activityKey == newItem.activityKey
    }

    override fun areContentsTheSame(oldItem: ActivityItem, newItem: ActivityItem): Boolean {
        return oldItem.activityTitle == newItem.activityTitle
    }
}

package cn.zhsa.module_collect.adapter

import android.graphics.Color
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.zhsa.module_collect.databinding.ItemCollectBinding
import cn.zhsa.module_collect.model.CollectBean
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
const val NORMAL = 0
const val PAUSE = 1
const val STOP = 2
@BindingAdapter("transferTime")
fun time(textView: TextView, time: Long) {
    var sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    if (time != null) {
        textView.text = sdf.format(time)
    }
}

@BindingAdapter("imageUrl")
fun bindImageUrl(imageView: ImageView, url: String) {
    if (!TextUtils.isEmpty(url)) {
        Glide.with(imageView.context).load(url).into(imageView)
    }

}
@BindingAdapter("status")
fun bindStatus(textView: TextView,type:Int){
    when(type){
        NORMAL->{
            textView.text = "正常经营"
            textView.setTextColor(Color.parseColor("#57C8A9"))
            textView.setBackgroundColor(Color.parseColor("#EDFAF0"))
        }
        PAUSE->{
            textView.text = "暂停经营"
            textView.setTextColor(Color.parseColor("#FE9E54"))
            textView.setBackgroundColor(Color.parseColor("#FDF1E3"))
        }
        STOP->{
            textView.text = "停止经营"
            textView.setTextColor(Color.parseColor("#F46E86"))
            textView.setBackgroundColor(Color.parseColor("#FDEFF1"))
        }
    }
}
class CollectAdapter : ListAdapter<CollectBean, CollectAdapter.CollectViewHolder>(DIFF) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectViewHolder {
        return CollectViewHolder(
            ItemCollectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CollectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CollectViewHolder(private val binding: ItemCollectBinding) : RecyclerView.ViewHolder(binding.root) {

        companion object {

        }

        fun bind(item: CollectBean) {
            with(binding){
                binding.item = item
                executePendingBindings()
            }
        }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<CollectBean>() {
            override fun areItemsTheSame(oldItem: CollectBean, newItem: CollectBean): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CollectBean, newItem: CollectBean): Boolean {
                return oldItem == newItem
            }
        }
    }
}
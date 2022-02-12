package com.humanverse.humanverseapp.feature.home.ui.ui.ui.dashboard.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.humanverse.humanverseapp.R
import com.humanverse.humanverseapp.databinding.ItemDashboardBinding
import com.humanverse.humanverseapp.model.ModelDashboardItem

class DashboardItemAdapterAll(var context: Context): RecyclerView.Adapter<DashboardItemAdapterAll.DashboardItemViewHolder>()  {
    class DashboardItemViewHolder (
        private val binding: ItemDashboardBinding,
        private val adapter: DashboardItemAdapterAll,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindView(position: Int) {
            var mSelectedItem = -1
            val model = adapter.getCurrentDataSet()[position]
            Glide
                .with(context)
                .load(model.image)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.plus_holder)
                .into(binding.imageView7)
            binding.textTitle.text = model.title
            binding.cardViewDashboardItem.setOnClickListener {
                adapter.itemActionListener.invoke(model)
            }
        }
    }

    private val diffUtilsCallBack =
        object : DiffUtil.ItemCallback<ModelDashboardItem>() {
            override fun areItemsTheSame(
                oldItem: ModelDashboardItem,
                newItem: ModelDashboardItem
            ): Boolean {
                return oldItem.image == newItem.image
            }

            override fun areContentsTheSame(
                oldItem: ModelDashboardItem,
                newItem: ModelDashboardItem
            ): Boolean {
                return oldItem.image == newItem.image
            }
        }

    @SuppressLint("NotifyDataSetChanged")
    fun clearDataSet() {
        differ.submitList(null)
        this.notifyDataSetChanged()
    }


    fun getCurrentDataSet(): MutableList<ModelDashboardItem> =
        ArrayList(differ.currentList.toMutableList()).toMutableList()

    private var differ: AsyncListDiffer<ModelDashboardItem> =
        AsyncListDiffer(this, diffUtilsCallBack)

    fun submitListData(dataList: MutableList<ModelDashboardItem>) {
        differ.submitList(dataList)
        notifyDataSetChanged()
    }
    internal var itemActionListener: (model: ModelDashboardItem) -> Unit =
        {}
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardItemViewHolder =
        DashboardItemViewHolder(
            ItemDashboardBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            ),
            this,
            context
        )
    override fun onBindViewHolder(
        holder: DashboardItemViewHolder,
        position: Int)=
        holder.bindView(position)

    override fun getItemCount(): Int  = getCurrentDataSet().size
}

package com.humanverse.humanverseapp.feature.service

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.humanverse.humanverseapp.databinding.ItemServiceBinding
import com.humanverse.humanverseapp.model.ServiceModel

class ServiceItemAdapter(var context: Context): RecyclerView.Adapter<ServiceItemAdapter.ServiceItemViewHolder>()  {
    class ServiceItemViewHolder (
        private val binding: ItemServiceBinding,
        private val adapter: ServiceItemAdapter?,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindView(position: Int) {
            var mSelectedItem = -1
            val model = adapter!!.getCurrentDataSet()[position]
            Glide.with(context).load(model.img).into(binding.ivBanner)
            binding.tvStartsFrom.text=model.price.toString()+"$"
            binding.tvTitle.text=model.title.toString()
            binding.tvDescription.text= model.des
        }
    }

    private val diffUtilsCallBack =
        object : DiffUtil.ItemCallback<ServiceModel>() {
            override fun areItemsTheSame(
                oldItem: ServiceModel,
                newItem: ServiceModel
            ): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(
                oldItem: ServiceModel,
                newItem: ServiceModel
            ): Boolean {
                return oldItem.title == newItem.title
            }
        }

    @SuppressLint("NotifyDataSetChanged")
    fun clearDataSet() {
        differ.submitList(null)
        this.notifyDataSetChanged()
    }


    fun getCurrentDataSet(): MutableList<ServiceModel> =
        ArrayList(differ.currentList.toMutableList()).toMutableList()

    private var differ: AsyncListDiffer<ServiceModel> =
        AsyncListDiffer(this, diffUtilsCallBack)

    fun submitListData(dataList: MutableList<ServiceModel>) {
        differ.submitList(dataList)
        notifyDataSetChanged()
    }
    internal var itemActionListener: (model: ServiceModel) -> Unit =
        {}
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ServiceItemViewHolder =
        ServiceItemViewHolder(
            ItemServiceBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            ),
            this, context
        )
    override fun onBindViewHolder(
        holder: ServiceItemViewHolder,
        position: Int)=
            holder.bindView(position)

    override fun getItemCount(): Int  = differ.currentList.size
}

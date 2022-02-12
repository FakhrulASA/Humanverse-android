package com.humanverse.humanverseapp.feature.home.ui.ui.ui.dashboard.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.humanverse.humanverseapp.databinding.ItemServiceHistoryBinding
import com.humanverse.humanverseapp.model.HistoryServiceModel

class HistoryItemAdapter(var context: Context) :
    RecyclerView.Adapter<HistoryItemAdapter.DashboardItemViewHolder>() {
    var cont = context

    class DashboardItemViewHolder(
        private val binding: ItemServiceHistoryBinding,
        private val adapter: HistoryItemAdapter
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindView(position: Int) {
            var mSelectedItem = -1
            val model = adapter.getCurrentDataSet()[position]
            var statusService: String = ""
            if (model.status == 1) {
                statusService = "Pending"
                binding.serviceServiceOngoing.text
                binding.serviceServiceOngoing.setTextColor(Color.parseColor("#FF9933"));

            }
            if (model.status == 2) {
                statusService = "Confirmed"
                binding.serviceServiceOngoing.setTextColor(Color.parseColor("#90B70C"));

            }
            if (model.status == 3) {
                statusService = "Payment pending"
                binding.serviceServiceOngoing.setTextColor(Color.parseColor("#A276CA"));

            }
            if (model.status == 4) {
                statusService = "Complted"
                binding.serviceServiceOngoing.setTextColor(Color.parseColor("#40C100"));

            }
            binding.titleServiceOngoing.text = model.title
            binding.dateServiceOngoing.text = "Date: " + model.datetime
            binding.costServiceOngoing.text ="Est. cost: "+ model.price.toString() + "$"
            binding.serviceServiceOngoing.text = "Status: " + statusService
            binding.cardView4.setOnClickListener {
                adapter.itemActionListener.invoke(model)
            }
        }
    }

    private val diffUtilsCallBack =
        object : DiffUtil.ItemCallback<HistoryServiceModel>() {
            override fun areItemsTheSame(
                oldItem: HistoryServiceModel,
                newItem: HistoryServiceModel
            ): Boolean {
                return oldItem.serviceId == newItem.serviceId
            }

            override fun areContentsTheSame(
                oldItem: HistoryServiceModel,
                newItem: HistoryServiceModel
            ): Boolean {
                return oldItem.serviceId == newItem.serviceId
            }
        }

    @SuppressLint("NotifyDataSetChanged")
    fun clearDataSet() {
        differ.submitList(null)
        this.notifyDataSetChanged()
    }


    fun getCurrentDataSet(): MutableList<HistoryServiceModel> =
        ArrayList(differ.currentList.toMutableList()).toMutableList()

    private var differ: AsyncListDiffer<HistoryServiceModel> =
        AsyncListDiffer(this, diffUtilsCallBack)

    fun submitListData(dataList: MutableList<HistoryServiceModel>) {
        differ.submitList(dataList)
        notifyDataSetChanged()
    }

    internal var itemActionListener: (model: HistoryServiceModel) -> Unit =
        {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardItemViewHolder =
        DashboardItemViewHolder(
            ItemServiceHistoryBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            ),
            this
        )

    override fun onBindViewHolder(
        holder: DashboardItemViewHolder,
        position: Int
    ) =
        holder.bindView(position)

    override fun getItemCount(): Int = differ.currentList.size
}

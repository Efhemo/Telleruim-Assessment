package com.efhem.farmapp.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.View.OnClickListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.efhem.farmapp.R
import com.efhem.farmapp.domain.Farmer
import com.efhem.farmapp.util.K
import com.efhem.farmapp.util.Utils
import kotlinx.android.synthetic.main.farmers_item.view.*

class FarmersAdapter(private val interaction: Interaction) :
    ListAdapter<Farmer, FarmersAdapter.FarmerViewHolder>(FarmerDC()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FarmerViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.farmers_item, parent, false), interaction
    )

    override fun onBindViewHolder(holder: FarmerViewHolder, position: Int) = holder.bind(getItem(position))

    fun swapData(data: List<Farmer>) {
        submitList(data.toMutableList())
    }

    inner class FarmerViewHolder(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView), OnClickListener {

        init { itemView.setOnClickListener(this) }

        override fun onClick(v: View?) {

            if (adapterPosition == RecyclerView.NO_POSITION) return
            interaction?.onFarmersClick(farmer = getItem(adapterPosition) )

        }

        fun bind(item: Farmer) = with(itemView) {
            Glide.with(this.context).load(K.BASE_IMAGE_URL + Utils.removeBackSlash(item.avatar))
                .apply(RequestOptions.placeholderOf(R.drawable.glide_placeholder))
                .apply(RequestOptions.errorOf(R.drawable.glide_placeholder))
                .apply(RequestOptions.centerCropTransform())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(this.avatar)

            this.tv_name.text = resources.getString(R.string.farmers_name_wph, item.surname, item.firstName)
            this.tv_city.text = item.city
        }
    }

    private class FarmerDC : DiffUtil.ItemCallback<Farmer>() {
        override fun areItemsTheSame(
            oldItem: Farmer,
            newItem: Farmer
        ): Boolean = oldItem == newItem


        override fun areContentsTheSame(
            oldItem: Farmer,
            newItem: Farmer
        ): Boolean = oldItem.id == newItem.id
    }
}

class Interaction(val clickListener: (farmer: Farmer) -> Unit) {
    fun onFarmersClick(farmer: Farmer) = clickListener(farmer)
}
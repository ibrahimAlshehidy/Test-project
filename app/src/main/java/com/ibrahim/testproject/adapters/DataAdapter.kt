package com.ibrahim.testproject.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ibrahim.testproject.R
import com.ibrahim.testproject.netWork.models.DataModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_recycler_view.view.*


class DataAdapter(context: Context) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    private var dataList: MutableList<DataModel> = mutableListOf()
    private var layoutInflater: LayoutInflater? = null


    init {
        this.dataList = mutableListOf()
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        dataList[position].let { holder.bindItems(it) }
    }

    fun add(data: List<DataModel>) {
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(dataList: DataModel) {
            itemView.tvRepositoryName.text = dataList.name
            itemView.tvViewsNumber.text = dataList.watchers.toString()
            itemView.tvRepoDescription.text = dataList.language
            Picasso.with(itemView.context).load(dataList.owner.avatar_url).into(itemView.ivAvatar)
            itemView.ivAvatar

        }
    }

}

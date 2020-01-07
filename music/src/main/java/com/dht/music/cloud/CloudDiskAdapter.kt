package com.dht.music.cloud

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dht.baselib.base.BaseAdapter
import com.dht.baselib.callback.RecycleItemClickCallBack
import com.dht.music.R
import com.dht.music.databinding.RecycleItemCloudDiskBinding
import com.dht.music.util.ViewHolder
import java.util.*

/**
 * created by Administrator on 2018/12/27 16:37
 *
 * @author Administrator
 */
class CloudDiskAdapter internal constructor(recycleItemClickCallBack: RecycleItemClickCallBack<String?>?) : BaseAdapter<String?>() {
    private var usernameList = ArrayList<String>()
    override fun setChangeList(mList: MutableList<String?>?) {
        this.mList = mList
        notifyDataSetChanged()
    }

    override fun onCreateVH(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val mBinding = DataBindingUtil.inflate<RecycleItemCloudDiskBinding>(LayoutInflater.from(parent.context),
                R.layout.recycle_item_cloud_disk, parent, false)
        mBinding.callback = callBack
        return ViewHolder(mBinding)
    }

    override fun onBindVH(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder<*>) {
            (holder as ViewHolder<RecycleItemCloudDiskBinding>).mBinding.tvSongName.text = mList[position]
            holder.mBinding.tvUsername.text = usernameList[position]
            holder.mBinding.name = mList[position]
            holder.mBinding.index = position
        }
    }

    fun setUsernameList(usernameList: ArrayList<String>) {
        this.usernameList = usernameList
    }

    companion object {
        private const val TAG = "dht"
    }

    init {
        callBack = recycleItemClickCallBack!!
    }


}
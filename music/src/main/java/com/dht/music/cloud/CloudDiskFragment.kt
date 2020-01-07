package com.dht.music.cloud

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dht.baselib.base.BaseFragment
import com.dht.baselib.callback.NetworkCallback
import com.dht.baselib.callback.RecycleItemClickCallBack
import com.dht.baselib.util.VerticalDecoration
import com.dht.databaselib.bean.music.CloudMusicBean
import com.dht.eventbus.RxBus
import com.dht.eventbus.event.UpdateTopPlayEvent
import com.dht.music.R
import com.dht.music.databinding.FragmentCloudDiskBinding
import com.dht.network.BaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.map as map1

/**
 *
 * 云盘音乐页
 *
 * @author Administrator
 */
class CloudDiskFragment : BaseFragment() {

    private var mViewModel: CloudDiskViewModel? = null
    lateinit var mBinding: FragmentCloudDiskBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_cloud_disk, container, false)
        initViews(mBinding.root)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(CloudDiskViewModel::class.java)
        mBinding.cloudDisk = mViewModel
        bindViews()
    }

    private var localAdapter: CloudDiskAdapter? = null
    override fun initViews(view: View) {
        super.initViews(view)
        mBinding.cloudTopTitleView.setActivity(activity, mModel)
        mBinding.cloudTopTitleView.updatePlayView()
    }

    override fun bindViews() {
        super.bindViews()
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        localAdapter = CloudDiskAdapter(recycleItemClickCallBack)
        val names = ArrayList<String?>()
        localAdapter!!.setChangeList(names)
        mViewModel!!.getMusicList(callback)
        mBinding.recyclerView.adapter = localAdapter
        mBinding.recyclerView.layoutManager = layoutManager
        mBinding.recyclerView.addItemDecoration(VerticalDecoration(3))
    }

    override fun onResume() {
        super.onResume()
        RxBus.getInstance().post(UpdateTopPlayEvent())
    }

    private val callback: NetworkCallback<BaseModel<List<CloudMusicBean>>> = NetworkCallback { data ->
        GlobalScope.launch {
            mViewModel?.insertMusicList(data.result)
            val list = mViewModel?.getMusicList()
            withContext(Dispatchers.Main) {
                localAdapter!!.setUsernameList(list?.map1 { it.name } as ArrayList<String>)
                localAdapter!!.setChangeList(list.map1 { it.name } as MutableList<String?>)
                setRecycleViewVisible(true)
            }
        }
    }
    private val recycleItemClickCallBack: RecycleItemClickCallBack<String?> = object : RecycleItemClickCallBack<String?>() {
        override fun onItemClickListener(value: String?, position: Int) {
            super.onItemClickListener(value, position)
            Log.d(TAG, "onItemClickListener() called with: value = [$value], position = [$position]")
            mViewModel!!.downloadMusic(value)
        }
    }

    /**
     * 设置recyclerView 是否可见
     *
     * @param isRecyclerView 是否是RecyclerView
     */
    private fun setRecycleViewVisible(isRecyclerView: Boolean) {
        mBinding.recyclerView.visibility = if (isRecyclerView) View.VISIBLE else View.GONE
        mBinding.cloudNoMusic.visibility = if (isRecyclerView) View.GONE else View.VISIBLE
    }

    companion object {
        private const val TAG = "CloudDiskFragment"
        fun newInstance(): CloudDiskFragment {
            return CloudDiskFragment()
        }
    }
}
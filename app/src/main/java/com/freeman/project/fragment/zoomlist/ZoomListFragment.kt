package com.freeman.project.fragment.zoomlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.freeman.project.databinding.AdapterZoomlistBinding
import com.freeman.project.databinding.FragmentZoomlistBinding
import com.freeman.project.fragment.BaseFragment
import com.freeman.project.fragment.zoom.ZoomFragment
import com.freeman.project.sql.table.ZoomInfo
import com.freeman.project.utils.UtilRoute
import com.freeman.project.utils.UtilView.onSingleClick

class ZoomListFragment : BaseFragment<FragmentZoomlistBinding,ZoomListFragmentModel>() {

    companion object{
        fun newInstance(): ZoomListFragment {
            return ZoomListFragment()
        }
    }

    lateinit var zoomListAdapter:ZoomListAdapter
    override fun createViewBinding(container: ViewGroup?): FragmentZoomlistBinding {
        return FragmentZoomlistBinding.inflate(layoutInflater,container,false)
    }

    override fun createViewModelClass(): Class<ZoomListFragmentModel> {
        return ZoomListFragmentModel::class.java
    }

    override fun initView() {
        viewBinding.apply {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                zoomListAdapter = ZoomListAdapter()
                adapter = zoomListAdapter
                addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
            }
        }

        viewModel.zoomInfos.observe(
            viewLifecycleOwner, {
                zoomListAdapter.updateDatas(it)
            }
        )
        viewModel.getZoomInfoJson()
    }

    override fun onBackPress(): Boolean {
        showExitDialog()
        return true
    }



    //viewholder
    inner class ViewHolderZoomList(var itemViewBinding: AdapterZoomlistBinding) : RecyclerView.ViewHolder(itemViewBinding.root) {

        fun bind(zoomInfo: ZoomInfo){
            itemViewBinding.apply {
                cell.tag = zoomInfo
                myImgView.apply {
                    //setTransType(MyImgView.TransType.Angle)
                    setImageUrl(zoomInfo.pic,0,0,false)
                }
                tvTitle.text = zoomInfo.name
                tvInfo.text = zoomInfo.info
                tvCategory.text = zoomInfo.category
            }
        }
    }
    //adapter--------------------------------------------------------
    inner class ZoomListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private var _datas = ArrayList<ZoomInfo>()

        open fun updateDatas(datas:ArrayList<ZoomInfo>?){
            if(datas!=null) {
                _datas = datas
            }
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return _datas.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return ViewHolderZoomList(
                AdapterZoomlistBinding.inflate(LayoutInflater.from(context), parent, false).apply {
                    cell.onSingleClick {
                        UtilRoute.routeNewFragment(ZoomFragment.newInstance(it.tag as ZoomInfo))
                    }
                }
            )
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as ViewHolderZoomList).bind(_datas[position])
        }
    }

}
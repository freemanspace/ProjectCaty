package com.freeman.project.fragment.zoom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.freeman.project.data.data.PlantInfo
import com.freeman.project.databinding.AdapterPlantBinding
import com.freeman.project.databinding.FragmentZoomBinding
import com.freeman.project.fragment.BaseFragment
import com.freeman.project.fragment.plant.PlantFragment
import com.freeman.project.sql.table.ZoomInfo
import com.freeman.project.utils.AppSetting
import com.freeman.project.utils.UtilPhone
import com.freeman.project.utils.UtilRoute
import com.freeman.project.utils.UtilView.onSingleClick

class ZoomFragment : BaseFragment<FragmentZoomBinding, ZoomFragmentModel>() {

    companion object{
        fun newInstance(zoomInfo:ZoomInfo): ZoomFragment {
            return ZoomFragment().apply {
                val args = Bundle()
                args.putParcelable("zoominfo",zoomInfo)
                arguments = args
            }
        }
    }

    lateinit var plantAdapter:PlantAdapter
    override fun createViewBinding(container: ViewGroup?): FragmentZoomBinding {
        return FragmentZoomBinding.inflate(layoutInflater,container,false)
    }

    override fun createViewModelClass(): Class<ZoomFragmentModel> {
        return ZoomFragmentModel::class.java
    }

    override fun initView() {
        viewBinding.apply {
            viewModel.zoomInfo = arguments?.getParcelable<ZoomInfo>("zoominfo")!!

            tvTitle.text = viewModel.zoomInfo.name
            btnTitleLeft.onSingleClick {
                finish()
            }

            val imgW = AppSetting.DeviceWidth
            val imgH = (imgW*2)/3
            myImg.apply {
                setImageUrl(viewModel.zoomInfo.pic,imgW,imgH,false)
            }

            collapsingToolbarLayout.apply {
                layoutParams.height = imgH
            }
            tvZoomInfo.text = viewModel.zoomInfo.info

            constraintLayout.viewTreeObserver.addOnPreDrawListener {
                appBarLayout.apply {
                    layoutParams.height = imgH + constraintLayout.measuredHeight
                }
                true
            }

            tvWeb.onSingleClick {
                UtilPhone.openBrowser(requireActivity(),viewModel.zoomInfo.url)
            }

            recyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                plantAdapter = PlantAdapter()
                adapter = plantAdapter
                addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            }

            viewModel.plantInfos.observe(viewLifecycleOwner, {
                plantAdapter.updateDatas(it)
            })
        }
        viewModel.getPlantInfos()
    }


    //viewholder
    inner class ViewHolderPlant(var itemViewBinding: AdapterPlantBinding) : RecyclerView.ViewHolder(itemViewBinding.root) {

        fun bind(plantInfo: PlantInfo){
            itemViewBinding.apply {
                cell.tag = plantInfo
                if(plantInfo.picList.size>0) {
                    myImgView.apply {
                        //setTransType(MyImgView.TransType.Angle)
                        setImageUrl(plantInfo.picList[0], 0, 0, false)
                    }
                }
                tvTitle.text = plantInfo.name
                tvAlso.text = plantInfo.alsoKnown
            }
        }
    }
    //adapter--------------------------------------------------------
    inner class PlantAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        private var _datas = ArrayList<PlantInfo>()

        open fun updateDatas(datas:ArrayList<PlantInfo>?){
            if(datas!=null) {
                _datas = datas
            }
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return _datas.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return ViewHolderPlant(
                AdapterPlantBinding.inflate(LayoutInflater.from(context), parent, false).apply {
                    cell.onSingleClick {
                        UtilRoute.routeNewFragment(PlantFragment.newInstance(it.tag as PlantInfo))
                    }
                }
            )
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as ViewHolderPlant).bind(_datas[position])
            if(position==_datas.size-1){
                viewModel.getPlantInfos()
            }
        }
    }

}

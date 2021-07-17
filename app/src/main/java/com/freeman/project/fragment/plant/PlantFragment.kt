package com.freeman.project.fragment.plant

import android.os.Bundle
import android.view.ViewGroup
import com.freeman.project.data.data.PlantInfo
import com.freeman.project.databinding.FragmentPlantBinding
import com.freeman.project.fragment.BaseFragment
import com.freeman.project.utils.AppSetting
import com.freeman.project.utils.UtilView.onSingleClick

class PlantFragment : BaseFragment<FragmentPlantBinding, PlantFragmentModel>() {

    companion object{
        fun newInstance(plantInfo: PlantInfo): PlantFragment {
            return PlantFragment().apply {
                val args = Bundle()
                args.putParcelable("plantInfo",plantInfo)
                arguments = args
            }
        }
    }

    override fun createViewBinding(container: ViewGroup?): FragmentPlantBinding {
        return FragmentPlantBinding.inflate(layoutInflater,container,false)
    }

    override fun createViewModelClass(): Class<PlantFragmentModel> {
        return PlantFragmentModel::class.java
    }

    override fun initView() {
        viewModel.plantInfo = arguments?.getParcelable<PlantInfo>("plantInfo")!!

        viewBinding.apply {
            tvTitle.text = viewModel.plantInfo.name
            btnTitleLeft.onSingleClick {
                finish()
            }

            val imgW = AppSetting.DeviceWidth
            val imgH = (imgW*2)/3
            posterView.apply {
                setImagePaths(viewModel.plantInfo.picList,imgW,imgH)
            }

            collapsingToolbarLayout.apply {
                layoutParams.height = imgH
            }
            appBarLayout.apply {
                layoutParams.height = imgH
            }

            tvMsg.text = viewModel.getPlantMsg()
        }
    }
}

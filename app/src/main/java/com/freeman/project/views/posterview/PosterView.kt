package com.freeman.project.views.posterview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.freeman.project.R
import com.freeman.project.databinding.AdapterPosterviewBinding
import com.freeman.project.databinding.ViewPosterBinding
import com.freeman.project.utils.AppSetting
import kotlin.collections.ArrayList

class PosterView : ConstraintLayout{
    val cycleTime = 20  //詢還次數
    lateinit var viewBinding:ViewPosterBinding
    private val dotViews = ArrayList<View>()
    private lateinit var posterViewAdapter:PosterViewAdapter
    constructor(context: Context):super(context){
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?):super(context,attrs){
        initView(context)
    }

    private fun initView(context: Context){
        viewBinding = ViewPosterBinding.inflate(LayoutInflater.from(context),this,true)
    }

    fun setImagePaths(imagePaths: ArrayList<String>, width: Int, height: Int) {
        dotViews.clear()

        viewBinding.apply {
            if(layoutParams==null){
                layoutParams = ViewGroup.LayoutParams(width, height)
            } else{
                layoutParams.width = width
                layoutParams.height = height
            }
            //dot
            var viewDot:View?
            linearLayout.apply {
                removeAllViews()
                for (i in imagePaths.indices){
                    viewDot = View(context).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            (7 * AppSetting.ScreenDensity).toInt(),
                            (7 * AppSetting.ScreenDensity).toInt()
                        ).apply {
                            setMargins(0, 0, (5 * AppSetting.ScreenDensity).toInt(), 0)
                        }
                    }
                    addView(viewDot)
                    dotViews.add(viewDot!!)
                }
            }
            //view pager
            viewPager.apply {
                posterViewAdapter = PosterViewAdapter(imagePaths)
                adapter = posterViewAdapter
                if(imagePaths.size>1) {
                    post {
                        setCurrentItem((imagePaths.size * cycleTime / 2), false)
                    }
                }
                registerOnPageChangeCallback(object: OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        val index = position % dotViews.size
                        for (i in 0 until dotViews.size){
                            if(i==index){
                                dotViews[i].setBackgroundResource(R.drawable.dot_black)
                            } else{
                                dotViews[i].setBackgroundResource(R.drawable.dot_gray)
                            }
                        }
                    }
                })
            }
        }
    }


    inner class ViewHolderPoster(var itemViewBinding: AdapterPosterviewBinding) : RecyclerView.ViewHolder(itemViewBinding.root) {
        fun bind(imgPath: String){
            itemViewBinding.apply {
                myImg.apply {
                    setImageUrl(imgPath,0,0,false)
                }
            }
        }
    }

    inner class PosterViewAdapter(var imagePaths: ArrayList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun getItemCount(): Int {
            return if(imagePaths.size>1) {
                cycleTime * imagePaths.size
            } else{
                imagePaths.size
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return ViewHolderPoster(
                AdapterPosterviewBinding.inflate(LayoutInflater.from(context), parent, false)
            )
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as ViewHolderPoster).bind(imagePaths[position % imagePaths.size])
        }
    }
}
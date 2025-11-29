package com.example.strayanimals.ui.encyclopedia

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.example.strayanimals.R
import com.example.strayanimals.databinding.ActivityMainBinding
import com.example.strayanimals.databinding.SearchEncFragmentBinding
import com.iammert.library.ui.multisearchviewlib.MultiSearchView
import com.makeramen.roundedimageview.RoundedImageView
import java.lang.Exception
import java.util.ArrayList
import android.view.animation.Animation

import android.view.animation.ScaleAnimation
import android.view.animation.AnimationSet

class SearchEncActivity : AppCompatActivity() {
    private lateinit var data: ArrayList<EncycBean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: SearchEncFragmentBinding = DataBindingUtil.setContentView(this, R.layout.search_enc_fragment)
        val image1 = binding.image1
        val image2 = binding.image2
        val image3 = binding.image3
        val image4 = binding.image4
        val image5 = binding.image5
        val image6 = binding.image6
        //获取Item列表
        val itemList = GetEncycItem(this)
        data = itemList.getItem()
        val setAnimation = AnimationSet(true)
        // 子动画2:缩放动画 https://www.jianshu.com/p/26a801a755a0
        val scale1: Animation = ScaleAnimation(
            0F,
            1f,
            0F,
            1f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        scale1.duration = 500
        setAnimation.addAnimation(scale1)
        binding.multiSearchView.setSearchViewListener(object : MultiSearchView.MultiSearchViewListener{
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onItemSelected(index: Int, s: CharSequence) {
                filter(s.toString())?.run {
                    try {
                        Glide.with(this@SearchEncActivity).load(this[0].getImg()).error(R.drawable.brazuca_dog)
                            .into(image1)
                        Glide.with(this@SearchEncActivity).load(this[1].getImg()).error(R.drawable.brazuca_dog)
                            .into(image2)
                        Glide.with(this@SearchEncActivity).load(this[2].getImg()).error(R.drawable.brazuca_dog)
                            .into(image3)
                        Glide.with(this@SearchEncActivity).load(this[3].getImg()).error(R.drawable.brazuca_dog)
                            .into(image4)
                        Glide.with(this@SearchEncActivity).load(this[4].getImg()).error(R.drawable.brazuca_dog)
                            .into(image5)
                        Glide.with(this@SearchEncActivity).load(this[5].getImg()).error(R.drawable.brazuca_dog)
                            .into(image6)
                        image1.startAnimation(setAnimation)
                        image2.startAnimation(setAnimation)
                        image3.startAnimation(setAnimation)
                        image4.startAnimation(setAnimation)
                        image5.startAnimation(setAnimation)
                        image6.startAnimation(setAnimation)


                    }catch (e:Exception){
                        image1.setImageIcon(null)
                        image2.setImageIcon(null)
                        image3.setImageIcon(null)
                        image4.setImageIcon(null)
                        image5.setImageIcon(null)
                        image6.setImageIcon(null)
                    }

                }

            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onTextChanged(index: Int, s: CharSequence) {
                Log.e("TEST", "changed: index: $index, query: $s")
//                if (s.toString()=="")
                filter(s.toString())?.run {
                    try {
                        Glide.with(this@SearchEncActivity).load(this[0].getImg()).error(R.drawable.brazuca_dog)
                            .into(image1)
                        Glide.with(this@SearchEncActivity).load(this[1].getImg()).error(R.drawable.brazuca_dog)
                            .into(image2)
                        Glide.with(this@SearchEncActivity).load(this[2].getImg()).error(R.drawable.brazuca_dog)
                            .into(image3)
                        Glide.with(this@SearchEncActivity).load(this[3].getImg()).error(R.drawable.brazuca_dog)
                            .into(image4)
                        Glide.with(this@SearchEncActivity).load(this[4].getImg()).error(R.drawable.brazuca_dog)
                            .into(image5)
                        Glide.with(this@SearchEncActivity).load(this[5].getImg()).error(R.drawable.brazuca_dog)
                            .into(image6)
                        image1.startAnimation(setAnimation)
                        image2.startAnimation(setAnimation)
                        image3.startAnimation(setAnimation)
                        image4.startAnimation(setAnimation)
                        image5.startAnimation(setAnimation)
                        image6.startAnimation(setAnimation)
                        image1.setOnClickListener {
                            val intent =
                                Intent(this@SearchEncActivity, WebViewEncActivity::class.java)
                            //把每个item对应的url传到webview
                            //把每个item对应的url传到webview
                            intent.putExtra("url", this@run[0].getUrl())
                            startActivity(intent)
                            finish()
                        }
                    }catch (e:Exception){
                        image1.setImageIcon(null)
                        image2.setImageIcon(null)
                        image3.setImageIcon(null)
                        image4.setImageIcon(null)
                        image5.setImageIcon(null)
                        image6.setImageIcon(null)
                    }

                }
                //            mImageView.setText(encycBean.getDate());

            }

            override fun onSearchComplete(index: Int, s: CharSequence) {
                Log.v("TEST", "complete: index: $index, query: $s")
                
            }

            override fun onSearchItemRemoved(index: Int) {
                Log.v("TEST", "remove: index: $index")
            }

        })
    }
    private fun changeImage(){

    }
    //搜索关键词
    private fun filter(key:String): ArrayList<EncycBean> {
        var temp = ArrayList<EncycBean>()
        var flag = 0
        for (i in data){
            if (i.title[0].toString() == key[0].toString()){
                temp.add(i)
                flag ++

            }

            if (flag>6) break

        }
        return temp
    }
}
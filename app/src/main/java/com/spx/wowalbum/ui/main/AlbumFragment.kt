package com.spx.wowalbum.ui.main

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.Coil
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import coil.transform.BlurTransformation
import com.bumptech.glide.Glide
import com.spx.wowalbum.R

class AlbumFragment : Fragment() {
    private lateinit var product: Product
    private lateinit var viewModel: AlbumViewModel
    private lateinit var viewPager: ViewPager2


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_album, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 初始化 ViewModel
        viewModel = ViewModelProvider(this).get(AlbumViewModel::class.java)

        viewPager = view.findViewById(R.id.album_pager)

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = HorizontalSlidePagerAdapter(product.photos)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = pagerAdapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // 在这里处理页面选中时的逻辑
                // position 是当前选中的页面的索引
                Log.i(TAG, "Selected page: $position")
                pagerAdapter.preload(requireContext(), position)
//                    .data(photoUrl))
            }
        })


        viewModel.photoUrls.observe(viewLifecycleOwner, Observer { newUrls ->
            // 更新 Adapter 中的数据
            pagerAdapter.updateData(newUrls)
        })

        // 初始生成一组 URL
        viewModel.updatePhotos(product)
    }

    class HorizontalSlidePagerAdapter(
        private val photoUrls: MutableList<String>
    ) : RecyclerView.Adapter<HorizontalSlidePagerAdapter.ViewHolder>() {

        fun preload(context: Context, currentIndex: Int) {
            if (photoUrls.size > currentIndex + 1) {
                context.imageLoader.enqueue(
                    ImageRequest.Builder(context).data(photoUrls[currentIndex + 1]).build()
                )
            }
        }

        fun updateData(newUrls: List<String>) {
            photoUrls.clear();
            photoUrls.addAll(newUrls)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_horizontal_slide_page, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val product = photoUrls[position]
            // 在这里使用 product 更新 ViewHolder 中的 UI 元素
            holder.bind(product)
        }

        override fun getItemCount(): Int = photoUrls.size

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val coverImageView: ImageView = itemView.findViewById(R.id.one_album_cover)
            private val imageInfoTv: TextView = itemView.findViewById(R.id.one_album_drawable_info)

            // 添加一个方法用于绑定数据
            fun bind(photoUrl: String) {
                Log.i(
                    TAG,
                    "load onCancel width:" + coverImageView.width + ", height:" + coverImageView.height
                )
//                Glide.with(coverImageView.context).load(photoUrl).into(coverImageView)
//                 //使用 product 更新 UI
                coverImageView.load(photoUrl) {
                    crossfade(true)
                    transformations(BlurTransformation(context = coverImageView.context, radius = 20f, sampling = 2f))
//                    BlurTransformation(context = coverImageView.context,)
                }
            }
        }
    }


    companion object {
        fun newInstance(product: Product): Fragment {
            val fragment = AlbumFragment()
            fragment.product = product
            return fragment
        }
    }
}

package com.spx.wowalbum.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.spx.wowalbum.R

private const val NUM_PAGES = 5

class PhotoSlideFragment : Fragment() {

    companion object {
        fun newInstance(product: Product): PhotoSlideFragment {
            val fragment = PhotoSlideFragment()
            fragment.product = product
            return fragment
        }
    }

    private lateinit var viewModel: PhotoSlideViewModel

    private lateinit var viewPager: ViewPager2
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PhotoSlideViewModel::class.java)
        product = arguments?.getSerializable("data") as Product
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_photo_slide, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Instantiate a ViewPager2 and a PagerAdapter.
        view.findViewById<TextView>(R.id.textViewTitle).text = product.product_desc
        view.findViewById<View>(R.id.icon_back_iv).setOnClickListener {
            findNavController().popBackStack()
        }
        viewPager = view.findViewById(R.id.photo_pager)
        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = AlbumFragment.HorizontalSlidePagerAdapter(product.photos)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = pagerAdapter
        viewModel.photoUrls.observe(viewLifecycleOwner, Observer { newUrls ->
            // 更新 Adapter 中的数据
            pagerAdapter.updateData(newUrls)
        })

        // 初始生成一组 URL
        viewModel.updatePhotos(product)
    }

    private inner class VerticalSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

        private var dataList: List<Product> = emptyList()

        override fun getItemCount(): Int = dataList.size

        override fun createFragment(position: Int): Fragment {
            // Pass the data to the fragment during creation
            return AlbumFragment.newInstance(dataList[position])
        }
    }
}
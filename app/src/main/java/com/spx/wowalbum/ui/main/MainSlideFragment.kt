package com.spx.wowalbum.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.spx.wowalbum.R

private const val NUM_PAGES = 5

class MainSlideFragment : Fragment() {

    companion object {
        fun newInstance() = MainSlideFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager = view.findViewById(R.id.model_pager)

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = VerticalSlidePagerAdapter(requireActivity())
        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        viewPager.adapter = pagerAdapter
        viewModel.dataList.observe(viewLifecycleOwner, Observer {
            pagerAdapter.updateData(it)
            pagerAdapter.notifyDataSetChanged()
        })
        viewModel.initModelDataList(requireContext())

        // Attach a listener to ViewPager2 to detect when reaching the end
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == pagerAdapter.itemCount - 1) {
                    // Load more data when reaching the end
                    viewModel.loadMoreData()
                }
            }
        })
    }

    private inner class VerticalSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

        private var dataList: List<Product> = emptyList()

        fun updateData(dataList: List<Product>) {
            this.dataList = dataList
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int = dataList.size

        override fun createFragment(position: Int): Fragment {
            // Pass the data to the fragment during creation
            return AlbumFragment.newInstance(dataList[position])
        }
    }
}
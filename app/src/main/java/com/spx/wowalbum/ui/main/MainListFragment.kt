package com.spx.wowalbum.ui.main

import android.content.Context
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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.spx.wowalbum.R

private const val NUM_PAGES = 5


class MainListFragment : Fragment() {

    companion object {
        val TAG = "MainListFragment"
        fun newInstance() = MainListFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var listView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: ...")
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(TAG, "onCreateView: ...")
        return inflater.inflate(R.layout.fragment_main_rv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: ...")
        view.findViewById<View>(R.id.icon_back_iv).visibility = View.GONE
        view.findViewById<TextView>(R.id.textViewTitle).text = "所有妹子"
        // Instantiate a ViewPager2 and a PagerAdapter.
        listView = view.findViewById(R.id.model_rv_List)

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = VerticalListAdapter(requireActivity())
        listView.layoutManager = LinearLayoutManager(requireContext())
        listView.adapter = pagerAdapter
        viewModel.modelList.observe(viewLifecycleOwner, Observer {
            pagerAdapter.updateData(it)
            pagerAdapter.notifyDataSetChanged()
        })
        viewModel.initModelDataList(requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView: ...")
    }

    fun gotoModelProfile(model: ModelData) {
        val bundle = Bundle()
        bundle.putSerializable("data", model)
        findNavController().navigate(R.id.action_mainListFragment_to_modelProfileFragment, bundle)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textViewName)
        private val descTextView: TextView = itemView.findViewById(R.id.textViewDescription)
        private val avatarIv: ImageView = itemView.findViewById(R.id.avatar)
        private val imageView1: ImageView = itemView.findViewById(R.id.imageView1)
        private val imageView2: ImageView = itemView.findViewById(R.id.imageView2)
        private val imageView3: ImageView = itemView.findViewById(R.id.imageView3)
        fun bind(model: ModelData) {
            titleTextView.text = model.name
            descTextView.text = model.desc
            avatarIv.load(model.avatar_url) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }

            if (model.products.size > 2) {
                showProductCover(imageView1, model.products[2].cover_url)
            }
            if (model.products.size > 1) {
                showProductCover(imageView2, model.products[1].cover_url)
            }
            if (model.products.size > 0) {
                showProductCover(imageView3, model.products[0].cover_url)
            }
            itemView.setOnClickListener {
                gotoModelProfile(model)
            }
        }

        private fun showProductCover(imageView: ImageView, coverUrl: String) {
            imageView.load(coverUrl) {
                crossfade(true)
            }
        }
    }

    inner class VerticalListAdapter(private val context: Context) :
        RecyclerView.Adapter<MyViewHolder>() {
        private var dataList: List<ModelData> = emptyList()

        fun updateData(dataList: List<ModelData>) {
            this.dataList = dataList
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.model_list_item, parent, false)
            return MyViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bind(dataList[position])
        }

        override fun getItemCount(): Int {
            return dataList.size
        }
    }
}

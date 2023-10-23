package com.spx.wowalbum.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.spx.wowalbum.R

private const val NUM_SPAN = 3

class ModelProfileFragment : Fragment() {

    companion object {
        fun newInstance(data: ModelData): ModelProfileFragment {
            val fragment = ModelProfileFragment()
//            fragment.arguments?.putSerializable("data", data)
            fragment.model = data;
            return fragment
        }
    }

//    private lateinit var viewModel: MainViewModel

    private lateinit var listView: RecyclerView
    private lateinit var model: ModelData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_model_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.textViewTitle).text =  model.name + "  " + model.desc
        view.findViewById<View>(R.id.icon_back_iv).setOnClickListener {
            requireActivity().finish()
        }

        listView = view.findViewById(R.id.model_product_List)

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = VerticalListAdapter(requireActivity(), model.products)
        listView.layoutManager = GridLayoutManager(requireContext(), NUM_SPAN)
        listView.adapter = pagerAdapter
//        viewModel.modelList.observe(viewLifecycleOwner, Observer {
//            pagerAdapter.updateData(it)
//            pagerAdapter.notifyDataSetChanged()
//        })
//        viewModel.initModelDataList(requireContext())
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        fun bind(product: Product) {
            imageView.load(product.cover_url) {
                crossfade(true)
            }
            itemView.setOnClickListener {
                (it.context as AppCompatActivity).startActivity(
                    Intent(
                        it.context,
                        PhotoSlideActivity::class.java
                    ).apply {
                        putExtra("data", product)
                    }
                )
            }
        }
    }

    class VerticalListAdapter(private val context: Context, val dataList: List<Product>) :
        RecyclerView.Adapter<MyViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.model_product_list_item, parent, false)
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
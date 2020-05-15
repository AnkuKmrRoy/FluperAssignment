package com.example.fluperassignmet.ui.products.product_list

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.util.Base64.encodeToString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fluperassignmet.R
import com.example.fluperassignmet.data.db.entity.Products
import com.example.fluperassignmet.databinding.ProductListRowItemBinding
import java.util.*
import java.util.Base64.getDecoder


class ProductListRecyclerViewAdapter(private val productList: List<Products>,private val clickListener:(Products)->Unit) :
    RecyclerView.Adapter<ProductListRecyclerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductListRecyclerViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ProductListRowItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.product_list_row_item, parent, false)
        return ProductListRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductListRecyclerViewHolder, position: Int) {
        holder.bind(productList[position],clickListener)
    }
}

class ProductListRecyclerViewHolder(val binding: ProductListRowItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Products,clickListener: (Products) -> Unit) {
        binding.tvProdName.text = product.name
        binding.tvProdDescription.text = product.description
        binding.tvProdRegPrice.text = product.regular_price
        binding.tvProdSalePrice.text = product.sale_price
        binding.tvProdColor.text = product.color_id
        binding.tvProdStores.text = product.store_id
        val imageBytes = android.util.Base64.decode(product.product_photo, 0)
        val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        binding.icProductImage.setImageBitmap(image)

        binding.llProductItem.setOnClickListener{
            clickListener(product)
        }

        binding.icProductImage.setOnClickListener{
            //clickListener(product.product_photo)
        }

    }
}
package com.example.fluperassignmet.ui.products.product_list

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fluperassignmet.R
import com.example.fluperassignmet.data.db.entity.Products
import com.example.fluperassignmet.databinding.ProductListRowItemBinding
import java.lang.Exception


class ProductListRecyclerViewAdapter(private val productList: List<Products>, private val clickListener: OnProductItemImageClickedListener
) :
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
        holder.bind(productList[position], clickListener)
    }


}

class ProductListRecyclerViewHolder(val binding: ProductListRowItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Products, clickListener: OnProductItemImageClickedListener) {
        //val productItemImageClicked: OnProductItemImageClickedListener? = clickListener
        var productPhoto: String? = null
        binding.tvProdName.text = product.name
        binding.tvProdDescription.text = product.description
        binding.tvProdRegPrice.text = product.regular_price
        binding.tvProdSalePrice.text = product.sale_price
        binding.tvProdColor.text = product.colors?.get(0)
        binding.tvProdStores.text = product.stores?.get(0)?.name
        productPhoto = product.product_photo
        try {
            val imageBytes = android.util.Base64.decode(productPhoto, 0)
            val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            binding.icProductImage.setImageBitmap(image)
        }catch (e:Exception){
            e.printStackTrace()
        }


        binding.llProductItem.setOnClickListener {
            //clickListener(product)
            clickListener?.productItemCLicked(product)

        }

        binding.icProductImage.setOnClickListener {
            if (productPhoto != null) {
                clickListener?.productItemImageClicked(productPhoto)
            }
        }
    }

}


interface OnProductItemImageClickedListener {
    fun productItemImageClicked(stringImage: String)

    fun productItemCLicked(product: Products)

}


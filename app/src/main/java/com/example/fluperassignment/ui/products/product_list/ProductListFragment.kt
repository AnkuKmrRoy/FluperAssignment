package com.example.fluperassignmet.ui.products.product_list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fluperassignmet.R
import com.example.fluperassignmet.data.db.FluperAssignmentDatabase
import com.example.fluperassignmet.data.db.dao.ProductsDao
import com.example.fluperassignmet.data.db.entity.Products
import com.example.fluperassignmet.data.repositories.ProductsRepositories
import com.example.fluperassignmet.databinding.ProductListFragmentBinding
import com.example.fluperassignmet.ui.view_model.ProductViewModel
import com.example.fluperassignmet.ui.products.update_product.UpdateProductFragment
import com.example.fluperassignmet.ui.view_model.ProductViewModelFactory
import com.leopold.mvvm.ui.BindingFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListFragment : BindingFragment<ProductListFragmentBinding>() {

    companion object {
        fun newInstance() =
            ProductListFragment()
    }
    override fun getLayoutResId() = R.layout.product_list_fragment;

    private  val productListViewModel: ProductViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.productListViewModel = productListViewModel
        binding.setLifecycleOwner(this)
        initRecyclerView()
    }


    private fun initRecyclerView(){
        binding.rvProductList.layoutManager = LinearLayoutManager(context)
        displayProductList()
    }

    @SuppressLint("FragmentLiveDataObserve")
    fun displayProductList(){
        productListViewModel.products.observe(this, Observer {

            binding.rvProductList.adapter = ProductListRecyclerViewAdapter(it,{selectedItem:Products->productListItemClicked(selectedItem)})
        })
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun productListItemClicked(product: Products){

        productListViewModel.selectedProduct(product)
        val createProduct = Bundle().apply {
            putBoolean("IS_CREATE_PRODUCT", false)
        }
        activity?.findNavController(R.id.nav_host_fragment)?.navigate( R.id.action_ProductListFragment_to_UpdateProductFragment,createProduct)

    }

}

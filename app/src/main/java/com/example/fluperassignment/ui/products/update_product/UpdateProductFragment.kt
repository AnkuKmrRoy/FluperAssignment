package com.example.fluperassignmet.ui.products.update_product

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import com.example.fluperassignmet.R
import com.example.fluperassignmet.data.db.entity.Products
import com.example.fluperassignmet.databinding.UpdateProductFragmentBinding
import com.example.fluperassignmet.ui.view_model.ProductViewModel
import com.leopold.mvvm.ui.BindingFragment
import kotlinx.android.synthetic.main.update_product_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.io.File

class UpdateProductFragment : BindingFragment<UpdateProductFragmentBinding>(),View.OnClickListener {


    var isProductData:Boolean = false
    val REQUEST_CODE = 200
    private var mCurrentPhotoPath: String? = null;
    private val PERMISSION_REQUEST_CODE: Int = 101

    companion object {
        fun newInstance() = UpdateProductFragment()
    }

    private  val updateProductViewModel: ProductViewModel by sharedViewModel()


    override fun getLayoutResId() = R.layout.update_product_fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.updateProductViewModel = updateProductViewModel
        binding.setLifecycleOwner(this)
        fabCaptureImage.setOnClickListener(this)
        ivDisplayProductImage.setOnClickListener(this)
    }


    override fun onResume() {
        super.onResume()
        val isCreateProduct = arguments?.getBoolean("IS_CREATE_PRODUCT")
        if(isCreateProduct!!){
            clearProductData()
            llUpdateDeleteProductItem.visibility = GONE
            btSaveProduct.visibility = VISIBLE

        }else {
            displayProductData()
            llUpdateDeleteProductItem.visibility = VISIBLE
            btSaveProduct.visibility = GONE
        }
    }

    fun validationCheck(){
        updateProductViewModel.message.observe(this, Observer {
            //Toast.makeText(context,it,Toast.LENGTH_SHORT).show()
        })
    }


    fun displayProductData(){
      updateProductViewModel.selected.observe(viewLifecycleOwner, Observer {
          if(it != null) {
              setProductData(it)
          }
      })
    }

    fun setProductData(product: Products){
        etProductName.setText(product.name)
        etProductDescription.setText(product.description)
        etProductRegPrice.setText(product.regular_price)
        etProductSalePrice.setText(product.sale_price)
        etProductColor.setText(product.color_id)
        etProductStores.setText(product.store_id)
        val imageBytes = android.util.Base64.decode(product.product_photo, 0)
        val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        ivDisplayProductImage.setImageBitmap(image)
    }

    fun clearProductData(){
        etProductName.getText().clear()
        etProductDescription.getText().clear()
        etProductRegPrice.getText().clear()
        etProductSalePrice.getText().clear()
        etProductColor.getText().clear()
        etProductStores.getText().clear()
        ivDisplayProductImage.setImageBitmap(null)
    }

    override fun onClick(v: View?) {
        if(v != null){
            when (v.getId()) {
                R.id.fabCaptureImage -> {
                    captureProductImage()

                }
                R.id.ivDisplayProductImage -> displayProductImage()
            }
        }
    }

    fun captureProductImage(){

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE && data != null){
            val imageBitmap = data.extras?.get("data") as Bitmap
            ivDisplayProductImage.setImageBitmap(imageBitmap)
        }
    }

    fun displayProductImage(){

    }





}

package com.example.fluperassignment.ui.products.show_product_image

import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.fluperassignmet.R
import com.example.fluperassignmet.databinding.ShowProductImageFragmentBinding
import com.example.fluperassignmet.ui.view_model.ProductViewModel
import com.leopold.mvvm.ui.BindingFragment
import kotlinx.android.synthetic.main.show_product_image_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ShowProductImageFragment : BindingFragment<ShowProductImageFragmentBinding>() {

    companion object {
        fun newInstance() = ShowProductImageFragment()
    }

    private val showProductImageViewModel: ProductViewModel by sharedViewModel()

    override fun getLayoutResId() = R.layout.show_product_image_fragment



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.updateProductViewModel = showProductImageViewModel
        binding.setLifecycleOwner(this)
    }

    override fun onResume() {
        super.onResume()
        val stringImage = arguments?.getString("STRING_IMAGE")

        val imageBytes = android.util.Base64.decode(stringImage, 0)
        val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        ivFullScreenImage.setImageBitmap(image)
    }

}

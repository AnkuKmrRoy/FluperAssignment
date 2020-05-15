package com.example.fluperassignmet.ui.main

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.fluperassignmet.R
import com.example.fluperassignmet.data.db.FluperAssignmentDatabase
import com.example.fluperassignmet.data.db.dao.ProductsDao
import com.example.fluperassignmet.data.repositories.ProductsRepositories
import com.example.fluperassignmet.databinding.ActivityMainBinding
import com.example.fluperassignmet.ui.view_model.ProductViewModel
import com.example.fluperassignmet.ui.products.product_list.ProductListFragment
import com.example.fluperassignmet.ui.products.update_product.UpdateProductFragment
import com.example.fluperassignmet.ui.view_model.ProductViewModelFactory
import com.leopold.mvvm.ui.BindingActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BindingActivity<ActivityMainBinding>(), View.OnClickListener {
    private  val mainViewModel: ProductViewModel by viewModel()

    private var isFabOpen = false

    private var fab_open: Animation? = null
    private var fab_close: Animation? = null
    private var rotate_forward: Animation? = null
    private var rotate_backward: Animation? = null
    private var navController: NavController? = null
    private lateinit var navigationController: NavController
    override fun getLayoutResId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.mainViewModel = mainViewModel
        binding.setLifecycleOwner(this)
        setSupportActionBar(toolbar)
        navigationController = findNavController(R.id.nav_host_fragment)
        fab.setOnClickListener(this)
        fabCreateProduct.setOnClickListener(this)
        fabUpdateProduct.setOnClickListener(this)

        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);
        displayProductList()

    }

    fun displayProductList(){
        mainViewModel.products.observe(this, Observer {
            if(it.size == 0) {
                val data = mainViewModel.getAssetJsonData(applicationContext)
                if (data != null) {
                    mainViewModel.saveDataInfoLocalDataBase(data)
                }
            }else{
                val host = NavHostFragment.create(R.navigation.fragment_navigation)
                supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, host)
                    .setPrimaryNavigationFragment(host).commit()
            }
        })

    }

    override fun onResume() {
        super.onResume()


    }


    override fun onClick(v: View?) {
        if (v != null) {
            when (v.getId()) {
                R.id.fab -> animateFAB()
                R.id.fabCreateProduct -> {
                    findNavController(R.id.nav_host_fragment).navigateUp()
                }
                R.id.fabUpdateProduct -> {
                    val createProduct = Bundle().apply {
                        putBoolean("IS_CREATE_PRODUCT", true)
                    }
                    findNavController(R.id.nav_host_fragment).navigate( R.id.action_ProductListFragment_to_UpdateProductFragment,createProduct)
                }
            }
        }
    }

    fun animateFAB() {
        if (isFabOpen) {
            fab.startAnimation(rotate_backward)
            fabCreateProduct.startAnimation(fab_close)
            fabUpdateProduct.startAnimation(fab_close)
            fabCreateProduct.setClickable(false)
            fabUpdateProduct.setClickable(false)
            tvCreateProduct.visibility = View.INVISIBLE
            tvShowProduct.visibility = View.INVISIBLE
            isFabOpen = false

        } else {
            fab.startAnimation(rotate_forward)
            fabCreateProduct.startAnimation(fab_open)
            fabUpdateProduct.startAnimation(fab_open)
            fabCreateProduct.setClickable(true)
            fabUpdateProduct.setClickable(true)
            tvCreateProduct.visibility = View.VISIBLE
            tvShowProduct.visibility = View.VISIBLE
            isFabOpen = true

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        removeFragment()

    }

    private fun removeFragment() {

        val stackedFrag = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)

        if (stackedFrag is UpdateProductFragment) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, stackedFrag)
                .commit()
            supportFragmentManager.popBackStackImmediate()
            getSupportFragmentManager().beginTransaction().remove(stackedFrag).addToBackStack(null).commit();

        } else if (stackedFrag is ProductListFragment) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, stackedFrag)
                .commit()
            supportFragmentManager.popBackStack()

        }

    }




}

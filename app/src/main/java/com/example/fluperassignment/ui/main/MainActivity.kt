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


class MainActivity : BindingActivity<ActivityMainBinding>() {
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

    override fun onBackPressed() {
        super.onBackPressed()


    }

}

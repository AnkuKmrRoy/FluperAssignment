package com.example.fluperassignmet.ui.view_model

import android.content.Context
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fluperassignmet.core.BaseViewModel
import com.example.fluperassignmet.data.db.entity.Products
import com.example.fluperassignmet.data.repositories.ProductsRepositories
import com.example.fluperassignmet.utils.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class ProductViewModel(private val repository: ProductsRepositories) : BaseViewModel(), Observable {

    private var isUpdateOrDelete = false
    public lateinit var productToUpdateOrDelete: Products
    val products = repository.products

    private val _selected = MutableLiveData<Products>()
    var selected : LiveData<Products> = _selected

    fun selectedProduct(item: Products) {
        _selected.value = item
    }

    @Bindable
    val prodName = MutableLiveData<String>()
    @Bindable
    val prodDesc = MutableLiveData<String>()
    @Bindable
    val prodRegPrice = MutableLiveData<String>()
    @Bindable
    val prodSalePrice = MutableLiveData<String>()
    @Bindable
    val prodImage = MutableLiveData<String>()
    @Bindable
    val prodColor = MutableLiveData<String>()
    @Bindable
    val prodStores = MutableLiveData<String>()


    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>> get() = statusMessage


    fun saveProductData(){
        val pName:String = prodName.value!!
        val pDesc:String = prodDesc.value!!
        val pRegPr:String = prodRegPrice.value!!
        val pSalePr:String = prodSalePrice.value!!
        val pImage:String? = prodImage.value
        val pColor:String = prodColor.value!!
        val pStores:String = prodStores.value!!

        insertProduct(Products(0,pName,pDesc,pRegPr,pSalePr,pImage,pColor,pStores))

        prodName.value = null
        prodDesc.value = null
        prodRegPrice.value = null
        prodSalePrice.value = null
        prodImage.value = null
        prodColor.value = null
        prodStores.value = null
    }

    fun insertProduct(product: Products): Job =
        viewModelScope.launch {
            repository.insetProducts(product)
            statusMessage.value = Event("Product Saved Successfully")
        }

    fun updateProduct(product: Products): Job = viewModelScope.launch {
        repository.updateProducts(product)
    }

    fun deleteProduct(product: Products): Job = viewModelScope.launch {
        repository.deleteProducts(product)
        statusMessage.value = Event("Product Deleted Successfully")
    }

    fun deleteAllProduct(product: Products): Job = viewModelScope.launch {
        repository.deleteAllProducts()
    }



    fun getAssetJsonData(context: Context): String? {
        val json: String
        try {
            val inputStream = context.getAssets().open("products.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.use { it.read(buffer) }
            json = String(buffer)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        // print the data
        Log.i("data", json)
        return json
    }

    fun saveDataInfoLocalDataBase(data:String) {
        var productList:ArrayList<Products> = ArrayList();
        val jsonProducts: JSONObject = JSONObject(data)
        var jsonarrayProducts: JSONArray = jsonProducts.getJSONArray("products")
        for (i in 0..jsonarrayProducts.length() - 1) {
            var json_objectProduct: JSONObject =jsonarrayProducts.getJSONObject(i)

            productList.add(Products(0 ,
                json_objectProduct.getString("name") ,
                json_objectProduct.getString("description"),
                json_objectProduct.getString("regular_price"),
                json_objectProduct.getString("sale_price"),
                json_objectProduct.getString("product_photo"),
                json_objectProduct.getString("color"),
                json_objectProduct.getString("store")))
        }
        insertProduct(productList)

    }

    fun insertProduct(productList:List<Products>): Job =
        viewModelScope.launch {
            repository.insertProductListFromJson(productList)
        }

    fun initUpdateAndDelete(product: Products){
        /*prodName.value = product.name
        prodDesc.value = product.description
        prodRegPrice.value = product.regular_price
        prodSalePrice.value = product.sale_price
        prodColor.value = product.color_id
        prodStores.value = product.store_id
        prodImage.value = product.product_photo
        isUpdateOrDelete = true
        productToUpdateOrDelete = product*/
        //selectedProduct(product)
    }

    fun getProductById(id:Int):Job= viewModelScope.launch{
        repository.getProductUsingID(id)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}
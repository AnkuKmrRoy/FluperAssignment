package com.example.fluperassignmet.ui.view_model

import android.content.Context
import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.fluperassignment.data.db.data_model.Stores
import com.example.fluperassignmet.core.BaseViewModel
import com.example.fluperassignmet.data.db.entity.Products
import com.example.fluperassignmet.data.repositories.ProductsRepositories
import com.example.fluperassignmet.utils.Event
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.update_product_fragment.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
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
    @Bindable
    val prodStoresAddress = MutableLiveData<String>()


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
        val pStoreAddress:String = prodStoresAddress.value!!

        val storeList: MutableList<Stores>? = mutableListOf<Stores>()
        storeList?.add(0, Stores(0, pStores, pStoreAddress))

        val colorList: MutableList<String>? = mutableListOf<String>()
        colorList?.add(0,pColor)

        insertProduct(Products(0,pName,pDesc,pRegPr,pSalePr,pImage,colorList,storeList))

        prodName.value = null
        prodDesc.value = null
        prodRegPrice.value = null
        prodSalePrice.value = null
        prodImage.value = null
        prodColor.value = null
        prodStores.value = null
        prodStoresAddress.value = null
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
        /*var productList:ArrayList<Products> = ArrayList();
        val jsonProducts: JSONObject = JSONObject(data)
        var jsonarrayProducts: JSONArray = jsonProducts.getJSONArray("products")
        for (i in 0..jsonarrayProducts.length() - 1) {
            var json_objectProduct: JSONObject =jsonarrayProducts.getJSONObject(i)
            val colorList:List<String>
            productList.add(Products(0 ,
                json_objectProduct.getString("name") ,
                json_objectProduct.getString("description"),
                json_objectProduct.getString("regular_price"),
                json_objectProduct.getString("sale_price"),
                json_objectProduct.getString("product_photo"),
                ,
                json_objectProduct.getJSONArray("stores")))
        }*/
        insertProduct(fromString(data))

    }


    fun fromString(value: String): List<Products> {
        val listType = object : TypeToken<List<Products>>() {}.type
        return Gson().fromJson(value, listType)
    }

    fun insertProduct(productList:List<Products>): Job =
        viewModelScope.launch {
            repository.insertProductListFromJson(productList)
        }


    fun getProductById(id:Int):Job= viewModelScope.launch{
        repository.getProductUsingID(id)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}
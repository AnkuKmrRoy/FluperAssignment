package com.example.fluperassignmet.data.db

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.fluperassignmet.data.db.FluperAssignmentDatabase.Companion.DB_VERSION
import com.example.fluperassignmet.data.db.dao.ProductsDao
import com.example.fluperassignmet.data.db.entity.Products

@Database(entities = [Products::class], version = DB_VERSION, exportSchema = false)

abstract class FluperAssignmentDatabase :RoomDatabase(){
    abstract fun getProductsDao(): ProductsDao

    companion object {
        const val DB_VERSION = 1
        private const val DB_NAME = "fluper_assignment.db"
        @Volatile
        private var INSTANCE: FluperAssignmentDatabase? = null

        fun getInstance(context: Context?): FluperAssignmentDatabase{
            synchronized(this){
                var instance:FluperAssignmentDatabase?= INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context!!.applicationContext,
                        FluperAssignmentDatabase::class.java,
                        "fluper_assignment_database"
                    ).build()
                }
               return instance
            }

        }



        /*private fun build(context: Context?) =
            Room.databaseBuilder(context.applicationContext, FluperAssignmentDatabase::class.java, DB_NAME)
                .addMigrations(MIGRATION_1_TO_2)
                .build()

        private val MIGRATION_1_TO_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

            }
        }*/
    }

}
package com.example.guardsprotectionapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.guardsprotectionapp.models.OfferModel
import com.example.guardsprotectionapp.utils.Converters

@Database(
    entities = [OfferModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class GuardDatabase : RoomDatabase(){
    abstract fun OfferModelDao(): OfferModelDao

    companion object {
        @Volatile private var instance: GuardDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            GuardDatabase::class.java, "guardDatabase.db")
            .build()
    }
}

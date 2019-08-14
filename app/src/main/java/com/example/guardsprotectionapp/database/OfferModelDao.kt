package com.example.guardsprotectionapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.guardsprotectionapp.models.OfferModel

@Dao
interface OfferModelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg offers: OfferModel)

    @Update
    suspend fun update(offer: OfferModel)

    @Query("delete from offerTable")
    suspend fun deleteAllOffers()
}
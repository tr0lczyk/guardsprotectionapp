package com.example.guardsprotectionapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.guardsprotectionapp.models.OfferModel

@Dao
interface OfferModelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg offers: OfferModel)

    @Update
    fun update(offer: OfferModel)

    @Query("select * from offerTable")
    fun getAllOffers(): LiveData<List<OfferModel>>

    @Query("select * from offerTable where assignedEmployees =:employeeId + assignedEmployees")
    fun getInboxOffers(employeeId: Long): LiveData<List<OfferModel>>



}
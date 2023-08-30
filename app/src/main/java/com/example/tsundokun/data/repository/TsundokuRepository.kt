package com.example.tsundokun.data.repository

import com.example.tsundokun.data.local.dao.TsundokuCategoryDao
import com.example.tsundokun.data.local.dao.TsundokuDao
import com.example.tsundokun.data.local.entities.TsundokuCategoryEntity
import com.example.tsundokun.data.local.entities.TsundokuEntity
import com.example.tsundokun.data.local.entities.toDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TsundokuRepository @Inject constructor(
    private val tsundokuDao: TsundokuDao,
    private val tsundokuCategoryDao: TsundokuCategoryDao
) {

    fun observeAllTsundoku() = tsundokuDao.observeAll().map {
        it.toDomainModel()
    }

    suspend fun deleteTsundokuById(id: String) = tsundokuDao.deleteById(id)

    suspend fun addTsundoku(tsundoku: TsundokuEntity, uuid: String, categoryId: String) {
        withContext(Dispatchers.IO) {
            //最初に、tsundokuテーブルに追加
            tsundokuDao.upsert(tsundoku)
            //次に、tsundoku_categoryテーブルに追加する。supabaseとか、firebaseとかのように、
            //外部キー制約を設定できないので、ここで制約を設ける。
            //usecaseにしないのは、単に、外部キー制約を設けるためだけの処理のため。
            tsundokuCategoryDao.upsert(
                TsundokuCategoryEntity(
                    tsundokuId = uuid,
                    categoryId = categoryId
                )
            )
        }
    }
//    = tsundokuDao.upsert(tsundoku)

    suspend fun updateFavorite(id: String, isFavorite: Boolean) =
        tsundokuDao.updateFavorite(id, isFavorite)
}

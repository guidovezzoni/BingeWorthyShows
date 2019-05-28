package com.guidovezzoni.bingeworthyshows.common.repofactory

import android.util.Log
import com.guidovezzoni.architecture.cacheddatasource.MemoryCacheDataSource
import com.guidovezzoni.architecture.datasource.DataSource
import com.guidovezzoni.architecture.repository.SingleLevelCacheRepository
import io.reactivex.Single

class SingleLevelCacheDebugRepository<M, P>(networkDataSource: DataSource<M, P>, cacheDataSource: MemoryCacheDataSource<M, P>)
    : SingleLevelCacheRepository<M, P>(networkDataSource, cacheDataSource) {

    override fun get(params: P?): Single<M> {
        Log.d(TAG, "get + $params")
        return super.get(params)
    }

    override fun getLatest(params: P?): Single<M> {
        Log.d(TAG, "getLatest + $params")
        return super.getLatest(params)
    }

    companion object {
        private const val TAG = "SingleLevCacheDebugRepo"
    }
}

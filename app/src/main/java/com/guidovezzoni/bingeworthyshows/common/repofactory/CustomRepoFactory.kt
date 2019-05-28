package com.guidovezzoni.bingeworthyshows.common.repofactory

import com.guidovezzoni.architecture.repository.Repository
import com.guidovezzoni.repofactory.RepoFactory
import com.guidovezzoni.repofactory.RepoType
import io.reactivex.Single
import retrofit2.Response

class CustomRepoFactory : RepoFactory() {

    override fun <M, P> createRepo(repoType: RepoType, endPointGet: (P) -> Single<Response<M>>): Repository<M, P> {
        return if (repoType == RepoType.SINGLE_LEVEL_CACHE) {
            val networkSource = createNetworkDataSource(endPointGet)
            val cachedDataSource = createCachedSource<M, P>()
            SingleLevelCacheDebugRepository(networkSource, cachedDataSource)
        } else {
            super.createRepo(repoType, endPointGet)
        }
    }
}

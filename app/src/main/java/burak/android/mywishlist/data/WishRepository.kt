package burak.android.mywishlist.data

import kotlinx.coroutines.flow.Flow

class WishRepository(private val wishDao: WishDao) {

    suspend fun addAWish(wish: Wish){
        wishDao.addAWish(wish)
    }
    fun getActiveWishes(): Flow<List<Wish>> = wishDao.getActiveWishes()

    fun getArchivedWishes(): Flow<List<Wish>> = wishDao.getArchivedWishes()

    fun getAWishById(id:Long) : Flow<Wish> {
        return wishDao.getAWishById(id)
    }

    suspend fun updateAWish(wish: Wish){
        wishDao.updateAWish(wish)
    }

    suspend fun deleteAWish(wish: Wish){
        wishDao.deleteAWish(wish)
    }

    suspend fun archiveWish(wishId: Long){
        wishDao.archiveWish(wishId)
    }
}
package burak.android.mywishlist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WishDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addAWish(wishEntity: Wish)

    @Query("SELECT * FROM `wish-table` WHERE is_archived = 0")
    abstract fun getActiveWishes(): Flow<List<Wish>> // Non-archived wishes

    @Query("SELECT * FROM `wish-table` WHERE is_archived = 1")
    abstract fun getArchivedWishes(): Flow<List<Wish>> // Archived wishes

    @Update
    abstract suspend fun updateAWish(wishEntity: Wish)

    @Delete
    abstract suspend fun deleteAWish(wishEntity: Wish)

    @Query("UPDATE `wish-table` SET is_archived = 1 WHERE id = :wishId")
    abstract suspend fun archiveWish(wishId: Long) // Archive the determined ID

    @Query("UPDATE `wish-table` SET is_archived = 0 WHERE id = :wishId")
    abstract suspend fun unarchiveWish(wishId: Long) // Unarchive the determined ID

    @Query("Select * from `wish-table` where id=:id")
    abstract fun getAWishById(id: Long): Flow<Wish>

}
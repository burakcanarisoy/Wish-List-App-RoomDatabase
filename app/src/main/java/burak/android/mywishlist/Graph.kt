package burak.android.mywishlist

import android.content.Context
import androidx.room.Room
import burak.android.mywishlist.data.WishDatabase
import burak.android.mywishlist.data.WishRepository

object Graph { //Singleton
    lateinit var database: WishDatabase

    val wishRepository by lazy {
        WishRepository(wishDao = database.wishDao())
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context, WishDatabase::class.java, "wishlist.db").build()
    }
}
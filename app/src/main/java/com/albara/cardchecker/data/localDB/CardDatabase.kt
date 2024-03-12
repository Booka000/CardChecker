package com.albara.cardchecker.data.localDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.albara.cardchecker.data.objects.Card

@Database(
    entities = [Card::class],
    version = 1,
    exportSchema = false
)
abstract class CardDatabase : RoomDatabase() {
    abstract fun cardDao() : CardDao
    companion object{
        @Volatile
        private var INSTANCE : CardDatabase? = null

        fun getInstance(context: Context): CardDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CardDatabase::class.java,
                    "card.db"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
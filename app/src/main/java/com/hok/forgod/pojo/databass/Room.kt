package com.hok.forgod.pojo.databass

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.hok.forgod.pojo.model.*

@Database(entities = arrayOf(Reciter::class, ReadersSelected::class,Downloaded::class), version = 1, exportSchema = false)

public abstract class Room:RoomDatabase() {
    abstract fun wordDao(): DaoROOM

    companion object {
        @Volatile
        private var INSTANCE: Room? = null
        fun getDatabase(context: Context): Room {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    Room::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

package id.hudiohizari.githubuser.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.hudiohizari.githubuser.R
import id.hudiohizari.githubuser.data.model.user.detail.DetailResponse

@Database(
    entities = [
        DetailResponse::class
    ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK){
            instance
                ?: buildDatabase(
                    context
                ).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                context.getString(R.string.app_db_name)
            ).build()
    }

    abstract fun getUserDetailDao(): UserDetailDao

}
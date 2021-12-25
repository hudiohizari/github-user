package id.hudiohizari.githubuser.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.hudiohizari.githubuser.data.model.user.detail.DetailResponse

@Dao
interface UserDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DetailResponse?)

    @Query("DELETE FROM user_detail")
    suspend fun deleteAll()

    @Query("SELECT * FROM user_detail WHERE id = :id")
    suspend fun getUserDetail(id: Int?): DetailResponse?
}
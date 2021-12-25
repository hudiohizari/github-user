package id.hudiohizari.githubuser.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.hudiohizari.githubuser.data.db.AppDatabase
import id.hudiohizari.githubuser.data.db.UserDetailDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun getAppDatabase(
        @ApplicationContext
        context: Context
    ): AppDatabase {
        return AppDatabase(context)
    }

    @Provides
    @Singleton
    fun getUserDetailDao(
        appDatabase: AppDatabase
    ): UserDetailDao {
        return appDatabase.getUserDetailDao()
    }

}
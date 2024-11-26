package com.example.wallet.presentation.moduls

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.wallet.data.dao.OperationDao
import com.example.wallet.data.dataBase.AppDatabase
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private val MIGRATION_4_5 = object : Migration(4, 5) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Создайте новую таблицу с правильной структурой
            database.execSQL("""
                CREATE TABLE operations_new (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    accountID INTEGER NOT NULL,
                    category TEXT NOT NULL,
                    amount INTEGER NOT NULL,
                    percentage TEXT NOT NULL,
                    isIncome INTEGER NOT NULL,
                    iconeResId INTEGER NOT NULL,
                    description TEXT,
                    date INTEGER NOT NULL,
                    isAccount INTEGER NOT NULL
                )
            """)

            // Скопируйте данные из старой таблицы в новую
            database.execSQL("""
                INSERT INTO operations_new (id, accountID, category, amount, percentage, isIncome, iconeResId,description,date,isAccount)
                SELECT id, 0, category, amount, percentage, isIncome, iconeResId,description,date,isAccount FROM operations
            """)

            // Удалите старую таблицу
            database.execSQL("DROP TABLE operations")

            // Переименуйте новую таблицу в старое название
            database.execSQL("ALTER TABLE operations_new RENAME TO operations")
        }
    }

    private val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Создайте новую таблицу с правильной структурой
            database.execSQL("""
                CREATE TABLE operations_new (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    category TEXT NOT NULL,
                    amount INTEGER NOT NULL,
                    percentage TEXT NOT NULL,
                    isIncome INTEGER NOT NULL,
                    iconeResId INTEGER NOT NULL,
                    description TEXT,
                    date INTEGER NOT NULL,
                    isAccount INTEGER NOT NULL
                )
            """)

            // Скопируйте данные из старой таблицы в новую
            database.execSQL("""
                INSERT INTO operations_new (id, category, amount, percentage, isIncome, iconeResId,description,date,isAccount)
                SELECT id, category, amount, percentage, isIncome, iconeResId,description,date,0 FROM operations
            """)

            // Удалите старую таблицу
            database.execSQL("DROP TABLE operations")

            // Переименуйте новую таблицу в старое название
            database.execSQL("ALTER TABLE operations_new RENAME TO operations")
        }
    }


    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Создайте новую таблицу с правильной структурой
            database.execSQL("""
                CREATE TABLE operations_new (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    category TEXT NOT NULL,
                    amount INTEGER NOT NULL,
                    percentage TEXT NOT NULL,
                    isIncome INTEGER NOT NULL,
                    iconeResId INTEGER NOT NULL,
                    description TEXT,
                    date INTEGER NOT NULL
                )
            """)

            // Скопируйте данные из старой таблицы в новую
            database.execSQL("""
                INSERT INTO operations_new (id, category, amount, percentage, isIncome, iconeResId,description,date)
                SELECT id, category, amount, percentage, isIncome, iconeResId,description,0 FROM operations
            """)

            // Удалите старую таблицу
            database.execSQL("DROP TABLE operations")

            // Переименуйте новую таблицу в старое название
            database.execSQL("ALTER TABLE operations_new RENAME TO operations")
        }
    }

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Создайте новую таблицу с правильной структурой
            database.execSQL("""
                CREATE TABLE operations_new (
                    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    category TEXT NOT NULL,
                    amount INTEGER NOT NULL,
                    percentage TEXT NOT NULL,
                    isIncome INTEGER NOT NULL,
                    iconeResId INTEGER NOT NULL,
                    description TEXT
                )
            """)

            // Скопируйте данные из старой таблицы в новую
            database.execSQL("""
                INSERT INTO operations_new (id, category, amount, percentage, isIncome, iconeResId)
                SELECT id, category, amount, percentage, isIncome, iconeResId FROM operations
            """)

            // Удалите старую таблицу
            database.execSQL("DROP TABLE operations")

            // Переименуйте новую таблицу в старое название
            database.execSQL("ALTER TABLE operations_new RENAME TO operations")
        }
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "operations_database"
        )
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)  // Подключите миграцию
            .build()
    }

    @Provides
    @Singleton
    fun provideOperationDao(appDatabase: AppDatabase): OperationDao {
        return appDatabase.operationDao()
    }
}

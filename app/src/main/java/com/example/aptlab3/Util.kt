package com.example.aptlab3

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.aptlab3.PreferencesKeys.COUNTRIES_QUESTIONS
import com.example.aptlab3.PreferencesKeys.DRG_QUESTIONS
import com.example.aptlab3.PreferencesKeys.USER_NAME
import com.example.aptlab3.model.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

val imageResMap = mapOf(
    "egypt" to R.drawable.egypt,
    "latvia" to R.drawable.latvia,
)

val typesMap: Map<String, Preferences.Key<Int>> = mapOf(
    "countries" to COUNTRIES_QUESTIONS
)

const val QUESTION_COUNT = 5
const val LIST_SIZE = QUESTION_COUNT - 1
const val EMPTY_RESULT = -1

const val CORRECT = "You are right!"
const val INCORRECT = "Dummy you"
const val SCORE_ON_CORRECT = "You answered correct! Congratulation! Here your score"
const val SCORE_ON_INCORRECT = "Better luck next time"

const val REWRITE = "Rewrite previous results?"
const val REWRITE_MESSAGE = "You are going to rewrite your previous results! Are you sure? Your results last time were"

const val LAST_QUESTION = "This was last question!"

class DataStoreManager(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_data")
    suspend fun saveUserData(userData: UserData) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = userData.userName
            preferences[COUNTRIES_QUESTIONS] = userData.countriesQuestionsResult
            preferences[DRG_QUESTIONS] = userData.deepRockQuestionsResult
        }
    }
    suspend fun saveTestResult(questionsType: String, score: Int?) {
        context.dataStore.edit { preferences ->
            preferences[typesMap[questionsType] as Preferences.Key<Int>] = score ?: EMPTY_RESULT
        }
    }

    fun readTestResult(questionsType: String): Flow<Int>{
        return context.dataStore.data
            .map { preferences ->
                typesMap[questionsType]?.let { key->
                    preferences[key] ?: EMPTY_RESULT
                } ?: EMPTY_RESULT
            }
    }
    fun readUserData(): Flow<UserData> {
        return context.dataStore.data
            .map { preferences ->
                UserData(
                    userName = preferences[USER_NAME] ?: "Rookie",
                    countriesQuestionsResult = preferences[COUNTRIES_QUESTIONS] ?: EMPTY_RESULT,
                    deepRockQuestionsResult = preferences[DRG_QUESTIONS] ?: EMPTY_RESULT
                )
            }
            .catch {
                this@DataStoreManager.context.dataStore.edit { preferences ->
                    preferences.clear()
                    this@DataStoreManager.saveUserData(DefaultUser)
                }
            }
    }


}

object PreferencesKeys {
    val USER_NAME = stringPreferencesKey("user")
    val COUNTRIES_QUESTIONS = intPreferencesKey("countries")
    val DRG_QUESTIONS = intPreferencesKey("DRG")
}

val DefaultUser = UserData("Rookie", EMPTY_RESULT, EMPTY_RESULT)
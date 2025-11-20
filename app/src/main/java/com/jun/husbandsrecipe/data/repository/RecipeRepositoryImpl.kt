package com.jun.husbandsrecipe.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.jun.husbandsrecipe.domain.model.Recipe
import com.jun.husbandsrecipe.domain.repository.RecipeRepository
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class RecipeRepositoryImpl @Inject constructor(private val firestore: FirebaseFirestore) :
        RecipeRepository {

    override fun getRecipeList(): Flow<List<Recipe>> = callbackFlow {
        val collection = firestore.collection("recipes")
        val subscription =
                collection.addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    if (snapshot != null) {
                        val recipes = snapshot.toObjects(Recipe::class.java)
                        trySend(recipes)
                    }
                }
        awaitClose { subscription.remove() }
    }

    override suspend fun getRecipeDetail(recipeId: String): Recipe? {
        return try {
            val document = firestore.collection("recipes").document(recipeId).get().await()
            document.toObject(Recipe::class.java)
        } catch (e: Exception) {
            null
        }
    }
}

package com.jun.husbandsrecipe.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jun.husbandsrecipe.domain.repository.AuthRepository
import com.jun.husbandsrecipe.domain.repository.CommentRepository
import com.jun.husbandsrecipe.domain.repository.LikeRepository
import com.jun.husbandsrecipe.domain.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(): RecipeRepository {
        return com.jun.husbandsrecipe.data.repository.FakeRecipeRepository()
    }
    @Provides
    @Singleton
    fun provideCommentRepository(): CommentRepository {
        return com.jun.husbandsrecipe.data.repository.FakeCommentRepository()
    }
    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return com.jun.husbandsrecipe.data.repository.FakeAuthRepository()
    }
    @Provides
    @Singleton
    fun provideLikeRepository(): LikeRepository {
        return com.jun.husbandsrecipe.data.repository.FakeLikeRepository()
    }
    //    @Provides
    //    @Singleton
    //    fun provideRecipeRepository(firestore: FirebaseFirestore): RecipeRepository {
    //        return RecipeRepositoryImpl(firestore)
    //    }
}

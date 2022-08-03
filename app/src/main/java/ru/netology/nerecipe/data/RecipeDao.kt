package ru.netology.nerecipe.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.nerecipe.db.RecipeEntity
import ru.netology.nerecipe.recipe.Content

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipes ORDER BY id DESC")
    fun getAll(): LiveData<List<RecipeEntity>>

    @Insert
    fun insert(recipe: RecipeEntity)

    @Query(
        """
        UPDATE recipes SET
        content = :content,
        title = :title,
        category = :category,
        picture = :picture
        WHERE id = :id;
        """
    )
    fun update(id: Long, content: List<Content>, title: String, category: String, picture: String)

    fun save(recipe: RecipeEntity) =
        if (recipe.id == 0L) insert(recipe) else update(
            recipe.id,
            recipe.content,
            recipe.title,
            recipe.category,
            recipe.picture
        )

    @Query(
        """
        UPDATE recipes SET
        isFavorite = CASE WHEN isFavorite THEN 0 ELSE 1 END
        WHERE id = :id;
        """
    )
    fun favoriteById(id: Long)

    @Query("DELETE FROM recipes WHERE id = :id")
    fun removeById(id: Long)
}
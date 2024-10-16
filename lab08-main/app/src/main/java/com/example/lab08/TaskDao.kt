package com.example.lab08
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface TaskDao {
    // Obtener todas las tareas
    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<Task>

    // Insertar una nueva tarea
    @Insert
    suspend fun insertTask(task: Task)

    // Marcar una tarea como completada o no completada
    @Update
    suspend fun updateTask(task: Task)

    // Eliminar todas las tareas
    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()

    // Eliminar una tarea específica
    @Query("DELETE FROM tasks WHERE id = :taskId")
    suspend fun deleteTask(taskId: Int)

    // Obtener tareas completadas
    @Query("SELECT * FROM tasks WHERE is_completed = 1")
    suspend fun getCompletedTasks(): List<Task>

    // Obtener tareas pendientes
    @Query("SELECT * FROM tasks WHERE is_completed = 0")
    suspend fun getPendingTasks(): List<Task>

    // Buscar tareas por descripción
    @Query("SELECT * FROM tasks WHERE description LIKE '%' || :query || '%'")
    suspend fun searchTasks(query: String): List<Task>

    // Ordenar tareas por nombre
    @Query("SELECT * FROM tasks ORDER BY description ASC")
    suspend fun getTasksOrderedByName(): List<Task>

    // Ordenar tareas por estado (completada o no)
    @Query("SELECT * FROM tasks ORDER BY is_completed ASC")
    suspend fun getTasksOrderedByCompletion(): List<Task>
}
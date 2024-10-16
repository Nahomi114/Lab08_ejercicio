package com.example.lab08

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(private val dao: TaskDao) : ViewModel() {
    // Estado para la lista de tareas
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        // Al inicializar, cargamos las tareas de la base de datos
        viewModelScope.launch {
            _tasks.value = dao.getAllTasks()
        }
    }

    // Función para añadir una nueva tarea
    fun addTask(description: String, priority: String, category: String) {
        val newTask = Task(description = description, priority = priority, category = category)
        viewModelScope.launch {
            dao.insertTask(newTask)
            _tasks.value = dao.getAllTasks() // Recargamos la lista
        }
    }

    // Función para editar una tarea existente
    fun editTask(task: Task, newDescription: String, newPriority: String, newCategory: String) {
        val updatedTask = task.copy(description = newDescription, priority = newPriority, category = newCategory)
        viewModelScope.launch {
            dao.updateTask(updatedTask)
            _tasks.value = dao.getAllTasks() // Recargamos la lista
        }
    }

    // Función para eliminar una tarea específica
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            dao.deleteTask(task.id)
            _tasks.value = dao.getAllTasks() // Recargamos la lista
        }
    }

    // Función para alternar el estado de completado de una tarea
    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.copy(isCompleted = !task.isCompleted)
            dao.updateTask(updatedTask)
            _tasks.value = dao.getAllTasks() // Recargamos la lista
        }
    }

    // Función para eliminar todas las tareas
    fun deleteAllTasks() {
        viewModelScope.launch {
            dao.deleteAllTasks()
            _tasks.value = emptyList() // Vaciamos la lista en el estado
        }
    }

    // Funciones para filtrar tareas
    fun loadCompletedTasks() {
        viewModelScope.launch {
            _tasks.value = dao.getCompletedTasks()
        }
    }

    fun loadPendingTasks() {
        viewModelScope.launch {
            _tasks.value = dao.getPendingTasks()
        }
    }

    // Función para buscar tareas
    fun searchTasks(query: String) {
        viewModelScope.launch {
            _tasks.value = dao.searchTasks(query)
        }
    }

    // Funciones para ordenar tareas
    fun loadTasksOrderedByName() {
        viewModelScope.launch {
            _tasks.value = dao.getTasksOrderedByName()
        }
    }

    fun loadTasksOrderedByCompletion() {
        viewModelScope.launch {
            _tasks.value = dao.getTasksOrderedByCompletion()
        }
    }
}

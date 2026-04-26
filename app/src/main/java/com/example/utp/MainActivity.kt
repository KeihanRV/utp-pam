package com.example.utp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.utp.ui.theme.UTPTheme
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UTPTheme {
                TodoApp()
            }
        }
    }
}

@Composable
fun TodoApp() {
    val navController = rememberNavController()
    // In-memory state
    val todoList = remember { mutableStateListOf<TodoItem>() }

    fun toggleTodo(todo: TodoItem) {
        val index = todoList.indexOfFirst { it.id == todo.id }
        if (index != -1) {
            todoList[index] = todo.copy(isDone = !todo.isDone)
        }
    }

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            TodoListScreen(
                todoList = todoList,
                onAddTodo = { title, course, deadline ->
                    todoList.add(
                        TodoItem(
                            title = title,
                            courseName = course,
                            deadline = deadline
                        )
                    )
                },
                onRemoveTodo = { todo -> todoList.remove(todo) },
                onToggleDone = { todo -> toggleTodo(todo) },
                onNavigateToDetail = { id -> navController.navigate("detail/$id") },
                onInsertDummyData = {
                    todoList.addAll(DummyData.sampleTodos)
                }
            )
        }
        composable(
            route = "detail/{todoId}",
            arguments = listOf(navArgument("todoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getString("todoId")
            TodoDetailScreen(
                todoId = todoId,
                todoList = todoList,
                onToggleDone = { todo -> toggleTodo(todo) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}

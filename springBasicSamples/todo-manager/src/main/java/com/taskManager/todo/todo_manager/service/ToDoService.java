package com.taskManager.todo.todo_manager.service;

import com.sun.source.doctree.EscapeTree;
import com.taskManager.todo.todo_manager.Exception.ResourceNotFoundException;
import com.taskManager.todo.todo_manager.Exception.TodoNotFoundException;
import com.taskManager.todo.todo_manager.model.ToDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToDoService {

    //fake database
    List<ToDo> todos = new ArrayList<>();

    Logger logger = LoggerFactory.getLogger(ToDoService.class);


    public ToDo createToDo(ToDo todo){
        todos.add(todo);

        logger.info("Todos {}", todos);
        //logic to save todo in database to be done later
        return todo;
    }

    public List<ToDo> getAllTodos() {
        return todos;
    }

    public ToDo getTodoById(int id) {
        return todos.stream().filter( t -> id == (t.getId()))
                .findAny().orElseThrow(() -> new
                        TodoNotFoundException("Todo not found with id: " + id));

    }

    public ToDo updateToDo(int id, ToDo updatedTodo) {
        //find the todo by id from list of todos and update it using streams.
        return todos.stream()
                .filter(todo -> todo.getId() == id)
                .findFirst()
                .map(existingTodo -> {
                    existingTodo.setTitle(updatedTodo.getTitle());
                    existingTodo.setContent(updatedTodo.getContent());
                    existingTodo.setStatus(updatedTodo.getStatus());
                    return existingTodo;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    public void deletedById(int id) {
        ToDo todo = todos.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));
        todos.remove(todo);
    }
}

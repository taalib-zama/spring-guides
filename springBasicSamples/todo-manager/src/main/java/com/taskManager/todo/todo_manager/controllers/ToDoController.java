package com.taskManager.todo.todo_manager.controllers;


import com.taskManager.todo.todo_manager.model.ToDo;
import com.taskManager.todo.todo_manager.service.ToDoService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/todos")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    Logger logger = org.slf4j.LoggerFactory.getLogger(ToDoController.class);
    Random random = new Random();

    //create todo
    @PostMapping
    public ResponseEntity<ToDo> createToDoHandler(@RequestBody ToDo todo){
        //testing purpose
        logger .info("create a TODO request");
        int id = random.nextInt(1000);
        todo.setId(id);
        Date date = new Date();
        todo.setCreatedAt(date);
        //call service to cerate a todo
        return ResponseEntity.status(HttpStatus.CREATED).body(toDoService.createToDo(todo));
    }

    @GetMapping
    public ResponseEntity<List<ToDo>> getAllTodosHandler(){
        logger.info("get all todos request");
        return ResponseEntity.ok(toDoService.getAllTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDo> getTodoByIdHandler(@PathVariable int id){
        logger.info("get todo by id request");
        return ResponseEntity.ok(toDoService.getTodoById(id));
    }

    //update todo
    @PutMapping("update/{id}")
    public ResponseEntity<ToDo> updateTodoHandler(@PathVariable int id, @RequestBody ToDo todo){
        logger.info("update todo by id request");
        return ResponseEntity.ok(toDoService.updateToDo(id, todo));
    }

    //delete todo
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodoHandler(@PathVariable int id){
        logger.info("delete todo by id request");
        toDoService.deletedById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Todo deleted successfully");
    }


}

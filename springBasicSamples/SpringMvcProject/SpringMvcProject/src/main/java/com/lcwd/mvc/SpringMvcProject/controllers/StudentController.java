package com.lcwd.mvc.SpringMvcProject.controllers;

import com.lcwd.mvc.SpringMvcProject.models.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
@RequestMapping("/student")
public class StudentController {

    //create student
    /*
    data: required

    we are receiving data in json format
    json -->java object==> deserialization
    java object-->json==> serialization
     */

    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody List<Student> students) {

        System.out.println("Student list size " + students.size());
        System.out.println(students);

        //save :data : process
        //logic apply
//        System.out.println(data);
//        Object name = data.get("name");
//        Object empId = data.get("empId");
//        System.out.println("Name "+name);
//        System.out.println("Emp Id "+empId);


//        List<String> foods= Arrays.asList("Pizza","Burger");

//        Map<String,Object> data=new HashMap<>();
//        data.put("content",students);
//        data.put("error","No error found");
//        data.put("currentData",new Date());
//        data.put("systemInformation",System.getProperties());

//        return students.get(0);
        //we have to create student

//        String str = null;
//        System.out.println(str.length());
        Student student = students.get(0);
        student.setDept("Test");

//        ResponseEntity<Student> response = new ResponseEntity<>(student, HttpStatus.NOT_FOUND);
        ResponseEntity<Student> response=ResponseEntity.status(HttpStatus.OK).body(student);
        return response;
    }

}

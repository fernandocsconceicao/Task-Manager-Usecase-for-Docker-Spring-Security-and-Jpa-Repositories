package com.alpha7.alpha7.Test.controller;

import com.alpha7.alpha7.Test.dto.TaskCreationRequestDto;
import com.alpha7.alpha7.Test.entity.Task;
import com.alpha7.alpha7.Test.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequestMapping("/task")
@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/late")
    public ResponseEntity<List<Task>> getAllLateTask(@RequestParam String email){
        return ResponseEntity.ok().body(taskService.getAllLateTask(email));

    }
    @DeleteMapping("/delete")
    public ResponseEntity deleteTask(@RequestParam UUID id){
        taskService.deleteTask(id);
        return (ResponseEntity) ResponseEntity.accepted();
    }

    //Listar todas as tarefas de todos os estados contendo  email descrição e prazo TODO: Implementar paginação
    @GetMapping("/all")
    public ResponseEntity<List<Task>> getTasks(@RequestParam String email)  {
        return ResponseEntity.ok().body(
                taskService.getTasksByEmail(email)
        );
    }

    @PostMapping("/save")
    public String saveTasks(@RequestBody TaskCreationRequestDto taskDto) throws ParseException {
        Task task = taskService.saveTask(taskDto.toTask(taskDto));
        return task.toString();
    }

//    @GetMapping
//    public ArrayList<Task> getAllTasksPageable(){
//        return taskService.getTasks();
//    }
//    @GetMapping
//    public ArrayList<Task> getAllLateTasksPageable(){
//        return taskService.getTasks();
//    }
}

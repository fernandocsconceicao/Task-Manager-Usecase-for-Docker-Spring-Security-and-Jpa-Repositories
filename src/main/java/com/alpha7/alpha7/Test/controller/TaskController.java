package com.alpha7.alpha7.Test.controller;

import com.alpha7.alpha7.Test.dto.AdminTaskResponseDto;
import com.alpha7.alpha7.Test.dto.TaskCreationRequestDto;
import com.alpha7.alpha7.Test.dto.TaskResponseDto;
import com.alpha7.alpha7.Test.entity.Task;
import com.alpha7.alpha7.Test.enums.TaskStatus;
import com.alpha7.alpha7.Test.security.service.UserServiceImp;
import com.alpha7.alpha7.Test.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.alpha7.alpha7.Test.enums.TaskStatus.LATE;
import static com.alpha7.alpha7.Test.enums.TaskStatus.TODO;

@Slf4j
@RequestMapping("/task")
@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserServiceImp userService;

    @GetMapping("/todo")
    public ResponseEntity<List<TaskResponseDto>> getAllToDoTasks(@RequestParam(value = "page", defaultValue = "0") Long page, HttpServletRequest request) {
        String email = userService.getEmailFromRequestServlet(request);
        return ResponseEntity.ok().body(taskService.getAllToDoTasks(email,page));
    }
    @GetMapping("/admin/alltodo")
    public ResponseEntity<List<AdminTaskResponseDto>> getAllUserToDoTasks(@RequestParam(value = "page", defaultValue = "0") Long page) {
        return ResponseEntity.ok().body(taskService.getAllUsersToDoTasks(page));
    }
    @GetMapping("/admin/alllate")
    public ResponseEntity<List<AdminTaskResponseDto>> getAllUserLateTasks(@RequestParam(value = "page", defaultValue = "0") Long page) {
        return ResponseEntity.ok().body(taskService.getAllUsersLateTasks(page));
    }


    @GetMapping("/late")
    public ResponseEntity<List<Task>> getAllLateTask(@RequestParam(value = "page", defaultValue = "0") Long page,HttpServletRequest request) {
        String email = userService.getEmailFromRequestServlet(request);
        return ResponseEntity.ok().body(taskService.getAllLateTasks(email,page));
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteTask(@RequestBody Long id) {
        taskService.deleteTask(id);
        return (ResponseEntity) ResponseEntity.accepted();
    }

    //Listar todas as tarefas de todos os estados contendo  email descrição e prazo
    @GetMapping("/all")
    public ResponseEntity<List<Task>> getTasks(@RequestParam(value = "page", defaultValue = "0") Long page,HttpServletRequest request) {
        String email = userService.getEmailFromRequestServlet(request);
        return ResponseEntity.ok().body(
                taskService.getTasksByEmail(email,page)
        );
    }

    @PostMapping("/save")
    public String saveTasks(@RequestBody TaskCreationRequestDto taskDto, HttpServletRequest request) throws Exception {
        String email = userService.getEmailFromRequestServlet(request);
        if (email != null) {
            Task task = taskService.saveTask(taskDto.toTask(taskDto, email));
            return task.toString();

        }
        throw new Exception("Error to get email");
    }

    @PutMapping("/edit")
    public ResponseEntity<Task> edit(@RequestBody Task task, HttpServletRequest request) throws Exception {
        String email = userService.getEmailFromRequestServlet(request);
        if (email != null) {
            return ResponseEntity.ok().body(taskService.editTask(task));

        }
        throw new Exception("Error to get email");
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

package com.alpha7.alpha7.Test.service;

import com.alpha7.alpha7.Test.dto.AdminTaskResponseDto;
import com.alpha7.alpha7.Test.dto.TaskResponseDto;
import com.alpha7.alpha7.Test.entity.Task;
import com.alpha7.alpha7.Test.enums.TaskStatus;
import com.alpha7.alpha7.Test.repository.TaskRepository;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.alpha7.alpha7.Test.enums.TaskStatus.LATE;
import static com.alpha7.alpha7.Test.enums.TaskStatus.TODO;

@Service
@Slf4j
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getTasksByEmail(@RequestParam String email, Long page) {
        Pageable pageable = PageRequest.of(page.intValue(),10);
        return taskRepository.findByEmail(email,pageable);
    }

    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllLateTasks(String email, Long page) {
        Pageable pageable = PageRequest.of(page.intValue(),10);
        List<Task> list = taskRepository.findByEmail(email,pageable);
        return filter(LATE, list);
    }

    public List<TaskResponseDto> getAllToDoTasks(String email, Long page) {
        Pageable pageable = PageRequest.of(page.intValue(),10);
        List<Task> list = taskRepository.findByEmail(email,pageable);
        return buildTaskResponseDto(list);

    }

    public List<AdminTaskResponseDto> getAllUsersToDoTasks(Long page) {
        Pageable pageable = PageRequest.of(page.intValue(),10);
        Page<Task> data = taskRepository.findAll(pageable);
        List<AdminTaskResponseDto> responselist = new ArrayList<>();
        for (Task task: data.toList()) {
            responselist.add(new AdminTaskResponseDto(task,task.getEmail(),task.getDescription(),task.getDueDate()));
        }
        log.info(String.valueOf(responselist.size()));
        return responselist;


    }

    public List<AdminTaskResponseDto> getAllUsersLateTasks(Long page) {
        Pageable pageable = PageRequest.of(page.intValue(),10);
        Page<Task> data = taskRepository.findAll(pageable);
        List<AdminTaskResponseDto> responselist = new ArrayList<>();
        for (Task task: data.toList()) {
            if(task.getDueDate().before(new Date()))
                responselist.add(new AdminTaskResponseDto(task,task.getEmail(),task.getDescription(),task.getDueDate()));
        }
        log.info(String.valueOf(responselist.size()));
        return responselist;
    }

    private List<TaskResponseDto> buildTaskResponseDto(List<Task> list) {
        List<TaskResponseDto> responselist = new ArrayList<>();
        for (Task task : list) {
            if (task.getDueDate().before(new Date())) {
                responselist.add(new TaskResponseDto(task, true));
            } else {

                responselist.add(new TaskResponseDto(task, false));
            }
        }
        log.info(String.valueOf(list.size()));
        return responselist;
    }


    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Task editTask(Task task) throws Exception {
        Optional<Task> taskOptional = taskRepository.findById(task.getId());
        if (taskOptional.isPresent()) {
            task.setLastEditionDate(new Date());
            return taskRepository.save(task);
        } else {
            throw new Exception("Task id Not found");
        }
    }

    private List<Task> filter(TaskStatus taskStatus, List<Task> tasks) {
        if (taskStatus == TODO) {
            List<Task> filteredTasks = tasks.stream().filter(t -> t.getDueDate().after(new Date())).collect(Collectors.toList());
            return filteredTasks;
        }
        if (taskStatus == LATE) {
            List<Task> filteredTasks = tasks.stream().filter(t -> t.getDueDate().before(new Date())).collect(Collectors.toList());
            return filteredTasks;
        }

        return null;
    }
}

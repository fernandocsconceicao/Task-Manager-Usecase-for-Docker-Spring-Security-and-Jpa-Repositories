package com.alpha7.alpha7.Test.service;

import com.alpha7.alpha7.Test.entity.Task;
import com.alpha7.alpha7.Test.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getTasksByEmail(@RequestParam String email) {
        return taskRepository.findByEmail(email);
    }
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllLateTask(String email) {
        List<Task> list= taskRepository.findByEmail(email);
        ArrayList<Task> filteredList= new ArrayList<>();

        for (Task t : list) {
            if(t.getDueDate().before(new Date()))
                filteredList.add(t);
        }
        return filteredList;

    }

    public void deleteTask( Long id) {
        taskRepository.deleteById(id);
    }

    public Task editTask(Task task) throws Exception {
        Optional<Task> taskOptional = taskRepository.findById(task.getId());
        if(taskOptional.isPresent()){
           return  taskRepository.save(task);
        }
        else{
            throw new Exception("Task id Not found");
        }
    }
}

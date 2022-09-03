package com.alpha7.alpha7.Test.service;

import com.alpha7.alpha7.Test.entity.Task;
import com.alpha7.alpha7.Test.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }
}

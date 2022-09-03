package com.alpha7.alpha7.Test.dto;

import com.alpha7.alpha7.Test.entity.Task;
import com.alpha7.alpha7.Test.enums.TaskStatus;
import lombok.AllArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
public class TaskCreationRequestDto {
    private String email;
    private String description;
    private String dueDate;

    public Task toTask(TaskCreationRequestDto dto) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return new Task(
                null,
                new Date(),
                sdf.parse(dto.dueDate),
                dto.email,
                dto.description,
                TaskStatus.TODO,
                new Date());
    }

}

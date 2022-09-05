package com.alpha7.alpha7.Test.dto;

import com.alpha7.alpha7.Test.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class AdminTaskResponseDto {
    private Task task;
    private String email;
    private String description;
    private Date dueDate;

}

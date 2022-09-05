package com.alpha7.alpha7.Test.dto;

import com.alpha7.alpha7.Test.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskResponseDto {
    private Task task;
    private Boolean late;

}

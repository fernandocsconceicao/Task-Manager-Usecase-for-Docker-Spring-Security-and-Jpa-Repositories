package com.alpha7.alpha7.Test.entity;

import com.alpha7.alpha7.Test.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task implements Serializable {
    @Id
    @GeneratedValue
    private UUID id;
    private Date createDate;
    private Date dueDate;
    private String email;
    private String description;
    private TaskStatus status;
    private Date lastEditionDate;


}

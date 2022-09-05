package com.alpha7.alpha7.Test.repository;

import com.alpha7.alpha7.Test.entity.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, Long> {
     List<Task> findByEmail(String email, Pageable pageable);

}

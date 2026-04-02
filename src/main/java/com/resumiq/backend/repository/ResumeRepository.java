package com.resumiq.backend.repository;

import com.resumiq.backend.model.User;
import com.resumiq.backend.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findByUser(User user);
    List<Resume> findByUserId(Long userId);
}

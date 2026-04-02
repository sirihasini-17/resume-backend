package com.resumiq.backend.service;

import com.resumiq.backend.model.*;
import com.resumiq.backend.repository.ResumeRepository;
import com.resumiq.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    public List<Resume> getAllResumes() {
        return resumeRepository.findByUser(getCurrentUser());
    }

    public Resume getResumeById(Long id) {
        Resume resume = resumeRepository.findById(id).orElseThrow();
        if (!resume.getUser().getId().equals(getCurrentUser().getId())) {
            throw new RuntimeException("Unauthorized access to resume");
        }
        return resume;
    }

    @Transactional
    public Resume saveResume(Resume resume) {
        User user = getCurrentUser();
        resume.setUser(user);
        
        // Link children entities to the parent resume
        if (resume.getExperiences() != null) {
            resume.getExperiences().forEach(exp -> exp.setResume(resume));
        }
        if (resume.getEducations() != null) {
            resume.getEducations().forEach(edu -> edu.setResume(resume));
        }
        if (resume.getSkills() != null) {
            resume.getSkills().forEach(skill -> skill.setResume(resume));
        }
        if (resume.getProjects() != null) {
            resume.getProjects().forEach(project -> project.setResume(resume));
        }
        if (resume.getCertificates() != null) {
            resume.getCertificates().forEach(c -> c.setResume(resume));
        }
        if (resume.getAchievements() != null) {
            resume.getAchievements().forEach(a -> a.setResume(resume));
        }
        if (resume.getLanguages() != null) {
            resume.getLanguages().forEach(l -> l.setResume(resume));
        }
        if (resume.getInterests() != null) {
            resume.getInterests().forEach(i -> i.setResume(resume));
        }
        if (resume.getCustomSections() != null) {
            resume.getCustomSections().forEach(section -> section.setResume(resume));
        }
        
        return resumeRepository.save(resume);
    }

    @Transactional
    public void deleteResume(Long id) {
        Resume resume = getResumeById(id);
        resumeRepository.delete(resume);
    }
}

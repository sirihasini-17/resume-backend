package com.resumiq.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Experience {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String company;
    private String role;
    private String startDate;
    private String endDate;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToOne @JoinColumn(name = "resume_id") @JsonIgnore
    private Resume resume;
}

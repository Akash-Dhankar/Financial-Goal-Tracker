package com.example.financialGoal.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "goals")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goal {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private Double targetAmount;
    private Double savedAmount;

    @ManyToOne
    @JsonIgnoreProperties("goals")
    private User user;
}

package com.example.financialGoal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalDTO {
    private Long id;
    private String title;
    private Double targetAmount;
    private Double savedAmount;
    private String username;
}


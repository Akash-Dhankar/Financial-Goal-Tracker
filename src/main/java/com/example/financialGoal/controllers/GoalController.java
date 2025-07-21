package com.example.financialGoal.controllers;

import com.example.financialGoal.models.Goal;
import com.example.financialGoal.services.GoalService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<Goal> getGoals(Authentication auth) {
        return goalService.getGoalsByUsername(auth.getName());
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public Goal createGoal(@RequestBody Goal goal, Authentication auth) {
        return goalService.createGoal(goal, auth.getName());
    }

    @PutMapping("/{id}")
    public Goal updateGoal(@PathVariable Long id, @RequestBody Goal goal) {
        return goalService.updateGoal(id, goal);
    }

    @DeleteMapping("/{id}")
    public void deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(id);
    }
}


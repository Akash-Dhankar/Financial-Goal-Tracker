package com.example.financialGoal.services;

import com.example.financialGoal.models.Goal;
import com.example.financialGoal.models.User;
import com.example.financialGoal.repositories.GoalRepository;
import com.example.financialGoal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Goal> getGoalsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new RuntimeException("User not found: " + username));
        return goalRepository.findByUser(user);
    }

    public Goal createGoal(Goal goal, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new RuntimeException("User not found: " + username));
        goal.setUser(user);
        return goalRepository.save(goal);
    }

    public Goal updateGoal(Long id, Goal updatedGoal) {
        Goal existingGoal = goalRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Goal not found with id: " + id));
        existingGoal.setTitle(updatedGoal.getTitle());
        existingGoal.setTargetAmount(updatedGoal.getTargetAmount());
        existingGoal.setSavedAmount(updatedGoal.getSavedAmount());
        return goalRepository.save(existingGoal);
    }
    public void deleteGoal(Long id) {
        goalRepository.deleteById(id);
    }
}

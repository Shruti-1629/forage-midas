package com.jpmc.midascore.controller;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    @Autowired
    private UserRecordRepository userRecordRepository;

    @GetMapping("/balance")
    public Balance getBalance(@RequestParam Long userId) {
        System.out.println("Received request for userId: " + userId);
        Optional<UserRecord> user = userRecordRepository.findById(userId);

        if (user.isPresent()) {
            Balance userBalance = user.get().getBalance();
            System.out.println("Found user, balance = " + userBalance.getAmount());
            return userBalance; // ✅ returning Balance directly
        } else {
            System.out.println("User not found, returning 0");
            return new Balance(BigDecimal.ZERO);
        }
    }
}

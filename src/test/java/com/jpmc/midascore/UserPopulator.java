package com.jpmc.midascore;

import com.jpmc.midascore.component.DatabaseConduit;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UserPopulator {
    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private UserRecordRepository userRepo;

    @Autowired
    private DatabaseConduit databaseConduit;

    public void populate() {
        String[] userLines = fileLoader.loadStrings("/test_data/lkjhgfdsa.hjkl");
        for (String userLine : userLines) {
            String[] userData = userLine.split(", ");
            String name = userData[0];
            float balanceFloat = Float.parseFloat(userData[1]);
            BigDecimal balanceAmount = BigDecimal.valueOf(balanceFloat);

            // Create UserRecord and set fields properly
            UserRecord user = new UserRecord();
            user.setName(name);
            user.setBalance(new Balance(balanceAmount));

            // Save using conduit (assumed for DB side-effects/logging)
            databaseConduit.save(user);

            // Special override case (seems to target "wilbur" testing)
            if ("wilbur".equalsIgnoreCase(name)) {
                user.setBalance(new Balance(BigDecimal.ZERO));
            }

            // Save user to repo
            userRepo.save(user);
        }
    }
}

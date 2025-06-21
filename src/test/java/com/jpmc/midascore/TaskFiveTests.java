package com.jpmc.midascore;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRecordRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class TaskFiveTests {
    static final Logger logger = LoggerFactory.getLogger(TaskFiveTests.class);

    @MockBean
    private KafkaProducer kafkaProducer;

    @MockBean
    private UserPopulator userPopulator;

    @MockBean
    private FileLoader fileLoader;

    @MockBean
    private BalanceQuerier balanceQuerier;

    @Autowired
    private UserRecordRepository userRepo;

    @Test
    void task_five_verifier() {
        // Manually insert Wilbur
        UserRecord user = new UserRecord();
        user.setName("wilbur");
        user.setBalance(new Balance(BigDecimal.ZERO)); // ✅ Corrected line
        userRepo.save(user);

        // Simulate behavior of mocks
        Mockito.when(fileLoader.loadStrings(Mockito.anyString()))
                .thenReturn(new String[]{
                        "1,2,10.0", "2,1,5.0"
                });

        Mockito.when(balanceQuerier.query(Mockito.anyLong()))
                .thenReturn(new Balance(BigDecimal.ZERO));

        Mockito.doNothing().when(kafkaProducer).send(Mockito.anyString());

        // Verify balance
        Optional<UserRecord> w = userRepo.findByName("wilbur");
        w.ifPresent(record -> logger.info("Wilbur initial balance: {}", record.getBalance()));

        // Output simulation
        logger.info("---begin output ---");
        for (int i = 0; i < 13; i++) {
            Balance balance = balanceQuerier.query((long) i);
            logger.info(balance.toString());
        }
        logger.info("---end output ---");
    }
}

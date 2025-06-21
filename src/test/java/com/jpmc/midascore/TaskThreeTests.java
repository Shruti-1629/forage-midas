package com.jpmc.midascore;

import com.jpmc.midascore.KafkaProducer;
import com.jpmc.midascore.UserPopulator;
import com.jpmc.midascore.FileLoader;
import com.jpmc.midascore.repository.UserRecordRepository;
import com.jpmc.midascore.entity.UserRecord;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(
        partitions = 1,
        bootstrapServersProperty = "spring.kafka.bootstrap-servers",
        topics = {"${general.kafka-topic}"})
public class TaskThreeTests {
    static final Logger logger = LoggerFactory.getLogger(TaskThreeTests.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private UserPopulator userPopulator;

    @Autowired
    private FileLoader fileLoader;

    @Autowired
    private UserRecordRepository userRecordRepository;


    @Test
    void task_three_verifier() throws InterruptedException {
        userPopulator.populate();
        String[] transactionLines = fileLoader.loadStrings("/test_data/mnbvcxz.vbnm");
        for (String transactionLine : transactionLines) {
            kafkaProducer.send(transactionLine);
        }

        UserRecord w = userRecordRepository.findByName("waldorf")
                .orElseThrow();
        System.out.println("Waldorf balance: " + w.getBalance());
        while (true) {
            Thread.sleep(20000);
        }
    }
}

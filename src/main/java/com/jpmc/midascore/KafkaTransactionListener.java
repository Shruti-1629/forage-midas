package com.jpmc.midascore;

import com.jpmc.midascore.entity.IncentiveRecord;
import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.IncentiveRecordRepository;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class KafkaTransactionListener {

    @Autowired
    private UserRecordRepository users;

    @Autowired
    private TransactionRecordRepository transactions;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IncentiveRecordRepository incentives;

    public KafkaTransactionListener(UserRecordRepository users,
                                    TransactionRecordRepository transactions) {
        this.users = users;
        this.transactions = transactions;
    }

    @KafkaListener(id = "midasCoreListener", topics = "${general.kafka-topic}")
    @Transactional
    public void onMessage(Transaction tx) {
        Optional<UserRecord> senderOpt = users.findById(tx.getSenderId());
        Optional<UserRecord> recipientOpt = users.findById(tx.getRecipientId());

        if (senderOpt.isPresent() && recipientOpt.isPresent()) {
            UserRecord sender = senderOpt.get();
            UserRecord recipient = recipientOpt.get();

            BigDecimal amt = BigDecimal.valueOf(tx.getAmount());

            BigDecimal senderBal = sender.getBalance().getAmount();
            BigDecimal recipientBal = recipient.getBalance().getAmount();

            if (senderBal.compareTo(amt) >= 0) {
                // Update sender and recipient balances
                sender.getBalance().setAmount(senderBal.subtract(amt));
                recipient.getBalance().setAmount(recipientBal.add(amt));

                users.save(sender);
                users.save(recipient);

                // Log transaction
                TransactionRecord rec = new TransactionRecord();
                rec.setAmount(amt);
                rec.setSender(sender);
                rec.setRecipient(recipient);
                transactions.save(rec);

                // Call incentive service
                IncentiveRecord response = restTemplate.postForObject(
                        "http://localhost:8080/incentive", tx, IncentiveRecord.class);

                if (response != null && response.getIncentiveAmount() != null) {
                    BigDecimal incentiveAmt = response.getIncentiveAmount();
                    recipient.getBalance().setAmount(
                            recipient.getBalance().getAmount().add(incentiveAmt)
                    );
                    users.save(recipient);

                    // Save incentive record
                    IncentiveRecord incentiverec = new IncentiveRecord();
                    incentiverec.setTransactionAmount(amt);
                    incentiverec.setIncentiveAmount(incentiveAmt);
                    incentiverec.setUser(recipient);
                    incentives.save(incentiverec);
                }
            }
        }
    }
}

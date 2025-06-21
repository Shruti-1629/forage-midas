JPMorgan Virtual Internship Project - Midas Core

This repository contains the completed implementation of the JPMorgan Chase Software Engineering Virtual Internship (Forage).

## ✅ Tasks Completed

1. **Kafka Integration** - Consumed real-time transaction data using a Kafka listener.
2. **H2 Database Integration** - Stored validated transactions using JPA and Spring Data.
3. **Transaction Validation** - Ensured sender and recipient were valid with sufficient balance.
4. **Incentive API Integration** - Fetched additional incentives via REST API and recorded them.
5. **Balance API** - Created a REST endpoint `/balance` to check user's current balance.

## ⚙️ Technologies Used

- Java 17
- Spring Boot
- Kafka
- REST API (Spring Web)
- H2 Database
- JPA & Hibernate
- Maven

## 🌐 How to Run

1. Clone the repo
2. Start the incentive API (`transaction-incentive-api.jar`)
3. Run the Spring Boot app on port `33400`
4. Test using Kafka producer + GET `/balance?userId=...`

## 📃 Certificate

Successfully completed via [Forage - JPMorgan Chase Software Engineering Internship](https://www.theforage.com/)

---





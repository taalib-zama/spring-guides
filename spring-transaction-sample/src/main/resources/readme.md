Source : https://medium.com/@bubu.tripathy/implementing-transactions-in-a-spring-boot-application-bc6b33e88557


curl to test transfer api : curl -X POST http://localhost:8080/transfer \
-d "fromAccountId=1" \
-d "toAccountId=2" \
-d "amount=100.50" \
-H "Content-Type: application/x-www-form-urlencoded"


get account balances : curl -X GET "http://localhost:8080/accounts/balance?accountIds=1,2,3"




NOTE : In the method name findByIdIn(List<Long> ids), the keyword In is a Spring Data JPA query keyword. It tells Spring to generate a SQL IN clause under the hood.

🔍 Meaning of In:
findByIdIn(List<Long> ids) means:

“Find all Account records where the id is in the given list of IDs.”

🧾 Equivalent SQL:
sql
Copy
Edit
SELECT * FROM account WHERE id IN (1, 2, 3);
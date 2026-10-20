\# Customer CRUD API Testing Report



\## Project

\*\*Subscription Billing System\*\*



Technologies: Java 11, Spring Boot, Spring Data JPA, PostgreSQL, REST APIs, Swagger UI.



\## Objective

To verify that the Customer REST API can create, retrieve, update, and delete customer records and that the changes are reflected in PostgreSQL.



\## API Tests Performed



\### 1. Get All Customers

\- \*\*Method:\*\* GET

\- \*\*Endpoint:\*\* `/api/customers`

\- \*\*Result:\*\* PASSED

\- \*\*Verification:\*\* Retrieved customer records successfully.



\### 2. Get Customer by ID

\- \*\*Method:\*\* GET

\- \*\*Endpoint:\*\* `/api/customers/2`

\- \*\*Result:\*\* PASSED

\- \*\*Verification:\*\* Received HTTP 200 OK and the expected customer details.



\### 3. Create Customer

\- \*\*Method:\*\* POST

\- \*\*Endpoint:\*\* `/api/customers`

\- \*\*Request body:\*\*

```json

{

&#x20; "name": "Test Customer",

&#x20; "email": "test.customer@example.com",

&#x20; "phone": "9876543210"

}

```

\- \*\*Result:\*\* PASSED

\- \*\*Verification:\*\* Created customer record with ID 6 and confirmed it in PostgreSQL.



\### 4. Update Customer

\- \*\*Method:\*\* PUT

\- \*\*Endpoint:\*\* `/api/customers/6`

\- \*\*Request body:\*\*

```json

{

&#x20; "name": "Test Customer Updated",

&#x20; "email": "test.customer@example.com",

&#x20; "phone": "9876543211"

}

```

\- \*\*Result:\*\* PASSED

\- \*\*Verification:\*\* Confirmed the updated name and phone number in PostgreSQL.



\### 5. Delete Customer

\- \*\*Method:\*\* DELETE

\- \*\*Endpoint:\*\* `/api/customers/6`

\- \*\*Result:\*\* PASSED

\- \*\*Verification:\*\* Confirmed that the deleted customer record no longer existed in PostgreSQL.



\## Summary

All five customer API checks listed above were completed successfully. The create, update, and delete operations were verified against the PostgreSQL database.



\## Skills Demonstrated

\- Java and Spring Boot REST API development

\- HTTP


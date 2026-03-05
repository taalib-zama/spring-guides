User Controller Test Suite Documentation
Test Architecture
Unit Tests (UserControllerTest)
Purpose: Test controller logic in isolation by mocking service dependencies.

Framework: JUnit 5 + Mockito + AssertJ

Key Annotations:

@ExtendWith(MockitoExtension.class) - Enables Mockito mocking

@Mock - Creates mock UserService
i HihI
@InjectMocks - Injects mocks into UserController

Test Cases Breakdown
1. Create User Test
   @Test
   @DisplayName("Should create user successfully")
   void shouldCreateUserSuccessfully()

Copy

Insert at cursor
What it tests:

POST /api/v1/users endpoint

Valid UserDTO input handling

HTTP 201 CREATED response

Service method invocation

Assertions:

Response status = 201 CREATED

Response body contains created user

Service.createUser() called once

2. Update User Test
   @Test
   @DisplayName("Should update user successfully")
   void shouldUpdateUserSuccessfully()

Copy

Insert at cursor
java
What it tests:

PUT /api/v1/users/{userId} endpoint

Path variable extraction

Request body validation

HTTP 200 OK response

Assertions:

Response status = 200 OK

Updated user returned

Service.updateUser() called with correct parameters

3. Delete User Test
   @Test
   @DisplayName("Should delete user successfully")
   void shouldDeleteUserSuccessfully()

Copy

Insert at cursor
java
What it tests:

DELETE /api/v1/users/{userId} endpoint

Void return type handling

HTTP 204 NO CONTENT status

Assertions:

Service.deleteUser() called once

No response body validation needed

4. Get All Users Test
   @Test
   @DisplayName("Should get all users with pagination")
   void shouldGetAllUsersWithPagination()

Copy

Insert at cursor
java
What it tests:

GET /api/v1/users endpoint

Pageable parameter handling

Page response type

Query parameter conversion

Assertions:

Response status = 200 OK

Page object returned correctly

Pagination parameters passed to service

5. Get User By ID Test
   @Test
   @DisplayName("Should get user by ID successfully")
   void shouldGetUserByIdSuccessfully()

Copy

Insert at cursor
What it tests:

GET /api/v1/users/{userId} endpoint

Path variable binding

Single user retrieval

Assertions:

Response status = 200 OK

Correct user returned

Service called with right userId

6. Search Users Test
   @Test
   @DisplayName("Should search users by keyword")
   void shouldSearchUsersByKeyword()

Copy

Insert at cursor
What it tests:

GET /api/v1/users/search?keyword= endpoint

Query parameter extraction

List response handling

Assertions:

Response status = 200 OK

Search results returned as list

Keyword passed to service correctly

7. Get User By Email Test
   @Test
   @DisplayName("Should get user by email successfully")
   void shouldGetUserByEmailSuccessfully()

Copy

Insert at cursor
What it tests:

GET /api/v1/users/email?email= endpoint

Email query parameter handling

Single user lookup by email

Assertions:

Response status = 200 OK

User found by email

Service method called with email parameter

Integration Test (UserControllerIntegrationTest)
Full Stack Test
@Test
@DisplayName("Should create user via REST API")
void shouldCreateUserViaRestApi()

Copy

Insert at cursor
java
What it tests:

Complete HTTP request/response cycle

Spring Boot application context loading

Real REST endpoint invocation

JSON serialization/deserialization

Key Components:

TestRestTemplate - Makes actual HTTP calls

@MockitoBean - Mocks service layer

Real Spring MVC processing

Test Data Setup
@BeforeEach Method
@BeforeEach
void setUp() {
userDTO = UserDTO.builder()
.userId("123")
.name("John Doe")
.email("john@example.com")
.build();

    userPage = new PageImpl<>(List.of(userDTO));
}

Copy

Insert at cursor
java
Purpose: Creates consistent test data for all test methods

Testing Patterns Used
1. Given-When-Then Structure
   // Given - Setup test data and mocks
   when(userService.createUser(userDTO)).thenReturn(userDTO);

// When - Execute the method under test
ResponseEntity<UserDTO> response = userController.createUser(userDTO);

// Then - Verify results
assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

Copy

Insert at cursor
java
2. Nested Test Classes
   @Nested
   @DisplayName("Create User")
   class CreateUser {
   // Related tests grouped together
   }

Copy

Insert at cursor
java
Benefits: Better organization, shared setup, clear test grouping

3. Multiple Assertions with assertAll()
   assertAll(
   () -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK),
   () -> assertThat(response.getBody()).isEqualTo(userDTO),
   () -> verify(userService).getUserById(userId)
   );

Copy

Insert at cursor
java
Benefits: All assertions run even if one fails, better error reporting

Mock Verification Patterns
Service Method Calls
verify(userService).createUser(userDTO);           // Exact parameter match
verify(userService, times(1)).deleteUser(userId);  // Verify call count
doNothing().when(userService).deleteUser(userId);  // Mock void methods

Copy

Insert at cursor
java
Response Validation
assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
assertThat(response.getBody()).isEqualTo(expectedUser);
assertThat(response.getBody()).isNotNull();

Copy

Insert at cursor
java
Coverage Metrics
Endpoints Covered: 7/7 (100%)

HTTP Methods: GET, POST, PUT, DELETE

Response Types: Single objects, Lists, Pages

Parameter Types: Path variables, Query parameters, Request body

Best Practices Implemented
Isolation: Each test is independent

Descriptive Names: Clear test method names

Single Responsibility: One assertion per test concept

Mock Verification: Ensures service layer interaction

Modern Annotations: Uses latest Spring Boot testing features

Readable Structure: Nested classes and display names
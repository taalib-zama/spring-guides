# ToDo Application - Functional Requirements Document (FRD)

1. add todo
2. get todo
3. get single todo
4. update todo
5. delete todo
6. mark todo as completed
7. get all completed todos
8. get all pending todos


Single todo object : 
{
    title: string,
    content: string,
    status: boolean
}




# ToDo Application - Technical Design

The apI's:
1. POST /todos - Add a new todo item
   - Request Body: { title: string, content: string }
   - Response: 201 Created, { id: string, title: string, content: string, status: boolean }
   - Description: This endpoint allows users to create a new todo item. The request body must include the title and content of the todo. The response will return the created todo item with a unique ID and a default status of false (not completed).
   - Validation: Ensure title and content are not empty.
   - Error Handling: Return 400 Bad Request for invalid input.
   - Logging: Log the creation of a new todo item with its ID.
   - Security: Ensure the endpoint is protected against CSRF attacks.
   - Testing: Unit tests for input validation and integration tests for the endpoint.
   - Documentation: Update API documentation with request and response examples.
   - Performance: Monitor response times and optimize database queries if necessary.
   - Scalability: Design the endpoint to handle a high volume of requests.

2. GET /todos - Retrieve all todo items
   - Response: 200 OK, [{ id: string, title: string, content: string, status: boolean }]
   - Description: This endpoint retrieves all todo items from the database.
   - Error Handling: Return 500 Internal Server Error for database issues.
   - Logging: Log the retrieval of todo items.
   - Security: Ensure the endpoint is protected against unauthorized access.
   - Testing: Integration tests to verify the retrieval of todo items.
   - Documentation: Update API documentation with response examples.

3. GET /todos/{id} - Retrieve a single todo item by ID
   - Response: 200 OK, { id: string, title: string, content: string, status: boolean }
   - Description: This endpoint retrieves a specific todo item by its unique ID.
   - Error Handling: Return 404 Not Found if the todo item does not exist.
   - Logging: Log the retrieval of the specific todo item with its ID.
   - Security: Ensure the endpoint is protected against unauthorized access.
   - Testing: Integration tests to verify the retrieval of a specific todo item.
   - Documentation: Update API documentation with request and response examples.
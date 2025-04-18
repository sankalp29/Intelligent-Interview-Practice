# What is the data to be stored?

User Details: Username, Password, Signup Date, Number of tests attended

Interview Details: Questions asked, Code written for each question, Feedback and rating for each code.

## SQL vs NoSQL

Let's look at how the data would be stored in both the scenarios.

### SQL:

**Users Table**
| Column Name             | Data Type        | Constraints                | Description                                  |
|-------------------------|------------------|----------------------------|----------------------------------------------|
| `UserID`                | INT              | PRIMARY KEY AUTO_INCREMENT | Unique identifier for each user              |
| `Username`              | VARCHAR(255)     | UNIQUE NOT NULL            | User's chosen username                       |
| `Password`              | VARCHAR(255)     | NOT NULL                   | Hashed password for security                 |
| `SignupDate`            | TIMESTAMP        | DEFAULT CURRENT_TIMESTAMP  | Date and time when the user signed up        |
| `NumberOfTestsAttended` | INT              | DEFAULT 0                  | Count of tests the user has completed        |


**Interviews Table**
| Column Name        | Data Type         | Constraints                                  | Description                                                  |
|--------------------|-------------------|----------------------------------------------|--------------------------------------------------------------|
| `InterviewID`      | INT               | PRIMARY KEY AUTO_INCREMENT                   | Unique identifier for each interview                         |
| `UserID`           | INT               | FOREIGN KEY REFERENCES `Users`( `UserID` )   | Foreign key linking to the specific user's interview attempt |
| `StartTime`        | TIMESTAMP         | DEFAULT CURRENT_TIMESTAMP                    | Date and time when the interview started                     |
| `EndTime`          | TIMESTAMP         |                                              | Date and time when the interview ended                       |
| `DifficultyLevel`  | VARCHAR(50)       |                                              | Difficulty level selected for the interview (L)              |
| `NumberOfQuestions`| INT               |                                              | Number of questions in the interview (N)                     |
| `RoundLength`      | INT               |                                              | Length of the interview in minutes (T)                       |
| `OverallRating`    | DECIMAL(4, 2)     |                                              | Average rating for all questions in this interview           |
| `SubmissionTime`   | TIMESTAMP         |                                              | Time when the user submitted the entire interview            |


**QuestionSubmissions Table**
| Column Name           | Data Type         | Constraints                                                  | Description                                                              |
|-----------------------|-------------------|--------------------------------------------------------------|--------------------------------------------------------------------------|
| `SubmissionID`        | INT               | PRIMARY KEY AUTO_INCREMENT                                   | Unique identifier for each question submission within an interview       |
| `InterviewID`         | INT               | FOREIGN KEY REFERENCES `Interviews`( `InterviewID` )         | Foreign key linking to the specific user's interview attempt             |
| `QuestionNumber`      | INT               | NOT NULL                                                     | Order of the question asked in the interview (e.g., 1, 2, 3)             |
| `QuestionAsked`       | TEXT              | NOT NULL                                                     | The actual DSA question asked                                            |
| `CodeWritten`         | TEXT              |                                                              | The code written by the candidate for this question                      |
| `Feedback`            | TEXT              |                                                              | Feedback provided by the AI for this code                                |
| `Rating`              | DECIMAL(3, 1)     | NOT NULL                                                     | Rating given by the AI for the code (on a scale of 10, e.g., 7.5)        |

##

### NoSQL

If we closely look at the data we want to store, it is Interview Details of a user for all interviews user has attended.
The interview details as discussed above, comprise: Questions asked, Code written for each question, Feedback and rating for each code.
All this data is related and always fetched together and can be understood as a document containing all the information that is fetched when a user requests for his past interview data.

How would the document based data be structured?
<pre>
  { 
    "userId": "123", 
    "username": "john_doe", 
    "interviews": [ 
            { 
              "interviewId": "i-456", 
              "timestamp": "2024-04-17T10:00:00Z", 
              "question": { "...": "..." }, 
              "code": { "...": "..." }, 
              "feedback": { "...": "..." }, 
              "rating": 8 
            } 
      ] 
  } 
</pre>

### Why can MongoDB suit well for this use case?
1. **Single Document Fetch**: The embedded document structure in the interviews collection allows us to retrieve all details of a user's past interview (questions, code, feedback, ratings) with a single query based on the `userId`. This is more efficient than SQL, which requires joins between tables.
2. By creating an index on the `userId` field in the interviews collection, MongoDB can quickly locate the specific documents belonging to a particular user without having to scan the entire collection. This can significantly speed up the retrieval process.
3. **Schema Flexibility**: MongoDB's schema-less nature allows you to easily add new fields or modify the structure of documents in the interviews collection in the future without requiring rigid schema migrations. This can be beneficial if your platform evolves with new features or data points.
4. **Alignment with Data Structure**: The hierarchical nature of an interview object (containing multiple questions) maps naturally to the embedded array of documents within a MongoDB document, making the data model more intuitive.

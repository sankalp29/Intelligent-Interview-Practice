# What is the data to be stored?

User Details: Username, Password, Signup Date, Number of tests attended

Interview Details: Questions asked, Code written for each question, Feedback and rating for each code.

#SQL vs NoSQL

Let's look at how the data would be stored in both the scenarios.

First up, SQL:

# Users Table
| Column Name             | Data Type        | Constraints                | Description                                  |
|-------------------------|------------------|----------------------------|----------------------------------------------|
| `UserID`                | INT              | PRIMARY KEY AUTO_INCREMENT | Unique identifier for each user              |
| `Username`              | VARCHAR(255)     | UNIQUE NOT NULL            | User's chosen username                       |
| `Password`              | VARCHAR(255)     | NOT NULL                   | Hashed password for security                 |
| `SignupDate`            | TIMESTAMP        | DEFAULT CURRENT_TIMESTAMP  | Date and time when the user signed up        |
| `NumberOfTestsAttended` | INT              | DEFAULT 0                  | Count of tests the user has completed        |


# Interviews Table
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


# QuestionSubmissions Table
| Column Name           | Data Type         | Constraints                                                  | Description                                                              |
|-----------------------|-------------------|--------------------------------------------------------------|--------------------------------------------------------------------------|
| `SubmissionID`        | INT               | PRIMARY KEY AUTO_INCREMENT                                   | Unique identifier for each question submission within an interview       |
| `InterviewID`         | INT               | FOREIGN KEY REFERENCES `Interviews`( `InterviewID` )         | Foreign key linking to the specific user's interview attempt             |
| `QuestionNumber`      | INT               | NOT NULL                                                     | Order of the question asked in the interview (e.g., 1, 2, 3)             |
| `QuestionAsked`       | TEXT              | NOT NULL                                                     | The actual DSA question asked                                            |
| `CodeWritten`         | TEXT              |                                                              | The code written by the candidate for this question                      |
| `Feedback`            | TEXT              |                                                              | Feedback provided by the AI for this code                                |
| `Rating`              | DECIMAL(3, 1)     | NOT NULL                                                     | Rating given by the AI for the code (on a scale of 10, e.g., 7.5)        |



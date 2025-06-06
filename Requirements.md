# Functional Requirements:
1) Candidates log in using gmail.com, github.com, outlook.com or work email id.

2) Candidate selects the difficulty level of the interview (L), number of questions (N), length of the round (T).

3) The system will use the input parameters (difficulty level L, number of questions N) to query a Language Model (LLM) API (e.g., OpenAI or Gemini) and fetch N relevant Data Structures and Algorithms (DSA) questions of the specified difficulty level.

4) To simulate a realistic interview setting, the candidate will be presented with the problem statement only. There will be no question tags, pre-defined test cases, or auto-complete suggestions in the simple code editor.

5) Once the interview begins, a timer will start. The candidate will code their solution within the simple editor and is expected to explicitly state the time and space complexity of their solution.

6) Candidate can submit the interview, else the interview ends when timer stops.

7) AI evaluates the code for each question and gives score + feedback.

8) Candidates can request further clarifications and address doubts on the given feedback.

9) Test results are stored on a database. Candidate can revisit past test results.





# Non-functional Requirements:

1) Availability: The platform should be available 99.5% of the time.

2) Performance: Question generation must complete within 5 seconds 95% of the time. Code evaluation and scoring should complete within 5 seconds 95% of the time.

3) Durability: Interview history must be persisted immediately after submission.

4) Throughput: Support up to 100 concurrent users with minimal latency degradation.

5) Security: All API calls must be authenticated; OpenAI API keys should not be exposed to frontend.

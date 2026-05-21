# Instructor Code Review Feedback

**Contact**: Dr. Yiji Zhang (yiji.zhang@northwestern.edu)

**Purpose of This Document**:
The instructor will perform code review with respect to software design, error handling, format and style on the main branch every week starting Week 6 using the letter grade A standards.
The following chapters of the textbook are considered: Chapter 1, 2, 3, 4, 5, 6, 7, 9, and 10. The corresponding lectures are considered, too.

Please note that this feedback does not include evaluation of your progress, the proper use of linters, the quality of your test cases, or your compliance of TDD/BDD workflow.  
You can find the weekly feedback from your dedicated PM/TA for that.

## Week 7 Code Review
I have read every line of production code currently in the main branch.

Take Pawn#isValidMoveShape as an example, a couple of improvements that can be made:
1) I noticed null checks. According to Clean Code, we don't want to pollute our code with null checks. Instead, we want to make sure no null is passed around.
2) The method is doing do much. Meaning, it should be split into multiple private methods. 
3) The exception message can be a little more descriptive (even though the two exception throwing will be gone if you take out the null check)

Make sure to apply these coding pratices in the other classes too!

Please approve and merge the PR once the team has read the feedback. Thanks!

## Week 6 Code Review
I have read every line of production code currently in the main branch.
Good job overall! The only issue I found is the use of magic numbers in the UI classes.
But I guess you simply used the provided code as the starter code and 
haven't worked on it yet. That's fine. 
Make sure to refactor/improve the starter code according to the coding standards
we have learned in the class as well!

Look forward to reviewing more of your domain logic in the next review.

Please approve and merge the PR once the team has read the feedback. Thanks!
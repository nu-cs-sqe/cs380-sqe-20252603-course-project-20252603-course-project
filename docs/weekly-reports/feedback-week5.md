# Week 5 Project Feedback by PM/TA

**Dedicated PM/TA**: Jiahao Yu

## How to Read This Feedback
> [!NOTE]
> **Purpose.** This feedback focuses on your team's progress and collaboration. It is meant as guidance, not judgement.

> [!IMPORTANT]
> **Scope.** For the BVA and TDD items, the PM/TA evaluates only the `main` branch. Ongoing work in feature branches will be evaluated after it is merged. If you'd like early feedback on work in progress, please reach out to your PM/TA directly.

> [!TIP]
> **Mistakes are expected :).** As the instructor mentioned in class, early mistakes are part of the learning process. As long as your team addresses the issues after you get the feedback, your grade will not suffer from them.

## Checklist
Status:
- ✅: All done/Good job!
- ⚠️: Attention needed
- ❌: Significant issue found
- ➖: No basis to evaluate

### Past Feedback
| # | Item                                                                                                 | Status | Reviewer Notes | Source Instructions or Resources |
|---|------------------------------------------------------------------------------------------------------|:------:|----------------|----------------------------------|
| 0 | The team has closed and merged the past Feedback PR(s), indicating that they have read the feedback. |   ✅   | Week 4 feedback PR #5 has been merged into `main`. Thank you for closing the loop. | |

### Software Process Quality
| # | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 1 | Each active feature branch has an open draft PR against main.                                                                                                |     ✅     | The active implementation branch `feature-pieces-board-setup` has draft PR #11 open against `main`. The separate Week 5 report branch also has PR #6 open. | Week 4 Wednesday Lecture (Lecture 08)                                             |
| 2 | The team has a "definition of done" (BVA) fully documented for the part of the system that is done. (needed for Letter Grade D)                              |     ➖     | There is no completed setup-phase implementation merged into `main` yet, so I do not have completed behavior to evaluate on `main`. | Project grading rubrics                                                           |
| 3 | GitHub commit history demonstrates evidence of a TDD/BDD workflow for all the non-UI code. (needed for Letter Grade C)                                       |     ➖     | There is no non-UI implementation merged into `main` yet. PR #11 shows promising commits for piece classes, and I will evaluate it once it is merged. | Project grading rubrics                                                           |

### Planning & Progress Evaluation
| # | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 4 | The team documents every week's planning and progress evaluation professionally. (needed for Letter Grade B)                                                 |     ⚠️     | Week 5 progress is documented in PR #6, but it has not been merged into `main` yet. Please get the weekly report reviewed and merged so the main branch stays current. | Week 4 Wednesday Lecture (Lecture 08), Project grading rubrics                    |

### Progress & Collaboration
| # | Item                                                                                                                                                                                   |  Status   | Reviewer Notes      | Source Instructions or Resources                 |
|---|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|--------------------------------------------------|
| 5 | Overall development progress (recall the recommended order is: Game Setup Phase -> One turn of the game -> Multiple turns -> One win condition -> Other win conditions (if applicable) |     ⚠️     | You have strong planning artifacts and a large draft PR for Piece/UI board work. However, the Game Setup Phase is still not complete on `main`, and the Week 5 report says the Board/GameState/Main and Location/MainView/WelcomeView tasks have not started. Please prioritize unblocking and merging small, reviewed setup-phase slices before moving deeply into later phases. | Canvas assignment Project: Week 4 and 5 Guidance |
| 6 | Collaboration: Quality of discussion in PR reviews and work item comments on the board.                                                                                                |     ⚠️     | The issues are detailed and assigned, and PR #11 has one useful comment explaining failing checks. I do not see much PR review discussion yet. Please add short review comments about BVA coverage, TDD evidence, dependency/order concerns, or requested changes so collaboration is visible. | |

### The following items are not checked by the reviewer as they were checked in the previous weeks
But if your team wants the reviewer to check any of these for any reasons, please contact them or the instructor via either email or tagging them in the feedback PR.

| #   | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|-----|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 1   | GitHub repository branch protection rules are fully set up so that people cannot push into main without a pull request approval. (needed for Letter Grade C) |     ➖     | Not rechecked this week. | Canvas assignment Project: Setup, Project grading rubrics                         |
| 2   | Continuous Integration (CI) is fully set up from the beginning. (needed for Letter Grade B)                                                                  |     ➖     | Not rechecked this week. | Canvas assignment Project: Setup, Project grading rubrics                         |
| 3   | The team uses the project management board steadily and frequently, and the description of each task is detailed. (needed for Letter Grade B)                | See below | See breakdown below | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.1 | Every functionality-related work item on the management board includes a user story, and optionally one or more use cases.                                   |     ➖     | Not rechecked in full this week. The Game Setup Phase parent issue has a clear user story/use case, and the split task issues describe file ownership and TDD focus. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.2 | The design is documented somewhere, either in the work item description, or in a separate design document.                                                   |     ➖     | Not rechecked in full this week. The design document remains available in `docs/design`. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.3 | Task assignments are documented clearly in the management board.                                                                                             |     ➖     | Not rechecked in full this week. The split task issues show owners for the main setup areas. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |

## Additional Comments
The structure of your work is good: the setup-phase requirements are detailed, the tasks are split by ownership, and PR #11 shows real effort on BVA and TDD-style piece implementation. The main risk is integration pace. Since several parts depend on each other, try to merge smaller reviewed slices into `main` frequently rather than letting one large draft PR become the only place where implementation exists. Also, it would be good to have some comments/conversations in the PR reviews so that we can see you are collaborating with your teammates.

## Review Snapshot (Just used for tracking purposes, not for feedback)
- Reviewed latest `main` commit: `9622cbc`
- Commit summary: Merge pull request #5 from `nu-cs-sqe/feedback-week4`
- Review date: 2026-05-05
- Verification: `bash ./gradlew test` passed with Java 11 on `main`; there are currently no test sources on `main`

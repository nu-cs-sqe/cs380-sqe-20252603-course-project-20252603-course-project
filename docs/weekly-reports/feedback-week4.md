# Week 4 Project Feedback by PM/TA

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

### Software Process Quality
| #   | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|-----|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 1   | GitHub repository branch protection rules are fully set up so that people cannot push into main without a pull request approval. (needed for Letter Grade C) |     ✅     | Great setup: `main` requires pull request review and the Gradle status check before merge. | Canvas assignment Project: Setup, Project grading rubrics                         |
| 2   | Continuous Integration (CI) is fully set up from the beginning. (needed for Letter Grade B)                                                                  |     ✅     | The Gradle Build workflow is configured and the recent runs are passing. | Canvas assignment Project: Setup, Project grading rubrics                         |
| 3   | The team uses the project management board steadily and frequently, and the description of each task is detailed. (needed for Letter Grade B)                | See below | See breakdown below | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.1 | Every functionality-related work item on the management board includes a user story, and optionally one or more use cases.                                   |     ✅     | Great work on the Game Setup Phase item. It includes a user story, acceptance criteria, a use case, alternate flows, and postconditions. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.2 | The design is documented somewhere, either in the work item description, or in a separate design document.                                                   |     ✅     | Nice start on the design. I see a design document in PR #4 and detailed class/file ownership in the project board. Please merge the design PR after review. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.3 | Task assignments are documented clearly in the management board.                                                                                             |     ✅     | All good! The setup work is split across Domain/Setup Logic, Board Rendering/Controller, and Start Screen/Main Window, with owners assigned. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 4   | Each active feature branch has an open draft PR against main.                                                                                                |     ✅     | All good! I see open PRs for the Week 4 report/design work, and they target `main`. | Week 4 Wednesday Lecture (Lecture 08)                                             |
| 5   | The team has a “definition of done” (BVA) fully documented for the part of the system that is done. (needed for Letter Grade D)                              |     ➖     | There does not seem to be setup-phase implementation merged into `main` yet, so there is not much completed behavior to evaluate. The project board already lays out BVA/documentation ownership, which is a good next step. | Project grading rubrics                                                           |
| 6   | GitHub commit history demonstrates evidence of a TDD/BDD workflow for all the non-UI code. (needed for Letter Grade C)                                       |     ➖     | There is no non-UI implementation merged into `main` yet. Once coding starts, please keep the tests and small TDD-style commits visible in PRs. | Project grading rubrics                                                           |

### Planning & Progress Evaluation
| # | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 7 | The team documents every week’s planning and progress evaluation professionally. (needed for Letter Grade B)                                                 |     ✅     | Good work. Week 4 progress is documented in open PRs, with the team meeting and the three major setup work areas recorded. Please merge the report after review. | Week 4 Wednesday Lecture (Lecture 08), Project grading rubrics                    |

### Progress & Collaboration
| #  | Item                                                                                                                                                             |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|----|------------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 8  | README is updated properly, including the project name, contributors, and build status badge. In addition, the team should specify the GUI library if appliable. |     ✅     | All good! The README has the project name, contributors, and build badge. | Canvas assignment Project: Setup                                                  |
| 9  | Overall progress on "Game Setup Phase"                                                                                                                           |     ⚠️     | Strong planning progress: the board has a detailed setup-phase requirement, task split, and design draft. The main next step is to start merging implementation and tests through PRs. | Canvas assignment Project: Week 4 Guidance                                        |
| 10 | Collaboration: Quality of discussion in PR reviews and work item comments on the board.                                                                          |     ✅     | All good! The team is using PRs and the project board, and prior PRs show review activity before merging. |                                                                                   |

## Additional Comments
Nice work organizing the Game Setup Phase. I believe that you are on the right track. Keep up the good work!

## Review Snapshot (Just used for tracking purposes, not for feedback)
- Reviewed latest `main` commit: `ed55c6db1a0f0f84c2ad6727090d82e7ce61882b`
- Commit summary: Merge pull request #2 from `nu-cs-sqe/reedgunn-patch-1`
- Review date: 2026-04-27

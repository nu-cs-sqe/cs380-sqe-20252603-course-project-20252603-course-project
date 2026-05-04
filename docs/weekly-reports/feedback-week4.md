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
| 3.1 | Every functionality-related work item on the management board includes a user story, and optionally one or more use cases.                                   |     ✅     | The board initialization task has a clear user story, BVA/TDD plan, and definition of done. Nice start. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.2 | The design is documented somewhere, either in the work item description, or in a separate design document.                                                   |     ⚠️     | I found the Board task and BVA, but the `docs/design` document is still a placeholder. Please add the design for the setup phase/classes soon. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.3 | Task assignments are documented clearly in the management board.                                                                                             |     ⚠️     | The current board task is assigned to Ryan. Please consider splitting tasks to other members to share the workload. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 4   | Each active feature branch has an open draft PR against main.                                                                                                |     ⚠️     | I see active work on `feature-board`, but there is no open/draft PR yet. Please open draft PRs early so the team can track and review ongoing work. | Week 4 Wednesday Lecture (Lecture 08)                                             |
| 5   | The team has a “definition of done” (BVA) fully documented for the part of the system that is done. (needed for Letter Grade D)                              |     ✅     | `docs/bva/Board.md` is a good beginning and includes implemented/unimplemented cases. | Project grading rubrics                                                           |
| 6   | GitHub commit history demonstrates evidence of a TDD/BDD workflow for all the non-UI code. (needed for Letter Grade C)                                       |     ⚠️     | The `feature-board` branch shows promising TDD-style commits for TC1/TC2. Since the scope for this feedback is `main`, please merge tested implementation through PR after review. | Project grading rubrics                                                           |

### Planning & Progress Evaluation
| # | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 7 | The team documents every week’s planning and progress evaluation professionally. (needed for Letter Grade B)                                                 |     ⚠️     | I found Week 4 progress notes on a branch, but they are not merged into `main` yet. Please merge the weekly report through a PR once reviewed. | Week 4 Wednesday Lecture (Lecture 08), Project grading rubrics                    |

### Progress & Collaboration
| #  | Item                                                                                                                                                             |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|----|------------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 8  | README is updated properly, including the project name, contributors, and build status badge. In addition, the team should specify the GUI library if appliable. |     ✅     | All good! | Canvas assignment Project: Setup                                                  |
| 9  | Overall progress on "Game Setup Phase"                                                                                                                           |     ⚠️     | You have started the setup phase with the Board task, BVA, and early implementation work on a feature branch. Great first step; next, add design documentation and continue implementing through draft PRs. | Canvas assignment Project: Week 4 Guidance                                        |
| 10 | Collaboration: Quality of discussion in PR reviews and work item comments on the board.                                                                          |     ✅     | All good! PR #6 has review approval, and the team has started using the project board for the Board setup task. |                                                                                   |

## Additional Comments
Nice progress getting the project infrastructure in place and starting the Board setup work. 
For the next step, I would suggest focusing on turning the early planning into a more complete Week 4 workflow: add the setup-phase design document, open draft PRs for active feature branches, merge the weekly report into `main`, and make sure work is split clearly across team members. You are moving in the right direction; just make the process artifacts visible as you code so the team can collaborate and review continuously.

## Review Snapshot (Just used for tracking purposes, not for feedback)
- Reviewed latest `main` commit: `1ad43c181888502975a4666a315045108e7bfe35`
- Commit summary: Merge pull request #6 from `nu-cs-sqe/BVA-updates`
- Review date: 2026-04-27

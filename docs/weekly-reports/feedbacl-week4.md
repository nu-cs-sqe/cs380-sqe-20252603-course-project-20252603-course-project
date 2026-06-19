# Week 4 Project Feedback by PM/TA

**Dedicated PM/TA**: Vandan Agrawal (vandanagrawal2026@u.northwestern.edu)

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
| 1   | GitHub repository branch protection rules are fully set up so that people cannot push into main without a pull request approval. (needed for Letter Grade C) | ✅         | "Branch protection rules have been implemented properly" | Canvas assignment Project: Setup, Project grading rubrics                         |
| 2   | Continuous Integration (CI) is fully set up from the beginning. (needed for Letter Grade B)                                                                  | ✅        | `.github/workflows/main.yml` runs a Gradle build on pushes and pull requests to `main`; recent workflow runs completed successfully. | Canvas assignment Project: Setup, Project grading rubrics                         |
| 3   | The team uses the project management board steadily and frequently, and the description of each task is detailed. (needed for Letter Grade B)                | ⚠️        | GitHub Project **`team-06-20252603`** is set up (Backlog, **Board**, **Current iteration**). Only **one** item (#5, Player class) sits in **Todo**; **In progress** and **Done** are empty—no visible workflow or completed board work. **Current iteration** (`iteration: @current`) shows **0** items, so sprint/iteration tracking is unused while the card lives outside that view. That is minimal adoption versus “steady/frequent” use and detailed per-task descriptions. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.1 | Every functionality-related work item on the management board includes a user story, and optionally one or more use cases.                                   | ⚠️        | The board’s only functionality card is #5 (“game setup phase: Player class”), which reads as an **implementation** task, not a written **user story** (As a… / I want… / so that…). Use cases are not evident from the board. The weekly report still lists generic “User Story” lines without matching rich cards on the board. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.2 | The design is documented somewhere, either in the work item description, or in a separate design document.                                                   | ⚠️        | `docs/design/README.md` remains a placeholder in the repo. The board views reviewed do **not** surface a design link or acceptance criteria on the card. Issue #5’s body references a design doc in text only—traceability from board to design is weak. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.3 | Task assignments are documented clearly in the management board.                                                                                             | ✅        | On the **Board** view, #5 shows an assignee (`zsharon012`) in **Todo**. Assignments are also reflected in `docs/weekly-reports/report.md` and on the issue itself. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance || 4   | Each active feature branch has an open draft PR against main.                                                                                                | ➖        | No open PRs were found. The only non-main branch visible is `wip-report`, which appears to have already been merged, so there is no clear active feature branch to evaluate. | Week 4 Wednesday Lecture (Lecture 08)                                             |
| 5   | The team has a “definition of done” (BVA) fully documented for the part of the system that is done. (needed for Letter Grade D)                              | ⚠️        | `docs/bva/README.md` contains only the template/instructions; no class-specific BVA or implemented-test status is documented yet. | Project grading rubrics                                                           |
| 6   | GitHub commit history demonstrates evidence of a TDD/BDD workflow for all the non-UI code. (needed for Letter Grade C)                                       | ➖        | "Commit history demonstrates TDD/BDD workflow but commits can be more meaningful and informative" | Project grading rubrics                                                           |

### Planning & Progress Evaluation
| # | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 7 | The team documents every week’s planning and progress evaluation professionally. (needed for Letter Grade B)                                                 | ⚠️        | `docs/weekly-reports/report.md` has Week 3-5 sections with statuses and assignees, but Week 4 entries need more detail and PR/work-item links. | Week 4 Wednesday Lecture (Lecture 08), Project grading rubrics                    |

### Progress & Collaboration
| #  | Item                                                                                                                                                             |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|----|------------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 8  | README is updated properly, including the project name, contributors, and build status badge. In addition, the team should specify the GUI library if applicable. | ⚠️        | README includes the project name, contributors, dependencies, and build badge. It still needs the GUI library choice if applicable. | Canvas assignment Project: Setup                                                  |
| 9  | Overall progress on "Game Setup Phase"                                                                                                                           | ❌        | No Game Setup Phase implementation is present on `main`: `src/main/java/ui/Main.java` is empty and `src/main/java/domain` contains only `.gitkeep`. | Canvas assignment Project: Week 4 Guidance                                        |
| 10 | Collaboration: Quality of discussion in PR reviews and work item comments on the board.                                                                          | ⚠️        | PR usage is visible, but most merged PRs have no comments/reviews and the open feature issue has no discussion. Project-board comments could not be checked from the local/public repo view. |                                                                                   |

## Additional Comments
- Checked the repository files, README, Gradle setup, CI workflow, docs, source/test folders, public branches, public PR list, public issues, recent commits, and recent GitHub Actions runs.

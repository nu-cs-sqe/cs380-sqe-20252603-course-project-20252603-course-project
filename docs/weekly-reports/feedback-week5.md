# Week 5 Project Feedback by PM/TA

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

### Past Feedback
| # | Item                                                                                                 | Status | Reviewer Notes | Source Instructions or Resources |
|---|------------------------------------------------------------------------------------------------------|:------:|----------------|----------------------------------|
| 0 | The team has closed and merged the past Feedback PR(s), indicating that they have read the feedbacl. | ✅      | Week 4 feedback PR was merged into `main` (merge commit references PR #6 from branch `feedback-week4`). |                                  |

### Software Process Quality
| # | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 1 | Each active feature branch has an open draft PR against main.                                                                                                | ❌        | No open PRs are currently visible, while non-main branches (e.g., `wip-player-setup`) still exist. This does not satisfy the “active branch has open draft PR” expectation. | Week 4 Wednesday Lecture (Lecture 08)                                             |
| 2 | The team has a “definition of done” (BVA) fully documented for the part of the system that is done. (needed for Letter Grade D)                              | ⚠️        | `docs/bva/Card.md` now documents Card BVA with implemented/not-implemented marking, which is progress. However, documentation and code are not fully aligned yet (BVA lists play methods not present in current `Card` implementation), and broader system BVA coverage is still pending. | Project grading rubrics                                                           |
| 3 | GitHub commit history demonstrates evidence of a TDD/BDD workflow for all the non-UI code. (needed for Letter Grade C)                                       | ⚠️        | There is some positive evidence (test-focused commits such as constructor tests and matching `CardTests.java`). Still, workflow evidence is concentrated on one class and not yet strong enough across all non-UI work. | Project grading rubrics                                                           |

### Planning & Progress Evaluation
| # | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 4 | The team documents every week’s planning and progress evaluation professionally. (needed for Letter Grade B)                                                 | ⚠️        | `docs/weekly-reports/report.md` now includes Week 5 updates with ownership and rough progress percentages. It still needs clearer deliverables/acceptance criteria and links to issues/PRs for professional traceability. | Week 4 Wednesday Lecture (Lecture 08), Project grading rubrics                    |

### Progress & Collaboration
| # | Item                                                                                                                                                                                   |  Status   | Reviewer Notes      | Source Instructions or Resources                 |
|---|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|--------------------------------------------------|
| 5 | Overall development progress (recall the recommended order is: Game Setup Phase -> One turn of the game -> Multiple turns -> One win condition -> Other win conditions (if applicable) | ⚠️        | Progress is visible but still early: `Card`/`CardType`/`Action` and constructor tests are in `main`, while key game-setup/domain pieces (Deck, Player, Game flow, turn progression, win conditions) are not yet implemented in `main`. | Canvas assignment Project: Week 4 and 5 Guidance |
| 6 | Collaboration: Quality of discussion in PR reviews and work item comments on the board.                                                                                                | ⚠️        | PR/issue activity exists, but visible discussion depth remains limited (most items have few or no comments/reviews). Add more substantive review comments and board discussion threads to show stronger collaboration quality. |                                                  |

### The following items are not checked by the reviewer as they were checked in the previous weeks
But if your team wants the reviewer to check any of these for any reasons, please contact them or the instructor via either email or tagging them in the feedback PR.

| #   | Item                                                                                                                                                         |  Status   | Reviewer Notes      | Source Instructions or Resources                                                  |
|-----|--------------------------------------------------------------------------------------------------------------------------------------------------------------|:---------:|---------------------|-----------------------------------------------------------------------------------|
| 1   | GitHub repository branch protection rules are fully set up so that people cannot push into main without a pull request approval. (needed for Letter Grade C) | ➖        | Not re-evaluated this week per the section guidance; see Week 4 feedback record. | Canvas assignment Project: Setup, Project grading rubrics                         |
| 2   | Continuous Integration (CI) is fully set up from the beginning. (needed for Letter Grade B)                                                                  | ➖        | Not re-evaluated this week per the section guidance; see Week 4 feedback record. | Canvas assignment Project: Setup, Project grading rubrics                         |
| 3   | The team uses the project management board steadily and frequently, and the description of each task is detailed. (needed for Letter Grade B)                | ➖        | Not re-evaluated this week per the section guidance; see Week 4 feedback record. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.1 | Every functionality-related work item on the management board includes a user story, and optionally one or more use cases.                                   | ➖        | Not re-evaluated this week per the section guidance; see Week 4 feedback record. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.2 | The design is documented somewhere, either in the work item description, or in a separate design document.                                                   | ➖        | Not re-evaluated this week per the section guidance; see Week 4 feedback record. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |
| 3.3 | Task assignments are documented clearly in the management board.                                                                                             | ➖        | Not re-evaluated this week per the section guidance; see Week 4 feedback record. | Week 4 Wednesday Lecture (Lecture 08), Canvas assignment Project: Week 4 Guidance |

## Additional Comments
- Strengths this week: meaningful progress on `Card` implementation, constructor-focused tests, and a class-specific BVA document (`docs/bva/Card.md`).
- Main next milestone: complete remaining Game Setup classes (Player/Deck/Game) in `main`, then move into one-turn gameplay flow.
- To improve grading readiness next week: ensure active branches have draft PRs, add richer PR/issue review discussion, and link weekly report items to concrete issues/PRs.

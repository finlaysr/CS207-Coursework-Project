# User Stories 1 - Tracking progress

## Stakeholder : player

> "As a player, I want to be able to see my score and other players' scores after my game session, so that I can compare"

* As a player, I want to be able to view my stats like puzzles completed, like hints used, so I can then compare to other players.
* As a player I want my login name saved so that my progress that my personal progress can be saved and accessed.
* As a player I want see my current game progress and other user progresses as a scoreboard after my session ends, so that I can easily see my score.
* As a player, I want my incomplete sessions to be saved during the term of the program, so that when I return to particular quiz my state remains.

### Acceptance Test:

#### Scenario: Only one player logged on

* **Given** that there is only one user logged on when the program is running
* **When** the user completes their game
* **Then** the user is shown only their scores

#### Scenario: Multiple players have logged in

* **Given** that multiple users have logged on and completed a game
* **When** the user completed their game
* **Then** the user is shown their score along with scores from other players

#### Scenario: No previous games have been completed

* **Given** that no previous games have been completed
* **When** the user finishes their game
* **Then** the user is only shown the score from their game

#### Scenario: Previous games have been completed

* **Given** that previous games have been completed
* **When** the user finishes their game
* **Then** the user is shown their score, along with the top scores from previous games
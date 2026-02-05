# User Stories 2 - Gameplay

## Stakeholder: User

* As a user, I want to be able to take options to continue progress so I can complete the puzzle. 
    * As a user, I want to be able to receive a hint to solve once letter.
    * As a user, I want to be able to see the full solution for a current game if I feel I am fully stuck and can not make any more progress.
    * As a user, I want to be able to change my current puzzle if I find the difficulty too hard.


* As a user, when I correctly decipher a letter I would like the game to autofill all other instances of this letter in the message so as it is more convinient and I don't have to re-type the same letter multiple times.


* As a user, I want to be able to have the option to play a large options of games, so as there is variety in the messages to be decoding and I am not repeating the same messages.


* As a user, I want to have a stopwatch timer from when I first start completing the puzzle so as I can see how first I can solve puzzle.


* As a user, I want to be able to view the rules so as when I'm playing for the first time I know what to do and I am not confused.


* As a user, I want to check the frequency of letters appearing in the message, as this will make it easier to solve the puzzle.

## Acceptance Tests (2 per story)

### Scenario: User is Stuck 
* **Given** that the user has started a game and is stuck
* **When** the user asks for a hint
* **Then** the program should insert a helpful letter so the user can continue




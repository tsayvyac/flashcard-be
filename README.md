1. [What is Flashcards web application?](#what-is-flashcards-web-application)
2. [What technologies is used?](#what-technologies-is-used)
3. [Prerequisite](#prerequisite)
4. [How to run?](#how-to-run)
5. [More](#more)

# What is Flashcards web application?

The Flashcards web application is an intuitive and powerful tool designed to enhance learning and exam preparation through interactive digital flashcards. Serving as a modern alternative to paper cards, the application empowers users to create, organize, and review custom flashcard sets with ease.

This application prioritizes a user-friendly experience, enabling users to seamlessly manage and study a wide range of topics, from academic subjects to professional knowledge areas. By supporting efficient information retention and recall, the Flashcards application provides an essential resource for learners aiming to build mastery and confidence in any subject.

## What technologies are used?
The Flashcards web application leverages a modern tech stack to ensure reliability, scalability, and a responsive user experience. Key technologies include:
- Database: PostgreSQL
- System: Java 17 with Spring Boot
- View: Typescript and React

The REST API architectural style is used to communicate between the system and the view.

# Prerequisite
1. Installed Java 17
2. Installed Maven
3. Installed IDE, for example Intellij Idea

# How to run?
1. Clone this repository
```
git clone https://github.com/tsayvyac/flashcard-be.git
```
2. Add the necessary variables to the `.env.example` file:
3. Erase `.example` and save the `.env` file
4. Run the `scripts/run.sh` script to start the application, or use IDE
5. It can also be run in a docker container
```
docker build -t [tag] .
docker run --env-file ./.env [tag or id]
```
6. Run [frontend](https://github.com/tsayvyac/flashcard-fe-vite).

# More
GitLab CI/CD pipeline was also created for this project. See [GitLab repository](https://gitlab.fel.cvut.cz/tsayvyac/zks_2024_semestral_work_tsayvyac_ulcheyev).

# Prerequisite
1. Installed Java 17
2. Installed Maven
3. Installed IDE, for example Intellij Idea

# How to run?
1. Clone this repository
```
git clone https://github.com/tsayvyac/flashcard-be.git
```
2. This application initially uses an H2 in-memory database for demonstration purposes. To switch to PostgreSQL,
   update the `spring.profiles.active` setting in `src/main/application.yml` by replacing "h2" with "pg".
   Then, configure `src/main/application-pg.yml` with the PostgreSQL URL, username, and password.
   If you prefer to use H2, no additional configuration is needed; simply start the application.
> :warning: After this step you can run project using IDE or follow next steps.
3. Go to the folder that was cloned and write in terminal (cmd):
```
mvn spring-boot:run
```
4. Run [frontend](https://github.com/tsayvyac/flashcard-fe-vite) and use the application

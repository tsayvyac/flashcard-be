# Prerequisite
1. Installed Java 17
2. Installed Maven
3. Installed IDE, for example Intellij Idea

# How to run?
1. Clone this repository
```
git clone https://github.com/tsayvyac/flashcard-be.git
```
2. Create `.env` file in the root directory
3. Add the necessary variables to the `.env` file:
```
# SERVER
CONTEXT_PATH=
SERVER_PORT= # Default 8080

# DATASOURCE
DATASOURCE_URL=
DATASOURCE_USERNAME=
DATASOURCE_PASSWORD=

# SECURITY
JWT_SECRET_KEY=
JWT_EXPIRATION=

# THREAD POOL
CORE_POOL_SIZE= # Default is 2
MAX_POOL_SIZE= # Default is 4
QUEUE_CAPACITY= # Default is 40
```
4. Save the `.env` file
5. Run the `scripts/run.sh` script, using IDE or Docker
6. Run [frontend](https://github.com/tsayvyac/flashcard-fe-vite)
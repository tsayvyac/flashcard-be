CREATE SEQUENCE IF NOT EXISTS card_set_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS flashcard_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS learner_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS learner
(
    uid      BIGINT       NOT NULL,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    role     VARCHAR(255),
    CONSTRAINT pk_learner PRIMARY KEY (uid)
    );

CREATE TABLE IF NOT EXISTS card_set
(
    id         BIGINT       NOT NULL,
    name       VARCHAR(255) NOT NULL,
    learner_id BIGINT       NOT NULL,
    CONSTRAINT pk_cardset PRIMARY KEY (id),
    CONSTRAINT FK_CARDSET_ON_LEARNER FOREIGN KEY (learner_id) REFERENCES learner (uid)
    );

CREATE TABLE IF NOT EXISTS flashcard
(
    id     BIGINT NOT NULL,
    front  OID    NOT NULL,
    back   OID    NOT NULL,
    set_id BIGINT NOT NULL,
    CONSTRAINT pk_flashcard PRIMARY KEY (id),
    CONSTRAINT FK_FLASHCARD_ON_SET FOREIGN KEY (set_id) REFERENCES card_set (id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS progress
(
    flashcard_id BIGINT  NOT NULL,
    repetitions  INTEGER NOT NULL,
    streak       INTEGER NOT NULL,
    last_score   INTEGER,
    next_date    date    NOT NULL,
    learner_id   BIGINT  NOT NULL,
    CONSTRAINT pk_progress PRIMARY KEY (flashcard_id),
    CONSTRAINT FK_PROGRESS_ON_FLASHCARD FOREIGN KEY (flashcard_id) REFERENCES flashcard (id) ON DELETE CASCADE,
    CONSTRAINT FK_PROGRESS_ON_LEARNER FOREIGN KEY (learner_id) REFERENCES learner (uid)
    );
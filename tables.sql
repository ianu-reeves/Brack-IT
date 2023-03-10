CREATE TABLE users(
    id INT GENERATED ALWAYS AS IDENTITY,
    username VARCHAR(24) UNIQUE,
    password CHAR(60),
    email VARCHAR,
    steam_id CHAR(17),
    gamertag VARCHAR(16),
    psn VARCHAR(16),
    bio TEXT,
    signup_date DATE,
    last_online TIMESTAMPTZ,
    PRIMARY KEY(id)
)

CREATE TABLE matches(
    id INT GENERATED ALWAYS AS IDENTITY,
    parent_match_id INT,
    match_slot_1 INT,
    match_slot_2 INT,
    competitor_1_id INT,
    competitor_2_id INT,
    match_depth INT,
    winner INT,
    score_to_win INT,
    PRIMARY KEY(id),
    FOREIGN KEY(parent_match_id) REFERENCES matches(id),
    FOREIGN KEY(match_slot_1) REFERENCES matches(id),
    FOREIGN KEY(match_slot_2) REFERENCES matches(id),
    FOREIGN KEY(competitor_1_id) REFERENCES userCompetitors(id)
    FOREIGN KEY(competitor_2_id) REFERENCES userCompetitors(id),
    FOREIGN KEY(winner) REFERENCES userCompetitors(id)
)

CREATE TABLE tournaments(
    id INT GENERATED ALWAYS AS IDENTITY,
    root_match_id INT,
    rounds_per_match INT DEFAULT 1,
    PRIMARY KEY(id),
    FOREIGN KEY(root_match_id) REFERENCES matches(id)
)

CREATE TABLE userCompetitors(
    id INT GENERATED ALWAYS AS IDENTITY,
    tournament_id INT,
    user_id INT,
    score INT DEFAULT 0,
    PRIMARY KEY(id),
    FOREIGN KEY(user_id) REFERENCES users(id),
    FOREIGN KEY(tournament_id) REFERENCES tournaments(id)
)
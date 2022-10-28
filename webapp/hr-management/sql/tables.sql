drop table e_t;
drop table team;
drop table employee;
drop table e_status;
drop table e_status_past;
create table employee
(
    id         SERIAL PRIMARY KEY NOT NULL,
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    dob        DATE,
    gender     VARCHAR(50),
    email      VARCHAR(50),
    phone      VARCHAR(50),
    education  VARCHAR(1000),
    xp         INT,
    cv         VARCHAR(50),
    profile    VARCHAR(1000),
    created    DATE
);
create table team
(
    id         SERIAL PRIMARY KEY NOT NULL,
    name       VARCHAR(50),
    boss_id    VARCHAR(50),
    profile    VARCHAR(150),
    is_remote  boolean,
    created    DATE,
    ip_address VARCHAR(20),
    department VARCHAR(50)
);

create table e_status
(
    id                    SERIAL PRIMARY KEY NOT NULL,
    e_id                  INT,
    t_id                  INT,
    working_hour          INT,
    sprint                INT,
    sprint_tasks_awaiting INT,
    tasks_completed       INT,
    tasks_delayed         INT,
    tasks_incomplete      INT,
    team_score_avg        INT,
    monthly_salary        INT,
    title                 VARCHAR(120)
);

create table e_status_past
(
    id                    SERIAL PRIMARY KEY NOT NULL,
    e_id                  INT,
    t_id                  INT,
    working_hour          INT,
    sprint                INT,
    sprint_tasks_awaiting INT,
    tasks_completed       INT,
    tasks_delayed         INT,
    tasks_incomplete      INT,
    team_score_avg        INT,
    monthly_salary        INT,
    year                  INT,
    work_days_late        INT,
    work_days_overtime    INT
);

ALTER TABLE "e_status"
    ADD FOREIGN KEY ("e_id") REFERENCES "employee" ("id");
ALTER TABLE "e_status"
    ADD FOREIGN KEY ("t_id") REFERENCES "team" ("id");
ALTER TABLE "e_status_past"
    ADD FOREIGN KEY ("e_id") REFERENCES "employee" ("id");
ALTER TABLE "e_status_past"
    ADD FOREIGN KEY ("t_id") REFERENCES "team" ("id");

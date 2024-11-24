--liquibase formatted sql

--changeset kuzneczov:1
INSERT INTO project (name)
VALUES ('Test my app');

--changeset kuzneczov:2
INSERT INTO task_state (name, project_id, left_task_state_id, right_task_state_id)
VALUES ('To Do', 1, NULL, 1),
       ('In Progress', 1, 1, 3),
       ('Done', 1, 2, NULL);

--changeset kuzneczov:3
INSERT INTO task (name, task_state_id, description)
VALUES ('Set up project repository', 1, 'Initialize the Git repository and set up the project structure.'),
       ('Design database schema', 2, 'Create the database schema for tasks and projects.'),
       ('Implement authentication', 2, 'Add user authentication using OAuth.'),
       ('Write documentation', 1, 'Create user and developer guides.'),
       ('Plan sprint tasks', 1, 'Define tasks for the upcoming sprint.'),
       ('Code review', 3, 'Review the code submitted in the latest merge request.'),
       ('Deploy to production', 3, 'Release the final version of the project to production.');

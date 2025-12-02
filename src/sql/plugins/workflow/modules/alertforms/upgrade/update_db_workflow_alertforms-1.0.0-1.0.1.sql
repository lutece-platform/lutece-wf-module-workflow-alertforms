-- liquibase formatted sql
-- changeset workflow-alertforms:update_db_workflow_alertforms-1.0.0-1.0.1.sql
-- preconditions onFail:MARK_RAN onError:WARN
ALTER TABLE task_alert ADD is_executed SMALLINT DEFAULT 1 NOT NULL;
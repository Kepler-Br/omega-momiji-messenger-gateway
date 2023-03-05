-- \c "postgres"

CREATE USER "user" WITH PASSWORD 'user';

GRANT ALL ON SCHEMA public TO "user";
-- GRANT USAGE, CREATE ON SCHEMA public TO user;
-- \c "user"

CREATE DATABASE "messages";

-- GRANT ALL ON DATABASE "messages" TO "user";


\c "postgres"

CREATE USER "user" WITH PASSWORD 'user';

CREATE DATABASE "messages";

GRANT ALL PRIVILEGES ON DATABASE "messages" TO "user";

GRANT ALL ON SCHEMA public TO "user";

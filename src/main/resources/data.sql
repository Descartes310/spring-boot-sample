--
-- PostgreSQL database
--


drop table if exists oauth_client_details cascade;
create table oauth_client_details
(
    client_id VARCHAR(255) PRIMARY KEY,
    resource_ids VARCHAR(255),
    client_secret VARCHAR(255),
    scope VARCHAR(255),
    authorized_grant_types VARCHAR(255),
    web_server_redirect_uri VARCHAR(255),
    authorities VARCHAR(255),
    access_token_validity INTEGER,
    refresh_token_validity INTEGER,
    additional_information VARCHAR(4096),
    autoapprove VARCHAR(255)
);

drop table if exists oauth_client_token cascade;
create table oauth_client_token
(
    token_id VARCHAR(255),
    token BYTEA,
    authentication_id VARCHAR(255) PRIMARY KEY,
    user_name VARCHAR(255),
    client_id VARCHAR(255)
);

drop table if exists oauth_access_token cascade;
create table oauth_access_token
(
    token_id VARCHAR(255),
    token BYTEA,
    authentication_id VARCHAR(255) PRIMARY KEY,
    user_name VARCHAR(255),
    client_id VARCHAR(255),
    authentication BYTEA,
    refresh_token VARCHAR(255)
);

drop table if exists oauth_refresh_token cascade;
create table oauth_refresh_token
(
    token_id VARCHAR(255),
    token BYTEA,
    authentication BYTEA
);

drop table if exists oauth_code cascade;
create table oauth_code
(
    code VARCHAR(255),
    authentication BYTEA
);

drop table if exists oauth_approvals cascade;
create table oauth_approvals
(
    userId VARCHAR(255),
    clientId VARCHAR(255),
    scope VARCHAR(255),
    status VARCHAR(10),
    expiresAt TIMESTAMP,
    lastModifiedAt TIMESTAMP
);


-- Client App : access_token = un ans = 31536000, refresh_token = un ans = 31536000
-- client passport: geloka-secret
INSERT INTO oauth_client_details
    (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
VALUES
    ('geloka-app', 'geloka-api', '$2y$12$3FO.GovgIzEY8EvRL15iJee5xyJnA7eC6p8CQoXY.yfTMKviRwBha', 'read,write,trust', 'implicit,password,authorization_code,refresh_token,client_credentials', '', '', 31536000, 31536000, '{}', '');


-- password is 1234 for all users
INSERT INTO users
    (id, email, password)
VALUES
    (1, 'admin@localhost.com', '$2y$12$fNJut5s9iQCQy/rn/VdqleB3bbycjiw3uQ8eX.Hxz4xgDZdSgj4L6');
    
    
    
    
    
    
    
    
    
    
    
    
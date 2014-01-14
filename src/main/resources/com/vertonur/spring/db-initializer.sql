-- Table that database-backed persistent token of spring security uses
CREATE TABLE IF NOT EXISTS persistent_logins (username varchar(50) not null, series varchar(64) primary key, token varchar(64) not null, last_used timestamp not null);

commit;
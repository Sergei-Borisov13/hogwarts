ALTER TABLE student
    ADD CONSTRAINT age_check CHECK ( age >= 16 );

ALTER TABLE student
    ADD CONSTRAINT name_unique UNIQUE (name);

ALTER TABLE student
    ALTER COLUMN name set not null;

ALTER TABLE faculty
    ADD CONSTRAINT name_color_unique UNIQUE (name, color);

ALTER TABLE student
    ALTER COLUMN age set default 20;

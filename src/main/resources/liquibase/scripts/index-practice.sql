-- liquibase formatted sql

-- changeset sborisov:1
CREATE INDEX student_name_index on student(name);

-- changeset sborisov:2
CREATE INDEX faculty_name_color_index on faculty(name, color);
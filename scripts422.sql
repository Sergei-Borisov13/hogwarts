create table car(
    id serial primary key,
    brand text not null,
    model text not null,
    price integer not null
);
insert into car(brand, model, price)
values ('BMW', 'M5', 20000000),
       ('VW', 'Golf', 3500000),
       ('FORD', 'FOCUS', 1800000);



create table person(
    id serial primary key,
    name text not null,
    age integer not null,
    driver_license boolean,
    car_id integer not null references car(id)
);

insert into person(name, age, driver_license, car_id)
values ('Ivan', 20, true, 1),
       ('Petr', 25, true, 2),
       ('Sergei', 30, true, 3);

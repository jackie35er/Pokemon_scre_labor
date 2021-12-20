use [db_pokemon_goetz_pils]



drop table if exists Multipliers;
drop table if exists Pokemons;
drop table if exists Types;

create table Types(
    id int primary key,
    name varchar(255),
)

create table Pokemons(
     id int primary key,
     name varchar(255),
     hp int,
     attack int,
     defense int,
     sp_attack int,
     sp_defense int,
     speed int,
     primary_type int foreign key references Types(id),
     secondary_type int foreign key references Types(id)
)

create table Multipliers(
    userType int foreign key references Types(id),
    targetType int foreign key references Types(id),
    multiplier double precision,
    primary key (userType,targetType)
)
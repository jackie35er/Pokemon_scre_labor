use [db_pokemon_goetz_pils]



drop table if exists Multipliers;
drop table if exists Pokemons;
drop table if exists Types;

create table Types(
    id int not null primary key,
    name varchar(255),
)

create table Pokemons(
     id int not null primary key,
     name varchar(255),
     hp int,
     attack int,
     defense int,
     sp_attack int,
     sp_defense int,
     speed int,
     primary_type int not null foreign key references Types(id),
     secondary_type int foreign key references Types(id) -- secondary type can be null
)

create table Multipliers(
    userType int not null foreign key references Types(id),
    targetType int foreign key references Types(id),
    multiplier double precision,
    primary key (userType,targetType)
)

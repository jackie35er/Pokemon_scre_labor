use [db_pokemon_goetz_pils]

drop table if exists Pokemons;

create table Pokemons(
    id int primary key,
    name varchar(255),
    hp int,
    attack int,
    defense int,
    sp_attack int,
    sp_defense int,
    speed int,
)
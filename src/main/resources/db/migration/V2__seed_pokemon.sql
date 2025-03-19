-- TYPE
INSERT INTO types (name) VALUES
('Electric'),
('Grass'),
('Poison'),
('Fire'),
('Water'),
('Normal'),
('Fairy'),
('Rock'),
('Ground'),
('Ghost'),
('Dragon'),
('Dark');

-- POKEMON
INSERT INTO pokemon (name, description, catch_rate, characteristic) VALUES
('Pikachu', 'Electric mouse', 75.0, 'AGILE'),
('Bulbasaur', 'Seed Pokémon', 85.0, 'CALM'),
('Charmander', 'Lizard Pokémon', 70.0, 'BRAVE'),
('Squirtle', 'Tiny Turtle', 80.0, 'CALM'),
('Jigglypuff', 'Balloon Pokémon', 60.0, 'CHEERFUL'),
('Geodude', 'Rock Pokémon', 90.0, 'STURDY'),
('Gastly', 'Gas Pokémon', 65.0, 'SNEAKY'),
('Eevee', 'Evolution Pokémon', 55.0, 'ADAPTABLE'),
('Snorlax', 'Sleeping Pokémon', 25.0, 'LAZY'),
('Dratini', 'Dragon Pokémon', 45.0, 'MYSTIC'),
('Chikorita', 'Leaf Pokémon', 85.0, 'GENTLE'),
('Cyndaquil', 'Fire Mouse', 70.0, 'FIERY'),
('Totodile', 'Big Jaw', 80.0, 'PLAYFUL'),
('Togepi', 'Spike Ball', 50.0, 'CUTE'),
('Mareep', 'Wool Pokémon', 75.0, 'MILD'),
('Houndour', 'Dark Pokémon', 60.0, 'FIERCE'),
('Larvitar', 'Rock Skin', 45.0, 'TOUGH'),
('Treecko', 'Wood Gecko', 80.0, 'QUICK'),
('Torchic', 'Chick Pokémon', 70.0, 'BOLD'),
('Mudkip', 'Mud Fish', 85.0, 'STEADY');

-- RELATIONSHIP (POKEMON & TYPE)
INSERT INTO pokemon_types (pokemon_id, type_id) VALUES
(1, 1),  -- Pikachu: Electric
(2, 2),  -- Bulbasaur: Grass
(2, 3),  -- Bulbasaur: Poison
(3, 4),  -- Charmander: Fire
(4, 5),  -- Squirtle: Water
(5, 6),  -- Jigglypuff: Normal
(5, 7),  -- Jigglypuff: Fairy
(6, 8),  -- Geodude: Rock
(6, 9),  -- Geodude: Ground
(7, 10), -- Gastly: Ghost
(7, 3),  -- Gastly: Poison
(8, 6),  -- Eevee: Normal
(9, 6),  -- Snorlax: Normal
(10, 11), -- Dratini: Dragon
(11, 2), -- Chikorita: Grass
(12, 4), -- Cyndaquil: Fire
(13, 5), -- Totodile: Water
(14, 7), -- Togepi: Fairy
(15, 1), -- Mareep: Electric
(16, 12), -- Houndour: Dark
(16, 4), -- Houndour: Fire
(17, 8), -- Larvitar: Rock
(17, 9), -- Larvitar: Ground
(18, 2), -- Treecko: Grass
(19, 4), -- Torchic: Fire
(20, 5); -- Mudkip: Water
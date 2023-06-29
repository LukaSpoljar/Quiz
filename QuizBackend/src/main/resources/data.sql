-- A file that generates DB after all DB tables have been initialized

-- Generate players:
-- { username: 'štef', password: 'štef_lozinka' }
-- { username: 'luka', password: 'luka_lozinka' }
-- { username: 'ivan', password: 'ivan_lozinka' }
INSERT INTO player (id, username, hashed_password, uuid) values
    (1, 'štef', '5ba723b2aa6416201cfdc5a207520a3fe4b625db25492cfa01d0d2bb97155b32', uuid()),
    (2, 'luka', '19f3f5ab1629cae390a8b959370143f74961c8fbd89ce2a7edabbbd141210166', uuid()),
    (3, 'ivan', '2d2a4c70577a75f07e3201e183e4db1fb176693597b5b804fc74acb78c31d9f9', uuid());

-- Generate Quiz categories
insert into category (id, name) values
    (1, 'General knowledge'),
    (2, 'Sports'),
    (3, 'Cooking'),
    (4, 'People'),
    (5, 'Personal');

-- Generate Quizzes
insert into quiz (id, name, author_id, category_id, uuid) values
    (1, 'Prvi kviz', 1, 1, uuid()),
    (2, 'Drugi kviz', 3, 1, uuid());

-- Generate quiz questions
insert into question (id, text, quiz_id) values
    (1, 'Koji je prvi kviz na kviz aplikaciji?', 1),
    (2, 'Kako se zove vepar sa 5 nogu?', 1),
    (3, 'Koji je drugi kviz na kviz aplikaciji?', 2),
    (4, 'Kako se griju zagorci zimi, a počinje sa ''z''?', 2);

-- Generate quiz answers
insert into answer (id, content, is_correct, question_id) values
    -- Koji je prvi kviz na kviz aplikaciji?
    (1, 'Ovaj', true, 1),
    (2, 'Onaj', false, 1),
    (3, 'Ne znam', false, 1),
    -- Kako se zove vepar sa 5 nogu?
    (4, 'Nepar', false, 2),
    (5, 'Vepar', false, 2),
    (6, 'Petar', true, 2),
    (7, 'Šestar', false, 2),
    -- Koji je drugi kviz na kviz aplikaciji?
    (8, 'Onaj prvi kviz', false, 3),
    (9, 'Ovaj kviz', true, 3),
    (10, 'Ne znam', false, 3),
    -- Kako se griju zagorci zimi, a počinje sa 'z'?
    (11, 'Ne znam', false, 4),
    (12, 'Sa drvima', false, 4),
    (13, 'Zdrvima', true, 4);

-- Generate quiz results
insert into result (id, player_id, quiz_id, correct) values
    (1, 2, 1, 2),
    (2, 3, 1, 1),
    (3, 2, 2, 0),
    (4, 3, 2, 1);

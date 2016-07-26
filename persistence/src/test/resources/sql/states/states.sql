INSERT INTO applications (candidate_id, position_id, channel_id, creation_date)
    SELECT c.id, p.id, ch.id, NOW() FROM
        candidates c, positions p, channels ch
    WHERE
        c.name = 'Candidate A'
        AND p.name = 'Fejlesztő'
        AND ch.name = 'direkt';

INSERT INTO applications (candidate_id, position_id, channel_id, creation_date)
    SELECT c.id, p.id, ch.id, NOW() FROM
        candidates c, positions p, channels ch
    WHERE
        c.name = 'Candidate B'
        AND p.name = 'Fejlesztő'
        AND ch.name = 'direkt';

INSERT INTO applications (candidate_id, position_id, channel_id, creation_date)
    SELECT c.id, p.id, ch.id, NOW() FROM
        candidates c, positions p, channels ch
    WHERE
        c.name = 'Candidate C'
        AND p.name = 'Fejlesztő'
        AND ch.name = 'facebook';

INSERT INTO states_history (application_id, creation_date, state_id)
    VALUES ((SELECT id FROM applications a WHERE a.candidate_id = (SELECT id FROM candidates c WHERE c.name = 'Candidate B')
                                                 AND a.channel_id = (SELECT id FROM channels ch WHERE ch.name = 'direkt')
                                                 AND a.position_id = (SELECT id FROM positions p WHERE p.name = 'Fejlesztő')),
            NOW(),
            (SELECT id FROM states WHERE name = 'firstTest'));

INSERT INTO states_history (application_id, creation_date, state_id)
    VALUES ((SELECT id FROM applications a WHERE a.candidate_id = (SELECT id FROM candidates c WHERE c.name = 'Candidate C')
                                                 AND a.channel_id = (SELECT id FROM channels ch WHERE ch.name = 'facebook')
                                                 AND a.position_id = (SELECT id FROM positions p WHERE p.name = 'Fejlesztő')),
            ADDDATE(NOW(), 2),
            (SELECT id FROM states WHERE name = 'hr'));

INSERT INTO states_history (application_id, creation_date, state_id)
    VALUES ((SELECT id FROM applications a WHERE a.candidate_id = (SELECT id FROM candidates c WHERE c.name = 'Candidate C')
                                                 AND a.channel_id = (SELECT id FROM channels ch WHERE ch.name = 'facebook')
                                                 AND a.position_id = (SELECT id FROM positions p WHERE p.name = 'Fejlesztő')),
            ADDDATE(NOW(), 3),
            (SELECT id FROM states WHERE name = 'coding'));

INSERT INTO states_history (application_id, creation_date, state_id)
    VALUES ((SELECT id FROM applications a WHERE a.candidate_id = (SELECT id FROM candidates c WHERE c.name = 'Candidate C')
                                                 AND a.channel_id = (SELECT id FROM channels ch WHERE ch.name = 'facebook')
                                                 AND a.position_id = (SELECT id FROM positions p WHERE p.name = 'Fejlesztő')),
            ADDDATE(NOW(), 1),
            (SELECT id FROM states WHERE name = 'firstTest'));
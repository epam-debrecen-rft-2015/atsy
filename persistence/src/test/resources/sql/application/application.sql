
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
        c.name = 'Candidate C'
        AND p.name = 'Fejlesztő'
        AND ch.name in ('profession hírdetés', 'profession adatbázis', 'facebook')
    ORDER BY c.id, p.id, ch.id;

INSERT INTO fixed_extension (extension, blocked)
VALUES
    ('bat', false),
    ('cmd', false),
    ('com', false),
    ('cpl', false),
    ('exe', false),
    ('scr', false),
    ('js', false)
ON DUPLICATE KEY UPDATE extension = VALUES(extension);

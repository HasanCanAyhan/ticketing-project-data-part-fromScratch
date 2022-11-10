INSERT INTO roles(insert_date_time, insert_user_id, is_deleted, last_update_date_time, last_update_user_id, description)
VALUES ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, 'Admin'),
       ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, 'Manager'),
       ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, 'Employee');


INSERT INTO users(insert_date_time, insert_user_id, is_deleted, last_update_date_time, last_update_user_id, enabled,
                  first_name, last_name, user_name, gender, phone, role_id)
VALUES ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, true, 'admin', 'admin', 'admin@admin.com', 'MALE', '0000000000', 1),
       ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, true, 'Harold', 'Finch', 'harold@manager.com', 'MALE', '0123456789', 2),
       ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, true, 'Samantha', 'Groves', 'samantha@manager.com', 'MALE', '9876543210', 2),
       ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, true, 'John', 'Reese', 'john@employee.com', 'MALE', '7894561230', 3),
       ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, true, 'Sameen', 'Shaw', 'sameen@employee.com', 'MALE', '0321654987', 3),
       ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, true, 'Grace', 'Hendricks', 'grace@employee.com', 'MALE', '7410258963', 3),
       ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, true, 'Lionel', 'Fusco', 'lionel@employee.com', 'MALE', '3698520147', 3);


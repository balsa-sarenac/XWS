-- USERS

insert into user_table (first_name, last_name, username, address ) values ('Niksa', 'Sarenac', 'niksa', 'luke vuklalovica 10', '$2a$10$N4CZptDrasoEx3IJHL.3ZO1q8xICGMf.EBQY98m.PiR6RjHExRENK', true);
insert into user_table (first_name, last_name, username, address, company_name, business_id) values ('Milica', 'Injac', 'milica', 'luke vuklalovica 12', 'Rent-A-Drive');
insert into user_table (first_name, last_name, username, address, company_name, business_id) values ('Balsa', 'Sarenac', 'bax', 'luke vuklalovica 14', 'Rent for you' );
insert into user_table (first_name, last_name, username, address, company_name, business_id) values ('Vidak', 'Sarenac', 'vidak', 'luke vuklalovica 15', 'Rapid rent' );
insert into user_table (first_name, last_name, username, address) values ('Stefan', 'Kovac', 'stefan', 'luke vuklalovica 16');
insert into user_table (first_name, last_name, username, address) values ('Milos', 'Andric', 'miki', 'luke vuklalovica 17');
insert into user_table (first_name, last_name, username, address) values ('Jovan', 'Vujic', 'vuja', 'luke vuklalovica 18');

-- CARS
insert into car (id, user_id) values (1, 2);
insert into car (id, user_id) values (2, 2);
insert into car (id, user_id) values (3, 2);
insert into car (id, user_id) values (4, 3);
insert into car (id, user_id) values (5, 3);
insert into car (id, user_id) values (6, 3);
insert into car (id, user_id) values (7, 4);
insert into car (id, user_id) values (8, 4);
insert into car (id, user_id) values (9, 5);
insert into car (id, user_id) values (10, 5);
insert into car (id, user_id) values (11, 6);
insert into car (id, user_id) values (12, 6);

-- COMMENTS

-- MESSAGES
insert into message (id, text, date_sent, sender_id, receiver_id) values (1, 'hi', '2020-06-03T21:39:45.618', 2, 3);
insert into message (id, text, date_sent, sender_id, receiver_id) values (2, 'hey', '2020-06-03T21:42:45.618', 3, 2);
insert into message (id, text, date_sent, sender_id, receiver_id) values (3, 'hello', '2020-06-03T21:44:45.618', 2, 5);

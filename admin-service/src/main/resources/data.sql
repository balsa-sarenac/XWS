-- USERS

insert into user_table (id, username, first_name, last_name, address) values (1, 'bax', 'balsa', 'sarenac', 'luke vuklalovica 93');
insert into user_table (id, username, first_name, last_name, address) values (2, 'milica', 'milica', 'injac', 'luke vuklalovica 93');
insert into user_table (id, username, first_name, last_name, address, company_name, business_id) values (3, 'milica', 'milica', 'injac', 'luke vuklalovica 93', 'carovi brt', 'xae12');

-- CARS
insert into car (id, user_id) values (1, 1);
insert into car (id, user_id) values (2, 1);
insert into car (id, user_id) values (3, 2);
insert into car (id, user_id) values (4, 2);
insert into car (id, user_id) values (5, 3);
insert into car (id, user_id) values (6, 3);
insert into car (id, user_id) values (7, 3);

-- COMMENTS

-- MESSAGES
insert into message (id, text, date_sent, sender_id, receiver_id) values (1, 'hi', '2020-06-03T21:39:45.618', 1, 3);
insert into message (id, text, date_sent, sender_id, receiver_id) values (2, 'hey', '2020-06-03T21:42:45.618', 3, 1);
insert into message (id, text, date_sent, sender_id, receiver_id) values (3, 'hello', '2020-06-03T21:44:45.618', 1, 2);

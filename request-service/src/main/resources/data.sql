-- USERS
insert into USER_TABLE (id, username) values (1, 'niksa');
insert into USER_TABLE (id, username) values (2, 'milica');
insert into USER_TABLE (id, username) values (3, 'bax');
insert into USER_TABLE (id, username) values (4, 'vidak');
insert into USER_TABLE (id, username) values (5, 'stefan');
insert into USER_TABLE (id, username) values (6, 'miki');
insert into USER_TABLE (id, username) values (7, 'vuja');

-- ADS
insert into AD (id, user_id) values (1, 2);
insert into AD (id, user_id) values (2, 2);
insert into AD (id, user_id) values (3, 2);
insert into AD (id, user_id) values (4, 3);
insert into AD (id, user_id) values (5, 3);
insert into AD (id, user_id) values (6, 3);

-- RENT BUNDLES
insert into rent_bundle (id, bundle_status) values (1, 'pending');
insert into rent_bundle (id, bundle_status) values (2, 'paid');

-- RENT REQUESTS
insert into RENT_REQUEST (id, status, date_created, pick_up_date, return_date, pick_up_place, ad_id, bundle_id, user_id) values (1, 'pending', '2020-06-03T21:39:45.618', '2020-06-05', '2020-06-15', 'Nevesinje', 1, null, 2);
insert into RENT_REQUEST (id, status, date_created, pick_up_date, return_date, pick_up_place, ad_id, bundle_id, user_id) values (2, 'pending', '2020-06-03T21:39:45.618', '2020-06-09', '2020-06-21', 'Trebinje', 2, 1, 2);
insert into RENT_REQUEST (id, status, date_created, pick_up_date, return_date, pick_up_place, ad_id, bundle_id, user_id) values (3, 'pending', '2020-06-01T21:39:45.618', '2020-06-03', '2020-06-15', 'Nevesinje', 4, 1, 3);
insert into RENT_REQUEST (id, status, date_created, pick_up_date, return_date, pick_up_place, ad_id, bundle_id, user_id) values (4, 'pending', '2020-06-01T21:39:45.618', '2020-06-06', '2020-06-26', 'Trebinje', 2, null, 3);
insert into RENT_REQUEST (id, status, date_created, pick_up_date, return_date, pick_up_place, ad_id, bundle_id, user_id) values (5, 'paid', '2020-06-01T21:39:45.618', '2020-06-03', '2020-06-30', 'Nevesinje', 5, null, 4);
insert into RENT_REQUEST (id, status, date_created, pick_up_date, return_date, pick_up_place, ad_id, bundle_id, user_id) values (6, 'paid', '2020-06-01T21:39:45.618', '2020-06-03', '2020-06-30', 'Novi Sad', 4, 2, 4);
insert into RENT_REQUEST (id, status, date_created, pick_up_date, return_date, pick_up_place, ad_id, bundle_id, user_id) values (7, 'paid', '2020-06-01T21:39:45.618', '2020-06-03', '2020-06-30', 'Novi Sad', 3, 2, 5);

insert into bill (id, user_id, price, paid) values (1, 5, 200, false);
insert into bill (id, user_id, price, paid) values (2, 5, 200, false);
insert into bill (id, user_id, price, paid) values (3, 5, 200, false);
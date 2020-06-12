insert into USER_TABLE (id) values (1);

insert into AD (id, user_id) values (1, 1);
insert into AD (id, user_id) values (2, 1);
insert into AD (id, user_id) values (3, 1);
insert into AD (id, user_id) values (4, 1);
insert into AD (id, user_id) values (5, 1);
insert into AD (id, user_id) values (6, 1);

insert into RENT_REQUEST (id, status, date_created, pick_up_date, return_date, pick_up_place, ad_id, bundle_id, user_id) values (1, 'pending', '2020-06-03T21:39:45.618', '2020-06-05', '2020-06-15', 'Nevesinje', 1, null, 1);
insert into RENT_REQUEST (id, status, date_created, pick_up_date, return_date, pick_up_place, ad_id, bundle_id, user_id) values (2, 'pending', '2020-06-03T21:39:45.618', '2020-06-09', '2020-06-21', 'Trebinje', 2, null, 1);
insert into RENT_REQUEST (id, status, date_created, pick_up_date, return_date, pick_up_place, ad_id, bundle_id, user_id) values (3, 'pending', '2020-06-01T21:39:45.618', '2020-06-03', '2020-06-15', 'Nevesinje', 4, null, 1);
insert into RENT_REQUEST (id, status, date_created, pick_up_date, return_date, pick_up_place, ad_id, bundle_id, user_id) values (4, 'pending', '2020-06-01T21:39:45.618', '2020-06-06', '2020-06-26', 'Trebinje', 2, null, 1);
insert into RENT_REQUEST (id, status, date_created, pick_up_date, return_date, pick_up_place, ad_id, bundle_id, user_id) values (5, 'pending', '2020-06-01T21:39:45.618', '2020-06-03', '2020-06-30', 'Nevesinje', 5, null, 1);
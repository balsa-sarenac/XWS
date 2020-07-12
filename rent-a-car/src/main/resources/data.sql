-- ROLES
insert into role (name, id) values ('ROLE_ADMIN', 1);
insert into role (name, id) values ('ROLE_AGENT', 2);
insert into role (name, id) values ('ROLE_USER', 3);

--Privileges

insert into privilege  (name, id) values ('POST_ADS', 1);

-- USERS
insert into user_table (id, username, first_name, last_name, address, password, enabled, account_non_expired, account_non_locked, credentials_non_expired, is_admin, status, num_can_req, last_password_reset_date)
values (1, 'bax', 'balsa', 'sarenac', 'luke vuklalovica 93', '$2a$10$U9jvaVCEV.48aHuR2vck/emgRLXJ3d5jleYyCTwdO/X9fmDtZ0bgG', true, false, false, false, false, 0, 3, '2020-06-09T21:39:42.000');
insert into user_table (id, username, first_name, last_name, address, password, enabled, account_non_expired, account_non_locked, credentials_non_expired, is_admin, status, num_can_req, last_password_reset_date)
values (2, 'mica', 'milica', 'injac', 'luke vuklalovica 93', '$2a$10$U9jvaVCEV.48aHuR2vck/emgRLXJ3d5jleYyCTwdO/X9fmDtZ0bgG', true, false, false, false, false, 0, 0, '2020-06-09T21:39:42.000');
insert into user_table (id, username, first_name, last_name, address, company_name, business_id, password, enabled, account_non_expired, account_non_locked, credentials_non_expired, is_admin, status, num_can_req, last_password_reset_date)
values (3, 'milica', 'milica', 'injac', 'luke vuklalovica 93', 'firma wow low', 'xae12', '$2a$10$U9jvaVCEV.48aHuR2vck/emgRLXJ3d5jleYyCTwdO/X9fmDtZ0bgG', true, false, false, false, false, 0, 1, '2020-06-09T21:39:42.000');
insert into user_table (id, username, first_name, last_name, address, password, enabled, account_non_expired, account_non_locked, credentials_non_expired, is_admin, status, num_can_req)
values (4, 'balsa', 'sarenac', 'bax', 'tamo daleko', '$2a$10$U9jvaVCEV.48aHuR2vck/emgRLXJ3d5jleYyCTwdO/X9fmDtZ0bgG', true, false, false, false, false, 0 ,4);
insert into user_table (id, username, first_name, last_name, address, password, enabled, account_non_expired, account_non_locked, credentials_non_expired, is_admin, status, num_can_req, last_password_reset_date)
values (5, 'admin', 'admin', 'admin', 'admin', '$2a$10$U9jvaVCEV.48aHuR2vck/emgRLXJ3d5jleYyCTwdO/X9fmDtZ0bgG', true, false, false, false, true, 0 ,0, '2020-06-09T21:39:42.000');

insert into users_roles (user_id, role_id) values (1, 2);
insert into users_roles (user_id, role_id) values (2, 3);
insert into users_roles (user_id, role_id) values (3, 2);
insert into users_roles (user_id, role_id) values (4, 3);
insert into users_roles (user_id, role_id) values (5, 1);

insert into users_privileges (user_id, privilege_id) values (1, 1);
insert into users_privileges (user_id, privilege_id) values (2, 1);
insert into users_privileges (user_id, privilege_id) values (3, 1);
insert into users_privileges (user_id, privilege_id) values (4, 1);

-- MESSAGES
insert into message (id, text, date_sent, sender_id, receiver_id) values (1, 'hi', '2020-06-03T21:39:45.618', 1, 4);
insert into message (id, text, date_sent, sender_id, receiver_id) values (2, 'hey', '2020-06-03T21:42:45.618', 4, 1);
insert into message (id, text, date_sent, sender_id, receiver_id) values (3, 'hello', '2020-06-03T21:44:45.618', 4, 2);


--Marke
insert into MARK (name ) values ('Audi');
insert into MARK (name ) values ('Bmw');
insert into MARK (name ) values ('Mercedes');
insert into MARK (name ) values ('Ford');
insert into MARK (name ) values ('Opel');

---Modeli
insert into MODEL (name, mark_id) values ('Q7', 1);
insert into MODEL (name, mark_id) values ('A4', 1);
insert into MODEL (name, mark_id) values ('Q5', 1);
insert into MODEL (name, mark_id) values ('S6', 1);
insert into MODEL (name, mark_id) values ('Gran Coupe', 2);
insert into MODEL (name, mark_id) values ('Sedan', 2);
insert into MODEL (name, mark_id) values ('X7', 2);
insert into MODEL (name, mark_id) values ('X4', 2);
insert into MODEL (name, mark_id) values ('G 550 SUV', 3);
insert into MODEL (name, mark_id) values ('AMG GT', 3);
insert into MODEL (name, mark_id) values ('C112', 3);
insert into MODEL (name, mark_id) values ('F700', 3);
insert into MODEL (name, mark_id) values ('Focus', 4);
insert into MODEL (name, mark_id) values ('Mondeo', 4);
insert into MODEL (name, mark_id) values ('Mustang', 4);
insert into MODEL (name, mark_id) values ('Explorer', 4);
insert into MODEL (name, mark_id) values ('Astra', 5);
insert into MODEL (name, mark_id) values ('Insignia', 5);
insert into MODEL (name, mark_id) values ('Corsa',5);
insert into MODEL (name, mark_id) values ('Meriva', 5);

--Fuel
insert into FUEL (type) values ('Benzin');
insert into FUEL (type) values ('Dizel');
insert into FUEL (type) values ('Electro');
insert into FUEL (type) values ('Hybrid');

--Klase
insert into CAR_CLASS (name ) values ('City');
insert into CAR_CLASS (name ) values ('Luxury');
insert into CAR_CLASS (name ) values ('Caravan');
insert into CAR_CLASS (name ) values ('Suv');

--Gearboxes
insert into GEARBOX (type) values ('Automatic');
insert into GEARBOX (type) values ('Manual');

insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days, user_id) values (7, 1, 20, 10, 20, 1);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days, user_id) values (8, 2, 30, 11, 30, 1);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days, user_id) values (9, 1, 40, 12, 20, 2);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days, user_id) values (11, 2, 30, 10, 25, 2);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days, user_id) values (7, 1, 20, 10, 20, 3);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days, user_id) values (8, 2, 30, 11, 30, 3);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days, user_id) values (9, 1, 40, 12, 20, 4);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days, user_id) values (7, 1, 20, 10, 20, 4);


insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id, owner_id, number_of_grades, overall_grade) values (100010, 0,false, 1,2,1,1,1,1,0,4.5);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id, owner_id, number_of_grades, overall_grade) values (25555, 0,false, 1,2,2,2,2,1,0,0.0);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id, owner_id, number_of_grades, overall_grade) values (100100, 1,false, 2,1,3,3,2,1,0,0.0);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id, owner_id, number_of_grades, overall_grade) values (250203, 2,false, 2,2,1,4,1,3,0,0.0);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id, owner_id, number_of_grades, overall_grade) values (10503, 0,false, 3,2,3,5,1,3,0,0.0);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id, owner_id, number_of_grades, overall_grade) values (150000, 2,false, 4,1,2,7,2,3,0,0.0);
insert into AD (cdw_available, pick_up_place, from_date, to_date, allowed_kilometrage, car_id, price_list_id, user_id, ref_id) values (true, 'Nevesinje', '2020-08-01T21:39:45.618', '2020-08-19T21:39:45.618', 350, 1,1,1,1);
insert into AD (cdw_available, pick_up_place, from_date, to_date, allowed_kilometrage, car_id, price_list_id, user_id, ref_id) values (true, 'Trebinje', '2020-08-01T21:39:45.618', '2020-08-19T21:39:45.618', 100, 2,2,1,2);
insert into AD (cdw_available, pick_up_place, from_date, to_date, allowed_kilometrage, car_id, price_list_id, user_id, ref_id) values (false, 'BanjaLuka', '2020-08-02T21:39:45.618', '2020-08-10T21:39:45.618', 200, 3,2,1,3);
insert into AD (cdw_available, pick_up_place, from_date, to_date, allowed_kilometrage, car_id, price_list_id, user_id, ref_id) values (false, 'Nevesinje', '2020-08-02T21:39:45.618', '2020-08-10T21:39:45.618', 300, 4,4,3,4);
insert into AD (cdw_available, pick_up_place, from_date, to_date, allowed_kilometrage, car_id, price_list_id, user_id, ref_id) values (true, 'Nevesinje', '2020-08-03T21:39:45.618', '2020-08-11T21:39:45.618', 0, 5,3,3,5);
insert into AD (cdw_available, pick_up_place, from_date, to_date, allowed_kilometrage, car_id, price_list_id, user_id, ref_id) values (true, 'Nevesinje', '2020-08-03T21:39:45.618', '2020-08-11T21:39:45.618', 250, 6,4,3,6);



-- REQUESTS

-- RENT BUNDLES
insert into rent_bundle (id, bundle_status) values (1, 'pending');
insert into rent_bundle (id, bundle_status) values (2, 'paid');

-- RENT REQUESTS
insert into RENT_REQUEST (id, status, date_created, pick_up_date, return_date, pick_up_place, ad_id, bundle_id, user_id) values (1, 'pending', '2020-08-03T21:39:45.618', '2020-08-05', '2020-08-15', 'Nevesinje', 1, null, 1);
insert into RENT_REQUEST (id, status, date_created, pick_up_date, return_date, pick_up_place, ad_id, bundle_id, user_id) values (2, 'pending', '2020-08-03T21:39:45.618', '2020-08-09', '2020-08-21', 'Trebinje', 2, 1, 1);
insert into RENT_REQUEST (id, status, date_created, pick_up_date, return_date, pick_up_place, ad_id, bundle_id, user_id) values (3, 'pending', '2020-08-01T21:39:45.618', '2020-08-03', '2020-08-15', 'Nevesinje', 4, 1, 1);
insert into RENT_REQUEST (id, status, date_created, pick_up_date, return_date, pick_up_place, ad_id, bundle_id, user_id) values (4, 'pending', '2020-08-01T21:39:45.618', '2020-08-08', '2020-08-26', 'Trebinje', 1, null, 1);
insert into RENT_REQUEST (id, status, date_created, pick_up_date, return_date, pick_up_place, ad_id, bundle_id, user_id) values (5, 'paid', '2020-08-01T21:39:45.618', '2020-08-03', '2020-08-12', 'Nevesinje', 5, null, 1);
insert into RENT_REQUEST (id, status, date_created, pick_up_date, return_date, pick_up_place, ad_id, bundle_id, user_id) values (6, 'paid', '2020-08-01T21:39:45.618', '2020-08-03', '2020-08-03', 'Novi Sad', 5, 2, 1);
insert into RENT_REQUEST (id, status, date_created, pick_up_date, return_date, pick_up_place, ad_id, bundle_id, user_id) values (7, 'paid', '2020-08-01T21:39:45.618', '2020-08-03', '2020-09-02', 'Novi Sad', 1, 2, 1);
insert into RENT_REQUEST (id, status, date_created, pick_up_date, return_date, pick_up_place, ad_id, bundle_id, user_id) values (8, 'paid', '2020-05-01T21:39:45.618', '2020-05-03', '2020-06-02', 'Novi Sad', 1, 2, 1);
insert into RENT_REQUEST (id, status, date_created, pick_up_date, return_date, pick_up_place, ad_id, bundle_id, user_id) values (9, 'paid', '2020-04-01T21:39:45.618', '2020-05-03', '2020-06-02', 'Novi Sad', 1, 2, 1);
insert into RENT_REQUEST (id, status, date_created, pick_up_date, return_date, pick_up_place, ad_id, bundle_id, user_id) values (10, 'paid', '2020-05-01T21:39:45.618', '2020-05-03', '2020-06-02', 'Novi Sad', 1, 2, 1);
insert into RENT_REQUEST (id, status, date_created, pick_up_date, return_date, pick_up_place, ad_id, bundle_id, user_id) values (11, 'paid', '2020-05-01T21:39:45.618', '2020-05-03', '2020-06-02', 'Novi Sad', 5, 2, 1);


--COMMENTS
insert into COMMENT (text, approved, user_id, car_id, ad_id) values ('Odlicno sve, monogo mi se svidja', true, 1, 5, 5);
insert into COMMENT (text, approved, user_id, car_id, ad_id) values ('Preporucujem', true, 2, 6, 6);
insert into COMMENT (text, approved, user_id, car_id, ad_id) values ('Sve korektno', true, 2, 2, 2);
insert into COMMENT (text, approved, user_id, car_id, ad_id) values ('Odlicno auto', true, 4, 3, 3);
insert into COMMENT (text, approved, user_id, car_id, ad_id) values ('Zadovoljan sam uslugom', true, 4, 3, 3);
insert into COMMENT (text, approved, user_id, car_id, ad_id) values ('Mnogo brz auto!!!11', true, 4, 4, 4);
insert into COMMENT (text, approved, user_id, car_id, ad_id) values ('Mnogo mi se svidja ovaj automobil', false, 1, 4, 4);
insert into COMMENT (text, approved, user_id, car_id, ad_id) values ('Preporucujem svakom ko voli udobnu voznju', false, 4, 6, 6);

insert into GRADE (grade, user_id, car_id, ad_id) values (5, 3, 1, 1);
insert into GRADE (grade, user_id, car_id, ad_id) values (4, 2, 1, 1);

delete from users_privileges where user_id = 1 and privilege_id = 1;
insert into bill (id, user_id, price, paid) values (1, 1, 200, false);
insert into bill (id, user_id, price, paid) values (2, 1, 200, false);
insert into bill (id, user_id, price, paid) values (3, 1, 200, false);
insert into user_table (first_name, last_name, username, password, is_enabled) values ('Niksa', 'Sarenac', 'niksa', '$2a$10$N4CZptDrasoEx3IJHL.3ZO1q8xICGMf.EBQY98m.PiR6RjHExRENK', true);
insert into user_table (first_name, last_name, username, password, is_enabled) values ('Milica', 'Injac', 'milica', '$2a$10$N4CZptDrasoEx3IJHL.3ZO1q8xICGMf.EBQY98m.PiR6RjHExRENK', true);
insert into user_table (first_name, last_name, username, password, is_enabled) values ('Balsa', 'Sarenac', 'bax',  '$2a$10$N4CZptDrasoEx3IJHL.3ZO1q8xICGMf.EBQY98m.PiR6RjHExRENK', true);
insert into user_table (first_name, last_name, username, password, is_enabled) values ('Vidak', 'Sarenac', 'vidak', '$2a$10$N4CZptDrasoEx3IJHL.3ZO1q8xICGMf.EBQY98m.PiR6RjHExRENK', true);
insert into user_table (first_name, last_name, username, password, is_enabled) values ('Stefan', 'Kovac', 'stefan', '$2a$10$N4CZptDrasoEx3IJHL.3ZO1q8xICGMf.EBQY98m.PiR6RjHExRENK', true);
insert into user_table (first_name, last_name, username, password, is_enabled) values ('Milos', 'Andric', 'miki', '$2a$10$N4CZptDrasoEx3IJHL.3ZO1q8xICGMf.EBQY98m.PiR6RjHExRENK', true);
insert into user_table (first_name, last_name, username, password, is_enabled) values ('Jovan', 'Vujic', 'vuja',  '$2a$10$N4CZptDrasoEx3IJHL.3ZO1q8xICGMf.EBQY98m.PiR6RjHExRENK', false);


--Price lists
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days, user_id) values (7, 1, 20, 10, 20, 2);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days, user_id) values (8, 2, 30, 11, 30, 2);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days, user_id) values (9, 1, 40, 12, 20, 2);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days, user_id) values (11, 2, 30, 10, 25, 3);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days, user_id) values (7, 1, 20, 10, 20, 3);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days, user_id) values (8, 2, 30, 11, 30, 3);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days, user_id) values (9, 1, 40, 12, 20, 4);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days, user_id) values (11, 2, 30, 10, 25, 4);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days, user_id) values (7, 1, 20, 10, 20, 4);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days, user_id) values (8, 2, 30, 11, 30, 5);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days, user_id) values (9, 1, 40, 12, 20, 5);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days, user_id) values (11, 2, 30, 10, 25, 5);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days, user_id) values (7, 1, 20, 10, 20, 6);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days, user_id) values (8, 2, 30, 11, 30, 6);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days, user_id) values (9, 1, 40, 12, 20, 6);

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

insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id, owner_id, number_of_grades, overall_grade) values (100010, 0,false, 1,1,1,1,1,2,2,4.5);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id, owner_id, number_of_grades, overall_grade) values (25555, 0,false, 2,2,2,5,2,2,1,4);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id, owner_id, number_of_grades, overall_grade) values (100130, 1,true, 3,3,3,9,1,2,1,5);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id, owner_id, number_of_grades, overall_grade) values (250203, 2,false, 4,4,1,13,2,3,1,5);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id, owner_id, number_of_grades, overall_grade) values (103503, 0,true, 5,1,2,17,1,3,1,3);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id, owner_id, number_of_grades, overall_grade) values (150003, 2,false, 1,1,2,2,2,3,2,5);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id, owner_id, number_of_grades, overall_grade) values (100010, 1,true, 2,2,1,6,1,4,2,4);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id, owner_id, number_of_grades, overall_grade) values (25455, 2,false, 3,2,2,10,2,4,1,5);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id, owner_id, number_of_grades, overall_grade) values (100108, 1,false, 4,1,3,14,2,5,2,3);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id, owner_id, number_of_grades, overall_grade) values (250203, 2,false, 5,2,1,18,1,5,1,5);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id, owner_id, number_of_grades, overall_grade) values (12503, 0,false, 1,2,3,3,1,6,6,4);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id, owner_id, number_of_grades, overall_grade) values (150200, 2,false, 2,1,2,7,2,6,3,4);
insert into AD (cdw_available, pick_up_place, from_date, to_date, allowed_kilometrage, car_id, price_list_id, user_id) values (true, 'Nevesinje', '2020-06-01T21:39:45.618', '2020-06-19T21:39:45.618', 350, 1,1,2);
insert into AD (cdw_available, pick_up_place, from_date, to_date, allowed_kilometrage, car_id, price_list_id, user_id) values (true, 'Trebinje', '2020-06-01T21:39:45.618', '2020-06-19T21:39:45.618', 100, 2,2,2);
insert into AD (cdw_available, pick_up_place, from_date, to_date, allowed_kilometrage, car_id, price_list_id, user_id) values (false, 'BanjaLuka', '2020-06-02T21:39:45.618', '2020-06-10T21:39:45.618', 200, 3,3,2);
insert into AD (cdw_available, pick_up_place, from_date, to_date, allowed_kilometrage, car_id, price_list_id, user_id) values (false, 'Nevesinje', '2020-06-02T21:39:45.618', '2020-06-10T21:39:45.618', 300, 4,4,3);
insert into AD (cdw_available, pick_up_place, from_date, to_date, allowed_kilometrage, car_id, price_list_id, user_id) values (true, 'Nevesinje', '2020-06-03T21:39:45.618', '2020-06-11T21:39:45.618', 0, 5,5,3);
insert into AD (cdw_available, pick_up_place, from_date, to_date, allowed_kilometrage, car_id, price_list_id, user_id) values (true, 'Nevesinje', '2020-06-03T21:39:45.618', '2020-06-11T21:39:45.618', 250, 6,6,3);

insert into GRADE (grade, user_id, car_id, ad_id) values (5, 6, 1, 1);
insert into GRADE (grade, user_id, car_id, ad_id) values (4, 5, 2, 5);
insert into GRADE (grade, user_id, car_id, ad_id) values (3, 2, 5, 5);
insert into GRADE (grade, user_id, car_id, ad_id) values (5, 6, 6, 6);
insert into GRADE (grade, user_id, car_id, ad_id) values (5, 5, 6, 6);
insert into GRADE (grade, user_id, car_id, ad_id) values (5, 3, 3, 5);
insert into GRADE (grade, user_id, car_id, ad_id) values (5, 6, 7, 6);
insert into GRADE (grade, user_id, car_id, ad_id) values (3, 2, 7, 6);

insert into COMMENT (text, approved, user_id, car_id, ad_id) values ('Odlicno sve, monogo mi se svidja', true, 3, 1, 1);
insert into COMMENT (text, approved, user_id, car_id, ad_id) values ('Preporucujem', true, 4, 2, 2);
insert into COMMENT (text, approved, user_id, car_id, ad_id) values ('Sve korektno', true, 5, 2, 2);
insert into COMMENT (text, approved, user_id, car_id, ad_id) values ('Odlicno auto', true, 6, 3, 3);
insert into COMMENT (text, approved, user_id, car_id, ad_id) values ('Zadovoljan sam uslugom', true, 2, 4, 4);
insert into COMMENT (text, approved, user_id, car_id, ad_id) values ('Mnogo brz auto!!!11', true, 4, 5, 5);
insert into COMMENT (text, approved, user_id, car_id, ad_id) values ('Mnogo mi se svidja ovaj automobil', false, 6, 4, 4);
insert into COMMENT (text, approved, user_id, car_id, ad_id) values ('Preporucujem svakom ko voli udobnu voznju', false, 5, 5, 5);
insert into COMMENT (text, approved, user_id, car_id, ad_id) values ('Zadovoljan sam uslugom', true, 2, 6, 6);
insert into COMMENT (text, approved, user_id, car_id, ad_id) values ('Mnogo brz auto!!!11', true, 4, 6, 6);
insert into COMMENT (text, approved, user_id, car_id, ad_id) values ('Mnogo mi se svidja ovaj automobil', false, 5, 4, 4);
insert into COMMENT (text, approved, user_id, car_id, ad_id) values ('Preporucujem svakom ko voli udobnu voznju', false, 5, 5, 5);





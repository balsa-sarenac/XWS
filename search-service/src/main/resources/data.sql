--Price lists
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days) values (7, 1, 20, 10, 20);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days) values (8, 2, 30, 11, 30);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days) values (9, 1, 40, 12, 20);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days) values (11, 2, 30, 10, 25);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days) values (7, 1, 20, 10, 20);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days) values (8, 2, 30, 11, 30);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days) values (9, 1, 40, 12, 20);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days) values (11, 2, 30, 10, 25);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days) values (7, 1, 20, 10, 20);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days) values (8, 2, 30, 11, 30);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days) values (9, 1, 40, 12, 20);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days) values (11, 2, 30, 10, 25);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days) values (7, 1, 20, 10, 20);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days) values (8, 2, 30, 11, 30);
insert into PRICE_LIST (per_day, extra_kilometrage, cdw, discount, discount_days) values (9, 1, 40, 12, 20);

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
insert into CAR_CLASS (name) values ('City');
insert into CAR_CLASS (name) values ('Luxury');
insert into CAR_CLASS (name) values ('Caravan');
insert into CAR_CLASS (name) values ('Suv');

--Gearboxes
insert into GEARBOX (type) values ('Automatic');
insert into GEARBOX (type) values ('Manual');

--Cars
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id,  number_of_grades, overall_grade) values (100010, 0,false, 1,1,1,1,1,2,4.5);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id,  number_of_grades, overall_grade) values (25555, 0,false, 2,2,2,5,2,1,4);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id,  number_of_grades, overall_grade) values (100130, 1,true, 3,3,3,9,1,1,5);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id,  number_of_grades, overall_grade) values (250203, 2,false, 4,4,1,13,2,1,5);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id,  number_of_grades, overall_grade) values (103503, 0,true, 5,1,2,17,1,1,3);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id,  number_of_grades, overall_grade) values (150003, 2,false, 1,1,2,2,2,2,5);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id,  number_of_grades, overall_grade) values (100010, 1,true, 2,2,1,6,1,2,4);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id,  number_of_grades, overall_grade) values (25455, 2,false, 3,2,2,10,2,1,5);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id,  number_of_grades, overall_grade) values (100108, 1,false, 4,1,3,14,2,2,3);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id,  number_of_grades, overall_grade) values (250203, 2,false, 5,2,1,18,1,1,5);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id,  number_of_grades, overall_grade) values (12503, 0,false, 1,2,3,3,1,6,4);
insert into CAR (kilometrage, number_of_child_seats, has_android,  mark_id, car_class_id, fuel_id, model_id, gearbox_id,  number_of_grades, overall_grade) values (150200, 2,false, 2,1,2,7,2,3,4);

--Ads
insert into AD (cdw_available, pick_up_place, from_date, to_date, allowed_kilometrage, car_id, price_list_id) values (true, 'Nevesinje', '2020-07-01T21:39:45.618-08:00', '2020-08-19T21:39:45.618-08:00', 350, 1,1);
insert into AD (cdw_available, pick_up_place, from_date, to_date, allowed_kilometrage, car_id, price_list_id) values (true, 'Trebinje', '2020-07-01T21:39:45.618-08:00', '2020-08-19T21:39:45.618-08:00', 100, 2,2);
insert into AD (cdw_available, pick_up_place, from_date, to_date, allowed_kilometrage, car_id, price_list_id) values (false, 'BanjaLuka', '2020-07-02T21:39:45.618-08:00', '2020-08-10T21:39:45.618-08:00', 200, 3,3);
insert into AD (cdw_available, pick_up_place, from_date, to_date, allowed_kilometrage, car_id, price_list_id) values (false, 'Nevesinje', '2020-07-02T21:39:45.618-08:00', '2020-08-10T21:39:45.618-08:00', 300, 4,4);
insert into AD (cdw_available, pick_up_place, from_date, to_date, allowed_kilometrage, car_id, price_list_id) values (true, 'Nevesinje', '2020-07-03T21:39:45.618-08:00', '2020-08-11T21:39:45.618-08:00', 0, 5,2);
insert into AD (cdw_available, pick_up_place, from_date, to_date, allowed_kilometrage, car_id, price_list_id) values (true, 'Nevesinje', '2020-07-03T21:39:45.618-08:00', '2020-08-11T21:39:45.618-08:00', 250, 6,4);
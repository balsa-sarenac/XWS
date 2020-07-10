-- ROLES
insert into role (name, id) values ('ROLE_ADMIN', 1);
insert into role (name, id) values ('ROLE_AGENT', 2);
insert into role (name, id) values ('ROLE_USER', 3);

-- PERMISSIONS
insert into privilege (name, id) values ('POST_ADS', 1);
insert into privilege (name, id) values ('POST_COMMENTS', 2);
insert into privilege (name, id) values ('POST_REQUESTS', 3);


insert into user_table (first_name, last_name, username, address, company_name, business_id, email, password, enabled, account_non_expired, account_non_locked, credentials_non_expired, is_admin, status, num_can_req, last_password_reset_date) values ('Niksa', 'Sarenac', 'niksa', 'luke vukajlovica 10', '', '', 'niksa@gmail.com', '$2a$10$N4CZptDrasoEx3IJHL.3ZO1q8xICGMf.EBQY98m.PiR6RjHExRENK', true, false, false, false, true, 0, 0, null);
insert into user_table (first_name, last_name, username, address, company_name, business_id, email, password, enabled, account_non_expired, account_non_locked, credentials_non_expired, is_admin, status, num_can_req, last_password_reset_date) values ('Milica', 'Injac', 'milica', 'luke vukajlovica 12', 'Rent-A-Drive', '0123456', 'milica@gmail.com', '$2a$10$N4CZptDrasoEx3IJHL.3ZO1q8xICGMf.EBQY98m.PiR6RjHExRENK', true, false, false, false, false, 0, 1, null);
insert into user_table (first_name, last_name, username, address, company_name, business_id, email, password, enabled, account_non_expired, account_non_locked, credentials_non_expired, is_admin, status, num_can_req, last_password_reset_date) values ('Balsa', 'Sarenac', 'bax', 'luke vukajlovica 14', 'Rent for you', '0123457', 'bax@gmail.com', '$2a$10$N4CZptDrasoEx3IJHL.3ZO1q8xICGMf.EBQY98m.PiR6RjHExRENK', true, false, false, false, false, 0, 2, null);
insert into user_table (first_name, last_name, username, address, company_name, business_id, email, password, enabled, account_non_expired, account_non_locked, credentials_non_expired, is_admin, status, num_can_req, last_password_reset_date) values ('Vidak', 'Sarenac', 'vidak', 'luke vukajlovica 15', 'Rapid rent', '0123458', 'vidak@gmail.com', '$2a$10$N4CZptDrasoEx3IJHL.3ZO1q8xICGMf.EBQY98m.PiR6RjHExRENK', true, false, false, false, false, 0, 3, null );
insert into user_table (first_name, last_name, username, address, company_name, business_id, email, password, enabled, account_non_expired, account_non_locked, credentials_non_expired, is_admin, status, num_can_req, last_password_reset_date) values ('Stefan', 'Kovac', 'stefan', 'luke vukajlovica 16', '', '', 'stefan@gmail.com', '$2a$10$N4CZptDrasoEx3IJHL.3ZO1q8xICGMf.EBQY98m.PiR6RjHExRENK', true, false, false, false, false, 0, 2, null );
insert into user_table (first_name, last_name, username, address, company_name, business_id, email, password, enabled, account_non_expired, account_non_locked, credentials_non_expired, is_admin, status, num_can_req, last_password_reset_date) values ('Milos', 'Andric', 'miki', 'luke vukajlovica 17', '', '', 'miki@gmail.com', '$2a$10$N4CZptDrasoEx3IJHL.3ZO1q8xICGMf.EBQY98m.PiR6RjHExRENK', true, false, false, false, false, 0, 1, null );
insert into user_table (first_name, last_name, username, address, company_name, business_id, email, password, enabled, account_non_expired, account_non_locked, credentials_non_expired, is_admin, status, num_can_req, last_password_reset_date) values ('Jovan', 'Vujic', 'vuja', 'luke vukajlovica 18', '', '', 'vuja@gmail.com', '$2a$10$N4CZptDrasoEx3IJHL.3ZO1q8xICGMf.EBQY98m.PiR6RjHExRENK', false, false, false, false, false, 0, 1, null);

insert into users_roles (user_id, role_id) values (1, 1);
insert into users_roles (user_id, role_id) values (2, 2);
insert into users_roles (user_id, role_id) values (3, 2);
insert into users_roles (user_id, role_id) values (4, 2);
insert into users_roles (user_id, role_id) values (5, 3);
insert into users_roles (user_id, role_id) values (6, 3);
insert into users_roles (user_id, role_id) values (7, 3);


insert into users_privileges (user_id, privilege_id) values (1, 1);
insert into users_privileges (user_id, privilege_id) values (2, 1);
insert into users_privileges (user_id, privilege_id) values (3, 1);
insert into users_privileges (user_id, privilege_id) values (4, 1);
insert into users_privileges (user_id, privilege_id) values (5, 1);
insert into users_privileges (user_id, privilege_id) values (6, 1);
insert into users_privileges (user_id, privilege_id) values (7, 1);


delete from users_privileges where user_id = 5 and privilege_id = 1;

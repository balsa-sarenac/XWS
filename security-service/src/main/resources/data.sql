-- ROLES
insert into role (name, id) values ('ROLE_ADMIN', 1);
insert into role (name, id) values ('ROLE_REGISTERED_USER', 2);
insert into role (name, id) values ('ROLE_AGENT', 3);
insert into role (name, id) values ('ROLE_USER', 4);

-- PERMISSIONS
insert into privilege (name, id) values ('POST_ADS', 1);
insert into privilege (name, id) values ('POST_COMMENTS', 2);
insert into privilege (name, id) values ('POST_REQUESTS', 3);

insert into roles_privileges (role_id, privilege_id) values (1, 1);
insert into roles_privileges (role_id, privilege_id) values (1, 2);
insert into roles_privileges (role_id, privilege_id) values (1, 3);
insert into roles_privileges (role_id, privilege_id) values (2, 1);
insert into roles_privileges (role_id, privilege_id) values (2, 2);
insert into roles_privileges (role_id, privilege_id) values (2, 3);
insert into roles_privileges (role_id, privilege_id) values (3, 1);
insert into roles_privileges (role_id, privilege_id) values (3, 2);

insert into user_table (first_name, last_name, username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired, is_admin) values ('Niksa', 'Sarenac', 'niksa', '$2a$10$N4CZptDrasoEx3IJHL.3ZO1q8xICGMf.EBQY98m.PiR6RjHExRENK', true, false, false, false, false);
insert into user_table (first_name, last_name, username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired, is_admin) values ('Milica', 'Injac', 'milica', '$2a$10$N4CZptDrasoEx3IJHL.3ZO1q8xICGMf.EBQY98m.PiR6RjHExRENK', true, false, false, false, true);
insert into user_table (first_name, last_name, username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired, is_admin) values ('Makro', 'Markovic', 'marko', '$2a$10$N4CZptDrasoEx3IJHL.3ZO1q8xICGMf.EBQY98m.PiR6RjHExRENK', true, false, false, false, false);
insert into user_table (first_name, last_name, username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired, is_admin) values ('Priste', 'Liste', 'pris', '$2a$10$N4CZptDrasoEx3IJHL.3ZO1q8xICGMf.EBQY98m.PiR6RjHExRENK', true, false, false, false, false);
insert into user_table (first_name, last_name, username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired, is_admin) values ('Stefan', 'Kovac', 'stefan', '$2a$10$N4CZptDrasoEx3IJHL.3ZO1q8xICGMf.EBQY98m.PiR6RjHExRENK', true, false, false, false, false);
insert into user_table (first_name, last_name, username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired, is_admin) values ('Milos', 'Andric', 'miki', '$2a$10$N4CZptDrasoEx3IJHL.3ZO1q8xICGMf.EBQY98m.PiR6RjHExRENK', true, false, false, false, false);

insert into users_roles (user_id, role_id) values (1, 1);
insert into users_roles (user_id, role_id) values (2, 1);
insert into users_roles (user_id, role_id) values (3, 2);
insert into users_roles (user_id, role_id) values (4, 2);
insert into users_roles (user_id, role_id) values (5, 3);
insert into users_roles (user_id, role_id) values (6, 4);
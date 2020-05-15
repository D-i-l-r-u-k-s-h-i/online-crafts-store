INSERT INTO role VALUES (1,'ROLE_ADMIN'), (2,'ROLE_CUSTOMER'),(3,'ROLE_CRAFT_CREATOR') ON CONFLICT DO NOTHING;

INSERT INTO all_users (id,password,username,role_id) VALUES (1,'$2a$10$2dkwQoyBqk412HyycaHMheltXwaM8mKrH9wjPQNgJvscDqd0bNfXa','Dilly',1) ON CONFLICT DO NOTHING;

INSERT INTO admin (admin_id,username,user_id,email) VALUES (1,'Dilly',1,'d@gmail.com') ON CONFLICT DO NOTHING;
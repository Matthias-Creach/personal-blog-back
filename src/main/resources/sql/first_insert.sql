insert into roles (name) values 
('ROLE_USER'),
('ROLE_ADMIN');

insert into articles (title, content, is_private, release_date, user_id) values
('Article 1', 'Contenu...', false, now(), 1),
('Article 2', 'Contenu...', true, now(), 1);


insert into comments (content, date, user_id, article_id) values
('Commentaire 1...', now(), 1, 1),
('Commentaire 2...', now(), 1, 2);

insert into folders(path, name) values
('root/', 'root');

insert into folders(path, name, parent_path) values
('root/images/',   'images',   'root/'),
('root/users/',    'users',    'root/'),
('root/articles/', 'articles', 'root/');
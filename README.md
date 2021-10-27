Repositório pro desafio techlead

Frameworkd ustilizados: #Spring-Boot #Spring-security #Spring-web-mvc #Hibernate

o root = livraria/ todos os end-points estão protegidos por autenticação exceto login e criar usuário. #/livraria/auth/login -> login da aplicação relembrar senha não está implementado usuário: ADMIN / senha: desafio-techlead (usuário administrador - senha está criptogrrafada no banaco) usuário: USER-TEST / senha:desafio-techlead-su #/livraria/auth/create-user (cria usuário) #/livraria/home (home da aplicação) #/livraria/books (lista livros, links de deleção com autorização, Somente ADMIN e o user criador podem deletar) #/livraria/books/book/save form de crição de livros Update não foi implementado

O autocofigure do spring está ativo, então as tabelas serão criadas automaticamente a partir das entidades jpa. o classe SetupData está carregando os dados iniciais caso não existam a cada restart da aplicação.

Sql do banco

-- Schema livraria

DROP SCHEMA IF EXISTS livraria ;

-- Schema livraria

CREATE SCHEMA IF NOT EXISTS livraria DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ; USE livraria ;

-- Table livraria.users

DROP TABLE IF EXISTS livraria.users ;

CREATE TABLE IF NOT EXISTS livraria.users ( USER_ID BIGINT NOT NULL AUTO_INCREMENT, ENABLED BIT(1) NOT NULL, LAST_LOGIN DATETIME NOT NULL, NAME VARCHAR(255) NOT NULL, PASSWORD VARCHAR(255) NOT NULL, USERNAME VARCHAR(255) NOT NULL, PRIMARY KEY (USER_ID)) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_bin;

-- Table livraria.book

DROP TABLE IF EXISTS livraria.book ;

CREATE TABLE IF NOT EXISTS livraria.book ( BOOK_ID BIGINT NOT NULL AUTO_INCREMENT, AUTHOR VARCHAR(255) NOT NULL, NAME VARCHAR(255) NOT NULL, REGISTERED_DATE DATETIME NOT NULL, user_id BIGINT NOT NULL, PRIMARY KEY (BOOK_ID), INDEX FKtl4svyjtxd0l25qcujjt1v1gx (user_id ASC), CONSTRAINT user_book_fk FOREIGN KEY (user_id) REFERENCES livraria.users (USER_ID)) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_bin;

-- Table livraria.role

DROP TABLE IF EXISTS livraria.role ;

CREATE TABLE IF NOT EXISTS livraria.role ( ROLE_ID BIGINT NOT NULL AUTO_INCREMENT, ROLE_NAME VARCHAR(255) NOT NULL, PRIMARY KEY (ROLE_ID)) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_bin;

-- Table livraria.user_role

DROP TABLE IF EXISTS livraria.user_role ;

CREATE TABLE IF NOT EXISTS livraria.user_role ( USER_ID BIGINT NOT NULL, ROLE_ID BIGINT NOT NULL, PRIMARY KEY (USER_ID, ROLE_ID), INDEX FKn1rn9qodd3u4le8uf3kl33qe3 (ROLE_ID ASC), CONSTRAINT role_user_fk FOREIGN KEY (ROLE_ID) REFERENCES livraria.role (ROLE_ID), CONSTRAINT user_role_fk FOREIGN KEY (USER_ID) REFERENCES livraria.users (USER_ID)) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_bin;

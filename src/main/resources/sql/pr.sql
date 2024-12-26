-- 테이블 생성 최종
-- database 생성
create database binary_stock_db default character set UTF8;
-- db 계정 생성
create user 'stock_admin1'@'%' identified by 't*@';
-- db stock_admin1 계정에 binary_stock_db db에 모든 권한 부여
grant all privileges on binary_stock_db.* to 'stock_admin1'@'%';

-- board 테이블 생성
CREATE TABLE board (
   id int auto_increment not null primary key comment 'PK',
   board_code varchar(50) not null comment '게시판코드',
   board_name varchar(50) not null comment '게시판이름',
   create_date datetime not null default current_timestamp() comment '게시판생성날짜',
   update_date datetime not null comment '게시판수정날짜',
   UNIQUE KEY `board_code` (`board_code`),
   UNIQUE KEY `board_name` (`board_name`)
);

-- travel)article 테이블 생성
CREATE TABLE travel_article (
	id int auto_increment not null primary key comment 'PK',
    title varchar(500) not null comment '제목',
    content text not null comment '내용',
    email varchar(30) not null comment '작성자',
    delete_yn varchar(5) not null comment '삭제여부',
    create_date datetime not null default current_timestamp() comment '생성일시',
	update_date datetime default null comment '최종수정일시',
	view_count int default 0 comment '조회수',
    board_code varchar(30) not null comment '게시판코드'
) comment '여행게시판';

-- humor_article 테이블 생성
CREATE TABLE humor_article (
	id int auto_increment not null primary key comment 'PK',
    title varchar(500) not null comment '제목',
    content text not null comment '내용',
    email varchar(30) not null comment '작성자',
    delete_yn varchar(5) not null comment '삭제여부',
    create_date datetime not null default current_timestamp() comment '생성일시',
	update_date datetime default null comment '최종수정일시',
	view_count int default 0 comment '조회수',
    board_code varchar(30) not null comment '게시판코드'
) comment '게시판';

-- member_info 테이블
create table member_info (
	id int auto_increment not null primary key,
	email varchar(100) not null,
	name varchar(100),
    provider varchar(50),
    nick_name varchar(200),
    picture varchar(300),
    role varchar(50),
	create_date timestamp default current_timestamp,
    update_date date
);

-- comment 댓글 테이블
CREATE TABLE comment (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY COMMENT 'PK',
    article_id INT NOT NULL COMMENT '게시글 ID',
    email VARCHAR(30) NOT NULL COMMENT '작성자 이메일',
    comment_content VARCHAR(1000) NOT NULL COMMENT '댓글 내용',
    create_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '댓글 작성일시',
    delete_yn VARCHAR(5) NOT NULL DEFAULT 'N' COMMENT '삭제여부',
    FOREIGN KEY (article_id) REFERENCES article1(id) ON DELETE CASCADE
) COMMENT '댓글';
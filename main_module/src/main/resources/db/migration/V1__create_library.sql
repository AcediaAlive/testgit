CREATE TABLE IF NOT EXISTS book (
    id        varchar(32) not null comment '主键' primary key,
    name      varchar(100) not null comment '书名',
    author    varchar(100),
    publisher varchar(100),
    price     double,
    quantity  int
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;
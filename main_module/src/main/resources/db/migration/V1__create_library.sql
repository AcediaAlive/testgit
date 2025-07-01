CREATE TABLE IF NOT EXISTS book (
    id           varchar(32) not null comment '主键' primary key,
    name         varchar(100) not null comment '书名',
    author       varchar(100),
    publisher    varchar(100),
    price        double,
    quantity     int,
    creator      varchar(32)                        not null,
    create_time  datetime default CURRENT_TIMESTAMP not null,
    updater      varchar(32) null,
    update_time  datetime null
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS user (
    id           varchar(32) not null comment '主键' primary key,
    password     varchar(32) not null,
    creator      varchar(32)                        not null,
    create_time  datetime default CURRENT_TIMESTAMP not null,
    updater      varchar(32) null,
    update_time  datetime null
    )ENGINE=InnoDB DEFAULT CHARSET=utf8;
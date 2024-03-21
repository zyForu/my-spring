create table user(
    id int primary key auto_increment,
    name varchar(30) comment '用户名',
    sex char(2) comment '性别',
    addr varchar(100) comment '地址',
    birth datetime comment '生日'
);
insert into user (name, sex, addr, birth) values
    ('安妮', '女', '艾欧尼亚', now()),
    ('艾瑞莉娅', '女', '艾欧尼亚', now()),
    ('压缩', '男', '艾欧尼亚', now());
# 1. 创建数据库
drop database if exists athena_upms;

create database athena_upms default character set utf8mb4 collate utf8mb4_general_ci;

use athena_upms;

set names utf8mb4;

# 2. 创建表
# 2.1 demo表
drop table if exists t_demo;

create table t_demo
(
    id               bigint unsigned auto_increment primary key comment '主键id',
    name             varchar(32)      default ''                                            not null comment '名称',
    description      varchar(255)     default ''                                            not null comment '描述',
    status           tinyint unsigned default 0                                             not null comment '状态 0:禁用 1:启用',
    tenant_id        bigint unsigned  default 0                                             not null comment '租户id 0:公共租户',
    version          int unsigned     default 0                                             not null comment '版本号',
    deleted          tinyint unsigned default 0                                             not null comment '是否删除 0:否 1:是',
    create_user_id   bigint unsigned  default 0                                             not null comment '创建人id',
    create_user_name varchar(32)      default ''                                            not null comment '创建人姓名',
    create_time      timestamp        default current_timestamp                             not null comment '创建时间',
    update_user_id   bigint unsigned  default 0                                             not null comment '更新人id',
    update_user_name varchar(32)      default ''                                            not null comment '更新人姓名',
    update_time      timestamp        default current_timestamp on update current_timestamp not null comment '更新时间'
) engine = InnoDB
  default charset = utf8mb4 comment 'demo表';

insert into t_demo (name, description, status, tenant_id, version, deleted, create_user_id, create_user_name,
                    update_user_id, update_user_name)
values ('demo', 'demo', 1, 0, 0, 0, 0, 'admin', 0, 'admin');
# 1. 创建数据库
drop database if exists upms;

create database athena_upms default character set utf8mb4 collate utf8mb4_general_ci;

use athena_upms;

set names utf8mb4;

# 2. 创建表
# 2.1 demo表
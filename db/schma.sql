CREATE DATABASE IF NOT EXISTS `simple_task` default character set utf8 collate utf8_unicode_ci;
use `simple_task`;

drop table if exists sys_user;

drop table if exists task_definition;
create table task_definition
(
    id              bigint unsigned auto_increment primary key,
    definition_code varchar(100) not null comment '定义代码',
    task_name       varchar(50) comment '流程定义名称',
    flow_class      varchar(100) comment '任务拓展处理类全路径,没有就填null',
    gmt_create      datetime              default current_timestamp,
    gmt_modified    datetime     not null default current_timestamp on update current_timestamp,
    unique key uk_dcode (definition_code)
) comment '流程定义表';

drop table if exists task_definition_node;
create table task_definition_node
(
    id               bigint unsigned auto_increment primary key,
    definition_code  varchar(100) not null comment '定义代码',
    parent_node_code varchar(100) comment '父节点代码',
    node_code        varchar(100) not null comment '节点代码',
    node_name        varchar(50)  not null comment '节点名称',
    condition_class  varchar(100) comment '条件判断类全路径',
    action_class     varchar(200) not null comment '关联类的全路径',
    priority         int          not null default 0 comment '分支优先级',
    gmt_create       datetime              default current_timestamp,
    gmt_modified     datetime     not null default current_timestamp on update current_timestamp,
    unique key uk_dncode (definition_code, node_code)
) comment '流程定义节点表';

drop table if exists task_instance;
create table task_instance
(
    id              bigint unsigned auto_increment primary key,
    instance_code   varchar(50)  not null comment '实例代码',
    definition_code varchar(100) not null comment '定义代码',
    node_id         bigint comment '节点id',
    creator         varchar(50) comment '创建者名称',
    `status`        int          not null default 0 comment '状态：1进行中，2已完成，3已终止等',
    create_time      datetime     not null default current_timestamp comment '申请时间',
    gmt_create      datetime              default current_timestamp,
    gmt_modified    datetime     not null default current_timestamp on update current_timestamp,
    unique key uk_icode (instance_code)
) comment '流程实例表';

drop table if exists task_instance_node;
create table task_instance_node
(
    id            bigint unsigned auto_increment primary key,
    node_id       bigint comment '节点id',
    parent_node_id     int comment '父节点id',
    instance_code varchar(50) comment '实例代码',
    task_code     varchar(50) comment '任务编号',
    actionUser      varchar(50) comment '审批人用户名',
    node_code     varchar(100) comment '节点代码',
    node_name     varchar(50) comment '节点名称',
    action_status int      not null default 0 comment '状态：1待处理，2同意，3不同意等',
    action_time   datetime comment '批阅时间',
    remark          varchar(250) comment '批注',
    gmt_create    datetime          default current_timestamp,
    gmt_modified  datetime not null default current_timestamp on update current_timestamp,
    unique key uk_task_id (task_code)
) comment '流程实例节点表';

drop table if exists task_variable;
create table task_variable
(
    id            bigint unsigned auto_increment primary key,
    instance_code varchar(50) comment '实例代码',
    `key`          varchar(64) comment '变量键',
    `value`        varchar(200) comment '变量值',
    type          varchar(100) comment '变量类型',
    gmt_create    datetime          default current_timestamp,
    gmt_modified  datetime not null default current_timestamp on update current_timestamp
) comment '流程实例变量';



create database oasystem character set utf8mb4;
use oasystem;
/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2019/12/12 23:01:01                          */
/*==============================================================*/


drop table if exists t_department;

drop table if exists t_apply;

drop table if exists t_apply_file;

drop table if exists t_article;

drop table if exists t_comment;

drop table if exists t_examine;

drop table if exists t_flow;

drop table if exists t_flow_line;

drop table if exists t_flow_node;

drop table if exists t_notice;

drop table if exists t_notice_file;

drop table if exists t_notice_read;

drop table if exists t_permission;

drop table if exists t_position;

drop table if exists t_position_permission;

drop table if exists t_user;

/*==============================================================*/
/* Table: "t_ department"                                       */
/*==============================================================*/
create table t_department
(
   department_id        varchar(64) not null,
   department_name      varchar(32),
   department_number    int,
   department_level     int,
   department_parent    varchar(64),
   primary key (department_id),
   key AK_parent (department_parent)
)DEFAULT CHARSET=utf8;

alter table t_department comment '部门表';

/*==============================================================*/
/* Table: t_apply                                               */
/*==============================================================*/
create table t_apply
(
   apply_id             varchar(64) not null,
   apply_title          varchar(64),
   apply_description    varchar(128),
   apply_submit_time    bigint,
   apply_state          int default 0,
   flow_id              varchar(64),
   user_id              varchar(64),
   apply_current_node   varchar(64),
   primary key (apply_id)
)DEFAULT CHARSET=utf8;

alter table t_apply comment '申请表';

/*==============================================================*/
/* Table: t_apply_file                                          */
/*==============================================================*/
create table t_apply_file
(
   file_id              varchar(64) not null,
   file_name            varchar(128),
   file_reality_name    varchar(128),
   file_address         varchar(256),
   file_size            bigint,
   apply_id             varchar(64),
   primary key (file_id)
)DEFAULT CHARSET=utf8;

alter table t_apply_file comment '申请文件表';

/*==============================================================*/
/* Table: t_article                                             */
/*==============================================================*/
create table t_article
(
   article_id           varchar(64) not null,
   article_title        varchar(128),
   article_content      varchar(2048),
   article_type         int,
   article_create_time  bigint,
   article_comment_count int,
   article_page_view    int,
   user_id              varchar(64),
   article_state        int default 0,
   article_file         varchar(512),
   primary key (article_id)
);

alter table t_article comment '文章表';

/*==============================================================*/
/* Table: t_comment                                             */
/*==============================================================*/
create table t_comment
(
   comment_id           varchar(64) not null,
   comment_content      varchar(512),
   comment_create_time  bigint,
   comment_parent       varchar(64),
   user_id              varchar(64),
   article_id           varchar(64),
   primary key (comment_id)
)DEFAULT CHARSET=utf8mb4;

alter table t_comment comment '评论表';

/*==============================================================*/
/* Table: t_examine                                             */
/*==============================================================*/
create table t_examine
(
   examine_id           varchar(64) not null,
   apply_id             varchar(64),
   flow_node_id         varchar(64),
   user_id              varchar(64),
   user_name            varchar(32),
   examine_status       bit,
   examine_info      	varchar(256),
   examine_date         bigint,
   primary key (examine_id)
)DEFAULT CHARSET=utf8;

alter table t_examine comment '审批表';

/*==============================================================*/
/* Table: t_flow                                                */
/*==============================================================*/
create table t_flow
(
   flow_id              varchar(64) not null,
   flow_name            varchar(64),
   flow_description     varchar(128),
   flow_state           int,
   primary key (flow_id)
)DEFAULT CHARSET=utf8;

alter table t_flow comment '流程表';

/*==============================================================*/
/* Table: t_flow_line                                           */
/*==============================================================*/
create table t_flow_line
(
   flow_line_id         varchar(64) not null,
   flow_id              varchar(64),
   prev_node_id         varchar(64),
   next_node_id         varchar(64),
   flow_line_description varchar(128),
   primary key (flow_line_id)
)DEFAULT CHARSET=utf8;

alter table t_flow_line comment '流程线表';

/*==============================================================*/
/* Table: t_flow_node                                           */
/*==============================================================*/
create table t_flow_node
(
   flow_node_id         varchar(64) not null,
   flow_node_name       varchar(64),
   flow_node_description varchar(128),
   flow_id              varchar(64),
   position_id          varchar(64),
   primary key (flow_node_id)
)DEFAULT CHARSET=utf8;

alter table t_flow_node comment '流程节点表';

/*==============================================================*/
/* Table: t_notice                                              */
/*==============================================================*/
create table t_notice
(
   notice_id            varchar(64) not null,
   notice_send_time     bigint,
   notice_title         varchar(64),
   notice_description   varchar(256),
   user_id              varchar(64),
   notice_type            int,
   primary key (notice_id)
)DEFAULT CHARSET=utf8;

alter table t_notice comment '通知表';

/*==============================================================*/
/* Table: t_notice_file                                         */
/*==============================================================*/
create table t_notice_file
(
   file_id              varchar(64) not null,
   file_name            varchar(128),
   file_reality_name    varchar(128),
   file_address         varchar(256),
   file_size            bigint,
   notice_id            varchar(64),
   primary key (file_id)
)DEFAULT CHARSET=utf8;

alter table t_notice_file comment '通知文件表';

/*==============================================================*/
/* Table: t_notice_read                                         */
/*==============================================================*/
create table t_notice_read
(
   notice_id            varchar(64) not null,
   user_id              varchar(64) not null,
   read_time            bigint,
   primary key (notice_id, user_id)
)DEFAULT CHARSET=utf8;

alter table t_notice_read comment '通知阅读情况表';

/*==============================================================*/
/* Table: t_permission                                          */
/*==============================================================*/
create table t_permission
(
   permission_id        varchar(64) not null,
   permission_name      varchar(64),
   primary key (permission_id)
)DEFAULT CHARSET=utf8;

alter table t_permission comment '权限表';

/*==============================================================*/
/* Table: t_position                                            */
/*==============================================================*/
create table t_position
(
   position_id          varchar(64) not null,
   position_name        varchar(32),
   primary key (position_id)
)DEFAULT CHARSET=utf8;

alter table t_position comment '职位表';

/*==============================================================*/
/* Table: t_position_permission                                 */
/*==============================================================*/
create table t_position_permission
(
   position_id          varchar(64) not null,
   permission_id        varchar(64) not null,
   primary key (position_id, permission_id)
)DEFAULT CHARSET=utf8;

alter table t_position_permission comment '职位权限表';

/*==============================================================*/
/* Table: t_user                                                */
/*==============================================================*/
create table t_user
(
   user_id              varchar(64) not null,
   user_account         varchar(16) not null,
   user_password        varchar(64) not null,
   user_name            varchar(32),
   user_create_time     bigint,
   user_phone           char(11),
   user_email           varchar(32),
   user_head_file_path  varchar(512),
   user_head_portrait   varchar(256),
   user_status          bit default 0,
   department_id        varchar(64),
   position_id          varchar(64),
   primary key (user_id),
   unique key AK_Key_2 (user_account)
)DEFAULT CHARSET=utf8;

alter table t_user comment '用户表';

create table t_user_file
(
   file_id              varchar(64) not null,
   file_name            varchar(128),
   file_reality_name    varchar(128),
   file_address         varchar(256),
   file_size            bigint,
   user_id              varchar(64),
   upload_time          bigint,
   primary key (file_id)
)DEFAULT CHARSET=utf8;

alter table t_user_file comment '用户文件表';

alter table t_user_file add constraint FK_user_file_user foreign key (user_id)
      references t_user (user_id) on delete cascade on update cascade;

alter table t_department add constraint FK_department_department foreign key (department_parent)
      references t_department (department_id) on delete restrict on update cascade;

alter table t_apply add constraint FK_apply_flow foreign key (flow_id)
      references t_flow (flow_id) on delete restrict on update restrict;

alter table t_apply add constraint FK_apply_flow_node foreign key (apply_current_node)
      references t_flow_node (flow_node_id) on delete set null on update cascade;

alter table t_apply add constraint FK_apply_user foreign key (user_id)
      references t_user (user_id) on delete set null on update cascade;

alter table t_apply_file add constraint FK_apply_file_apply foreign key (apply_id)
      references t_apply (apply_id) on delete cascade on update cascade;

alter table t_article add constraint FK_article_user foreign key (user_id)
      references t_user (user_id) on delete set null on update cascade;

alter table t_comment add constraint FK_comment_article foreign key (article_id)
      references t_article (article_id) on delete cascade on update cascade;

alter table t_comment add constraint FK_comment_user foreign key (user_id)
      references t_user (user_id) on delete cascade on update cascade;

alter table t_examine add constraint FK_examine_user foreign key (user_id)
      references t_user (user_id) on delete set null  on update cascade;

alter table t_examine add constraint FK_examine_apply foreign key (apply_id)
      references t_apply (apply_id) on delete cascade on update cascade;

alter table t_flow_line add constraint FK_flow_line_flow foreign key (flow_id)
      references t_flow (flow_id) on delete cascade on update cascade;

alter table t_flow_line add constraint FK_flow_line_flow_node_next foreign key (next_node_id)
      references t_flow_node (flow_node_id) on delete set null on update cascade;

alter table t_flow_line add constraint FK_flow_line_flow_node_prev foreign key (prev_node_id)
      references t_flow_node (flow_node_id) on delete set null on update cascade;

alter table t_flow_node add constraint FK_Reference_18 foreign key (flow_id)
      references t_flow (flow_id) on delete cascade on update cascade;

alter table t_flow_node add constraint FK_flow_node_position foreign key (position_id)
      references t_position (position_id) on delete set null on update cascade;

alter table t_notice add constraint FK_notice_user foreign key (user_id)
      references t_user (user_id) on delete set null on update cascade;

alter table t_notice_file add constraint FK_notice_file_notice foreign key (notice_id)
      references t_notice (notice_id) on delete cascade on update cascade;

alter table t_notice_read add constraint FK_notice_read_notice foreign key (notice_id)
      references t_notice (notice_id) on delete cascade on update cascade;

alter table t_notice_read add constraint FK_notice_read_user foreign key (user_id)
      references t_user (user_id) on delete cascade on update cascade;

alter table t_position_permission add constraint FK_position_permission_permission foreign key (permission_id)
      references t_permission (permission_id) on delete restrict on update restrict;

alter table t_position_permission add constraint FK_position_permission_poition foreign key (position_id)
      references t_position (position_id) on delete cascade on update cascade;

alter table t_user add constraint FK_user_department foreign key (department_id)
      references t_department (department_id) on delete set null on update cascade;

alter table t_user add constraint FK_user_position foreign key (position_id)
      references t_position (position_id) on delete set null on update cascade;

alter table t_examine add constraint FK_examine_node foreign key (flow_node_id)
      references t_flow_node (flow_node_id) on delete set null on update cascade;

/*初始化权限*/
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('1','admin:all');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('10','apply:get');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('11','apply:list');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('12','examine:add');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('13','examine:del');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('14','examine:alter');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('15','examine:get');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('16','examine:list');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('17','file:add');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('18','file:del');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('19','file:alter');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('2','notice:add');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('20','file:get');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('21','file:list');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('22','user:add');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('23','user:del');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('24','user:alter');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('25','user:get');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('26','user:list');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('27','article:add');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('28','article:del');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('29','article:alter');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('3','notice:del');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('30','article:get');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('31','article:list');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('32','comment:add');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('33','comment:del');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('34','comment:alter');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('35','comment:get');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('36','comment:list');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('37','permission:add');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('38','permission:del');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('39','permission:alter');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('4','notice:alter');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('40','permission:get');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('41','permission:list');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('42','dep:add');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('43','dep:get');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('44','dep:alter');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('45','dep:del');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('46','dep:list');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('47','flow:add');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('48','flow:del');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('49','flow:alter');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('5','notice:get');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('50','flow:get');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('51','flow:list');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('52','position:add');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('53','position:del');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('54','position:alter');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('55','position:get');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('56','position:list');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('6','notice:list');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('7','apply:add');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('8','apply:del');
INSERT INTO `t_permission` (`permission_id`,`permission_name`) VALUES ('9','apply:alter');

/*初始化管理员职位*/
insert into t_position(position_id, position_name) values ('1','管理员');
/*初始化管理员账号*/
insert into t_user(user_id, user_account, user_password, user_name,user_create_time,user_status, position_id) values ('1','admin','038bdaf98f2037b31f1e75b5b4c9b26e','管理员',0,1,'1');
/*初始化管理员职位的权限*/
insert into t_position_permission(position_id, permission_id) values ('1','1');
      


-- csmall_ups

DROP DATABASE IF EXISTS csmall_ups;
CREATE DATABASE csmall_ups;

USE csmall_ups;

# 用户表
DROP TABLE IF EXISTS ups_user;
CREATE TABLE ups_user
(
    id           bigint(20) unsigned DEFAULT NULL AUTO_INCREMENT COMMENT '数据id',
    username     varchar(50)          DEFAULT NULL COMMENT '用户名',
    password     char(64)             DEFAULT NULL COMMENT '密码（密文）',
    gender       varchar(25)          DEFAULT NULL COMMENT '性别',
    age          tinyint(32) unsigned DEFAULT NULL COMMENT '年龄',
    nickname     varchar(50)          DEFAULT NULL COMMENT '昵称',
    avatar       varchar(255)         DEFAULT NULL COMMENT '头像URL',
    phone        varchar(50)          DEFAULT NULL COMMENT '手机号码',
    email        varchar(50)          DEFAULT NULL COMMENT '电子邮箱',
    sign         varchar(255)         DEFAULT NULL COMMENT '个性签名',
    gmt_create   datetime             DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified datetime             DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8mb4 COMMENT ='用户';
INSERT INTO ups_user(username, password, gender, age, nickname, avatar, phone, email, sign,gmt_create,gmt_modified)
VALUES ('开发者','123456','男',19,'Devotion','https://img2.baidu.com/it/u=4244269751,
4000533845&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500','15551898016','2168149198@qq.com','我很懒,什么也不想说...', '2022-07-08 11:30:44', '2022-07-08 11:30:44');

# 收货地址
DROP TABLE IF EXISTS ups_address;
CREATE TABLE ups_address
(
    id           bigint(20) unsigned DEFAULT NULL AUTO_INCREMENT COMMENT '数据id',
    user_id      bigint(20) unsigned DEFAULT NULL COMMENT '用户id',
    name         varchar(50) NOT NULL COMMENT '地址名称',
    gmt_create   datetime    DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified datetime    DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8mb4 COMMENT ='收货地址';
INSERT INTO ups_address(user_id, name,gmt_create,gmt_modified) VALUES (1,'浙江省-宁波市-江北区-孔浦街道', '2022-07-08 11:30:44', '2022-07-08 11:30:44');

# 购物车
DROP TABLE IF EXISTS ups_cart;
CREATE TABLE ups_cart
(
    id             bigint(20) unsigned DEFAULT NULL AUTO_INCREMENT COMMENT '数据id',
    user_id        bigint(20) unsigned DEFAULT NULL COMMENT '用户id',
    spu_id         bigint(20) unsigned DEFAULT NULL COMMENT '商品SPU详情id',
    gmt_create     datetime            DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified   datetime            DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8mb4 COMMENT ='购物车';

# 商品分类
DROP TABLE IF EXISTS ups_category;
CREATE TABLE ups_category
(
    id           bigint(20) unsigned DEFAULT NULL AUTO_INCREMENT COMMENT '数据id',
    name         varchar(50)         DEFAULT NULL COMMENT '类别名称',
    parent_id    bigint(20) unsigned DEFAULT '0' COMMENT '父级类别id，如果无父级，则为0',
    depth        tinyint(3) unsigned DEFAULT '1' COMMENT '深度，最顶级类别的深度为1，次级为2，以此类推',
    keywords     varchar(255)        DEFAULT NULL COMMENT '关键词列表，各关键词使用英文的逗号分隔',
    sort         tinyint(3) unsigned DEFAULT '0' COMMENT '排序序号',
    enable       tinyint(3) unsigned DEFAULT '0' COMMENT '是否启用，1=启用，0=未启用',
    is_parent    tinyint(3) unsigned DEFAULT '0' COMMENT '是否为父级（是否包含子级），1=是父级，0=不是父级',
    is_display   tinyint(3) unsigned DEFAULT '0' COMMENT '是否显示在导航栏中，1=启用，0=未启用',
    gmt_create   datetime            DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified datetime            DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8mb4 COMMENT ='类别';
INSERT INTO ups_category(name, parent_id, depth, keywords, sort, enable, is_parent, is_display,gmt_create,gmt_modified)
VALUES ('建材/家居',0,1,'家',1,1,1,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('科技/厨电',0,1,'厨',2,1,1,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('美食/饮食',0,1,'食',3,1,1,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('健康/保健',0,1,'健',4,1,1,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('空调',1,2,'无',5,1,0,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('电视',1,2,'无',6,1,0,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('洗衣机',1,2,'无',7,1,0,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('冰箱',1,2,'无',8,1,0,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('燃气灶',2,2,'无',9,1,0,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('排气扇',2,2,'无',10,1,0,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('炒菜机',2,2,'无',11,1,0,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('豆浆机',2,2,'无',12,1,0,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('臭鳜鱼',3,2,'无',13,1,0,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('毛豆腐',3,2,'无',14,1,0,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('符离集烧鸡',3,2,'无',15,1,0,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('黄山烧饼',3,2,'无',16,1,0,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('淮南牛肉汤',3,2,'无',17,1,0,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('芜湖虾籽面',3,2,'无',18,1,0,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('阜阳（fù yáng）格拉条',3,2,'无',19,1,0,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('蜂蜜',4,2,'无',20,1,0,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('蛋白粉',4,2,'无',21,1,0,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('肌酸',4,2,'无',22,1,0,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('葡萄籽',4,2,'无',23,1,0,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('芦荟胶囊',4,2,'无',24,1,0,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44');

# 品牌
DROP TABLE IF EXISTS ups_brand;
CREATE TABLE ups_brand
(
    id           bigint(20) unsigned DEFAULT NULL AUTO_INCREMENT COMMENT '数据id',
    name         varchar(50)         DEFAULT NULL COMMENT '品牌名称',
    pinyin       varchar(50)         DEFAULT NULL COMMENT '品牌名称的拼音',
    logo         varchar(255)        DEFAULT NULL COMMENT '品牌logo的URL',
    description  varchar(255)        DEFAULT NULL COMMENT '品牌简介',
    keywords     varchar(255)        DEFAULT NULL COMMENT '关键词列表，各关键词使用英文的逗号分隔',
    sort         tinyint(3) unsigned DEFAULT '0' COMMENT '排序序号',
    sales        int(10) unsigned    DEFAULT '0' COMMENT '销量',
    enable       tinyint(3) unsigned DEFAULT '0' COMMENT '是否启用，1=启用，0=未启用',
    gmt_create   datetime            DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified datetime            DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8mb4 COMMENT ='品牌';
INSERT INTO ups_brand(name, pinyin, logo, description, keywords, sort, sales, enable,gmt_create,gmt_modified)
VALUES ('格力','geli','无','自信即巅峰','无',1,'10000',1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('美的','meidi','无','自信即巅峰','无',2,'10000',1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('奥克斯','aokesi','无','自信即巅峰','无',3,'10000',1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('海尔','haier','无','自信即巅峰','无',4,'10000',1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('联想','lianxiang','无','自信即巅峰','无',5,'10000',1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('华为','huawei','无','自信即巅峰','无',6,'10000',1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('伊利','yili','无','自信即巅峰','无',7,'10000',1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('百草味','baicaowei','无','自信即巅峰','无',8,'10000',1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('金龙鱼','jinlongyu','无','自信即巅峰','无',8,'10000',1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('中华老字号','laozihao','无','自信即巅峰','无',8,'10000',1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('汤臣倍健','beijian','无','自信即巅峰','无',8,'10000',1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('同仁堂','rentang','无','自信即巅峰','无',8,'10000',1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('善存','shancun','无','自信即巅峰','无',8,'10000',1, '2022-07-08 11:30:44', '2022-07-08 11:30:44');

# 相册
DROP TABLE IF EXISTS ups_album;
CREATE TABLE ups_album
(
    id           bigint(20) unsigned DEFAULT NULL AUTO_INCREMENT COMMENT '数据id',
    name         varchar(50)         DEFAULT NULL COMMENT '相册名称',
    description  varchar(255)        DEFAULT NULL COMMENT '相册简介',
    sort         tinyint(3) unsigned DEFAULT '0' COMMENT '排序序号',
    gmt_create   datetime            DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified datetime            DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8mb4 COMMENT ='相册';
INSERT INTO ups_album(name, description, sort,gmt_create,gmt_modified)
VALUES ('空调','有关空调的相关图片',1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('电视','有关电视的相关图片',2, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('洗衣机','有关洗衣机的相关图片',3, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('冰箱','有关冰箱的相关图片',4, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('燃气灶','有关燃气灶的相关图片',5, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('排气扇','有关排气扇的相关图片',6, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('炒菜机','有关炒菜机的相关图片',7, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('豆浆机','有关豆浆机的相关图片',8, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('臭鳜鱼','有关臭鳜鱼的相关图片',9, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('毛豆腐','有关毛豆腐的相关图片',10, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('符离集烧鸡','有关符离集烧鸡的相关图片',11, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('黄山烧饼','有关黄山烧饼的相关图片',12, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('淮南牛肉汤','有关淮南牛肉汤的相关图片',13, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('芜湖虾籽面','有关芜湖虾籽面的相关图片',14, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('阜阳（fù yáng）格拉条','有关阜阳（fù yáng）格拉条的相关图片',15, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('蜂蜜','有关蜂蜜的相关图片',16, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('蛋白粉','有关蛋白粉的相关图片',17, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('肌酸','有关肌酸的相关图片',18, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('葡萄籽','有关葡萄籽的相关图片',19, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('芦荟胶囊','有关芦荟胶囊的相关图片',20, '2022-07-08 11:30:44', '2022-07-08 11:30:44');

# 商品图片
DROP TABLE IF EXISTS ups_picture;
CREATE TABLE ups_picture
(
    id           bigint(20) unsigned DEFAULT NULL AUTO_INCREMENT COMMENT '数据id',
    album_id     bigint(20) unsigned  DEFAULT NULL COMMENT '相册id',
    url          varchar(255)         DEFAULT NULL COMMENT '图片url',
    description  varchar(255)         DEFAULT NULL COMMENT '图片简介',
    width        smallint(5) unsigned DEFAULT NULL COMMENT '图片宽度，单位：px',
    height       smallint(5) unsigned DEFAULT NULL COMMENT '图片高度，单位：px',
    is_cover     tinyint(3) unsigned  DEFAULT '0' COMMENT '是否为封面图片，1=是，0=否',
    sort         tinyint(3) unsigned  DEFAULT '0' COMMENT '排序序号',
    gmt_create   datetime             DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified datetime             DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8mb4 COMMENT ='图片';
INSERT INTO ups_picture(album_id, url, description, width, height, is_cover, sort,gmt_create,gmt_modified)
VALUES (1,'https://img.alicdn.com/imgextra/i1/2435073206/O1CN01JCQjzc1ZYOZ89Bvbg_!!2435073206.jpg','有关空调的相关图片',200,200,1,1, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (1,'https://img.alicdn.com/imgextra/i1/470168984/O1CN01v7YtuL2GEj34IITmL_!!470168984.jpg','有关空调的相关图片',200,200,0,2, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (2,'https://img.alicdn.com/imgextra/i4/467221028/O1CN01SAORVg1JSrxAv60Wg_!!467221028.jpg','有关电视机的相关图片',200,200,1,3, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (2,'https://img.alicdn.com/imgextra/i2/2201759255529/O1CN01G3jskU1qiKbRBE8eE_!!2201759255529.jpg','有关电视机的相关图片',200,200,0,4, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (3,'https://img.alicdn.com/imgextra/i2/2214303283930/O1CN01VolvEA1etzJLwoosJ_!!2214303283930.jpg','有关洗衣机的相关图片',200,200,1,5, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (3,'https://img.alicdn.com/imgextra/i4/201749140/O1CN01APMnm62HOAoE2m0nG_!!201749140.jpg','有关洗衣机的相关图片',200,200,0,6, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (4,'https://img.alicdn.com/imgextra/i1/2695817590/O1CN01dXVo7T25wGvicpVSu_!!2695817590.jpg','有关冰箱的相关图片',200,200,1,7, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (4,'https://img.alicdn.com/imgextra/i4/2435073206/O1CN01FhrEyP1ZYOZsH07dW_!!2435073206.jpg','有关冰箱的相关图片',200,200,0,8, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (5,'https://img.alicdn.com/imgextra/i3/865424358/O1CN01auUwgx1i40pgOhZl0_!!865424358.jpg','有关燃气灶的相关图片',200,200,1,9, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (5,'https://item.taobao.com/item.htm?spm=pc_detail.27183998/evo318828b447259.202206.2.21fb7dd671fulO&id=671603955155','有关燃气灶的相关图片',200,200,0,10, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (6,'https://img.alicdn.com/imgextra/i4/2201209071803/O1CN01s6irrh1PBomzOkdJq_!!2201209071803.jpg','有关排气扇的相关图片',200,200,1,11, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (6,'https://img.alicdn.com/imgextra/i2/588181605/O1CN01uqNdOX1Nj8EE1ed51_!!588181605.jpg','有关排气扇的相关图片',200,200,0,12, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (7,'https://img.alicdn.com/imgextra/i3/2214310013146/O1CN016AUVHW1Z6uonHtIVN_!!2214310013146.jpg','有关炒菜机的相关图片',200,200,1,13, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (7,'https://img.alicdn.com/imgextra/i2/160586276/O1CN01hkp3d91wESTFyP7fR_!!160586276.jpg','有关炒菜机的相关图片',200,200,0,14, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (8,'https://img.alicdn.com/imgextra/i3/2210521197157/O1CN01xtPrna22jxPgg0Alh_!!2210521197157.jpg','有关豆浆机的相关图片',200,200,1,15, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (8,'https://img.alicdn.com/imgextra/i2/2210521197157/O1CN01bHO8fY22jxPfmQV2r_!!2210521197157.jpg','有关豆浆机的相关图片',200,200,0,16, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (9,'https://pics2.baidu.com/feed/9f2f070828381f30897b6be9b20980026e06f027.jpeg@f_auto?token=b5cdf3c51b6ed900f136202fbe38d190','有关臭鳜鱼的相关图片',200,200,1,17, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (9,'https://pics4.baidu.com/feed/77094b36acaf2edd33917f5a9518cde339019337.jpeg@f_auto?token=28b5899e344bf789b5e5b2216af186d9','有关臭鳜鱼的相关图片',200,200,0,18, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (10,'https://pics4.baidu.com/feed/8718367adab44aedd15ea166a9144b0ba18bfb37.jpeg@f_auto?token=10f7ac8ea89b6f3e5df56fee028c65fa','有关毛豆腐的相关图片',200,200,1,19, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (10,'https://pics5.baidu.com/feed/03087bf40ad162d9d2f3744d07d765e68a13cd50.jpeg@f_auto?token=f2292cf7e8fa9e83d27477f8c11d2707','有关毛豆腐的相关图片',200,200,0,20, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (11,'https://pics0.baidu.com/feed/c2fdfc039245d68878adb9fcbccab114d31b24c9.jpeg@f_auto?token=b9237e672f68cd7a313ee7ee6837e236','有关符离集烧鸡的相关图片',200,200,1,21, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (11,'https://pics4.baidu.com/feed/9358d109b3de9c82bd4a8de542894c0019d843bb.jpeg@f_auto?token=e9b793e621c638b90f8d97c01db06f60','有关符离集烧鸡的相关图片',200,200,0,22, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (12,'https://pics2.baidu.com/feed/f2deb48f8c5494ee3916e18e09fd2cf499257e00.jpeg@f_auto?token=9258a0d6e954b70ce9cddc9e6e312624','有关黄山烧饼的相关图片',200,200,1,23, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (12,'https://pics5.baidu.com/feed/9358d109b3de9c82816cbd7c76894c0018d843ce.jpeg@f_auto?token=4cbbf5bfab9d9e08a4ee4fa024183421','有关黄山烧饼的相关图片',200,200,0,24, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (13,'https://pics7.baidu.com/feed/960a304e251f95ca5872ec5ad11fb3346609526d.jpeg@f_auto?token=80270f477c8e9fb74da77ff5edd9f41c','有关淮南牛肉汤的相关图片',200,200,1,25, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (13,'https://pics6.baidu.com/feed/730e0cf3d7ca7bcb6200087baa01a769f624a87a.jpeg@f_auto?token=ba826f21519dd004192a713f17d81916','有关淮南牛肉汤的相关图片',200,200,0,26, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (14,'https://pics7.baidu.com/feed/94cad1c8a786c917aaf39877d235bcc53ac7579b.jpeg@f_auto?token=090a353a696c384cf61d52f61c33c2f9','有关芜湖虾籽面的相关图片',200,200,1,27, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (14,'https://pics7.baidu.com/feed/0b46f21fbe096b6315651068103b4b4eebf8ac99.jpeg@f_auto?token=8f4a703132274fea0a32d7bbfcefc4c0','有关芜湖虾籽面的相关图片',200,200,0,28, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (15,'https://pics1.baidu.com/feed/a50f4bfbfbedab64c398291fe03e63c978311e1d.jpeg@f_auto?token=cc3d5fa1de1a15ef77c7db9754bcda81','有关阜阳（fù yáng）格拉条的相关图片',200,200,1,29, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (15,'https://pics3.baidu.com/feed/8694a4c27d1ed21b01cbf915bb6611ce50da3fd6.jpeg@f_auto?token=93156913ce6044e8852965e7322cc65c','有关阜阳（fù yáng）格拉条的相关图片',200,200,0,30, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (16,'https://img.alicdn.com/imgextra/i4/1662192113/O1CN018Gw6BO1RTnZE47hid_!!1662192113.jpg','有关蜂蜜的相关图片',200,200,1,31, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (16,'https://img.alicdn.com/imgextra/i3/2208421205948/O1CN01DQmhN81toEa4plyJ2_!!2208421205948-0-scmitem6000.jpg','有关蜂蜜的相关图片',200,200,0,32, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (17,'https://img.alicdn.com/imgextra/i4/1893742642/O1CN0135UAcJ1VO5A3BdgzJ_!!1893742642.jpg','有关蛋白粉的相关图片',200,200,1,33, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (17,'https://img.alicdn.com/imgextra/i1/2207983598945/O1CN01p1fxYk2FwrbDqLFZx_!!2207983598945.jpg','有关蛋白粉的相关图片',200,200,0,34, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (18,'https://img.alicdn.com/imgextra/i4/2212199576322/O1CN01j8czqR1wZWd5nzdJ5_!!2212199576322.jpg','有关肌酸的相关图片',200,200,1,35, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (18,'https://img.alicdn.com/imgextra/i2/2212199576322/O1CN01mzsGeI1wZWd8deBeL_!!2212199576322.jpg','有关肌酸的相关图片',200,200,0,36, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (19,'https://img.alicdn.com/imgextra/i3/2352952336/O1CN01VdkN4g1T7vtKTVEIp_!!2352952336.jpg','有关葡萄籽的相关图片',200,200,1,37, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (19,'https://img.alicdn.com/imgextra/i3/2207983598945/O1CN01iw0Mek2Fwrb8nzFgU_!!2207983598945.jpg','有关葡萄籽的相关图片',200,200,0,38, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (20,'https://img.alicdn.com/imgextra/i4/88625217/TB2aacexd0opuFjSZFxXXaDNVXa_!!88625217.jpg','有关芦荟胶囊的相关图片',200,200,1,39, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (20,'https://img.alicdn.com/imgextra/i1/2212313000979/O1CN01hWuvbS1J6QXuL9c4r_!!2212313000979-0-scmitem8000.jpg','有关芦荟胶囊的相关图片',200,200,0,40, '2022-07-08 11:30:44', '2022-07-08 11:30:44');


# 属性模板
DROP TABLE IF EXISTS ups_attribute_template;
CREATE TABLE ups_attribute_template
(
    id           bigint(20) unsigned DEFAULT NULL AUTO_INCREMENT COMMENT '数据id',
    name         varchar(50)         DEFAULT NULL COMMENT '属性模版名称',
    pinyin       varchar(50)         DEFAULT NULL COMMENT '属性模版名称的拼音',
    keywords     varchar(255)        DEFAULT NULL COMMENT '关键词列表，各关键词使用英文的逗号分隔',
    sort         tinyint(3) unsigned DEFAULT '0' COMMENT '排序序号',
    gmt_create   datetime            DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified datetime            DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8mb4 COMMENT ='属性模版';
INSERT INTO ups_attribute_template(name, pinyin, keywords, sort,gmt_create,gmt_modified)
VALUES ('空调的属性模板', 'KongTiao', '关键词1,关键词2,关键词3', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('电视机的属性模板', 'DianShiJi', '关键词1,关键词2,关键词3', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('洗衣机的属性模板', 'XiYiJi', '关键词1,关键词2,关键词3', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('冰箱的属性模板', 'BingXiang', '关键词1,关键词2,关键词3', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('燃气灶的属性模板', 'RanQiZao', '关键词1,关键词2,关键词3', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('排气扇的属性模板', 'PaiQiShan', '关键词1,关键词2,关键词3', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('炒菜机的属性模板', 'ChaoCaiJi', '关键词1,关键词2,关键词3', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('豆浆机的属性模板', 'DouJiangJi', '关键词1,关键词2,关键词3', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('臭鳜鱼的属性模板', 'CouQueYu', '关键词1,关键词2,关键词3', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('毛豆腐的属性模板', 'MaoDouFu', '关键词1,关键词2,关键词3', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('符离集烧鸡的属性模板', 'FuLiJiShaoJi', '关键词1,关键词2,关键词3', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('黄山烧饼的属性模板', 'HuangShanShaoBing', '关键词1,关键词2,关键词3', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('淮南牛肉汤的属性模板', 'NiuRouTang', '关键词1,关键词2,关键词3', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('芜湖虾籽面的属性模板', 'XiaZiMian', '关键词1,关键词2,关键词3', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('阜阳（fù yáng）格拉条的属性模板', 'GeLaTiao', '关键词1,关键词2,关键词3', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('蜂蜜的属性模板', 'FengMi', '关键词1,关键词2,关键词3', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('蛋白粉的属性模板', 'DanBaiFen', '关键词1,关键词2,关键词3', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('肌酸的属性模板', 'JiSuan', '关键词1,关键词2,关键词3', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('葡萄籽的属性模板', 'PuTaoZi', '关键词1,关键词2,关键词3', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       ('芦荟胶囊的属性模板', 'LuHuJiaoNang', '关键词1,关键词2,关键词3', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44');

# 属性
DROP TABLE IF EXISTS ups_attribute;
CREATE TABLE ups_attribute
(
    id           bigint(20) unsigned DEFAULT NULL AUTO_INCREMENT COMMENT '数据id',
    template_id  bigint(20) unsigned DEFAULT NULL COMMENT '所属属性模版id',
    name         varchar(50)         DEFAULT NULL COMMENT '属性名称',
    description  varchar(255)        DEFAULT NULL COMMENT '属性简介（某些属性名称可能相同，通过简介补充描述）',
    value_list         varchar(255)        DEFAULT NULL COMMENT '备选值列表',
    unit         varchar(50)         DEFAULT NULL COMMENT '计量单位',
    sort         tinyint(3) unsigned DEFAULT '0' COMMENT '排序序号',
    gmt_create   datetime            DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified datetime            DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8mb4 COMMENT ='属性';
INSERT INTO ups_attribute(template_id, name, description,value_list, unit, sort,gmt_create,gmt_modified)
VALUES (1, '制冷量', '暂无', '5KW', '', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (1, '制冷功率', '暂无', '1.86', 'KW', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (1, '能效比', '暂无','2.69', '', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (2, '耗电量', '暂无', '低', '', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (2, '制冷量', '暂无', '5', 'KW', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (2, '制冷功率', '暂无', '1.86', 'KW', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (2, '能效比', '暂无','2.69', '', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (2, '耗电量', '暂无', '低', '', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (3, '制冷量', '暂无', '5', 'KW', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (3, '制冷功率', '暂无', '1.86', 'KW', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (3, '能效比', '暂无','2.69', '', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (3, '耗电量', '暂无', '低', '', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (4, '制冷量', '暂无', '5', 'KW', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (4, '制冷功率', '暂无', '1.86', 'KW', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (4, '能效比', '暂无','2.69', '', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (4, '耗电量', '暂无', '低', '', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (5, '热负荷', '暂无','3.5', 'KW', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (5, '热效率', '暂无','55', '百分比', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (5, '电源', '暂无','220', 'v', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (5, '燃料', '暂无', '天然气', '', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (6, '风量', '暂无','大', '', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (6, '功率', '暂无','中', '', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (6, '压力', '暂无', '小', '', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (6, '动力', '暂无','中', '', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (7, '电源功率', '暂无','220', 'W', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (7, '电机功率', '暂无','6', 'W', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (7, '净重', '暂无', '4.7', 'Kg', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (7, '耗电量', '暂无','0.02~0.12', '度/次', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (8, '电源功率', '暂无','220W', 'W', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (8, '电机功率', '暂无','6', 'W', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (8, '净重', '暂无', '4.7', 'Kg', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (8, '耗电量', '暂无','0.02~0.12', '度/次', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (9, '鲜度', '暂无', '新鲜', '', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (9, '净重', '暂无','1.3', 'Kg', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (10, '鲜度', '暂无', '新鲜', '', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (10, '净重', '暂无','0.3', 'Kg', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (11, '鲜度', '暂无', '新鲜', '', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (11, '净重', '暂无','2.3', 'Kg', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (12, '鲜度', '暂无', '新鲜', '', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (12, '净重', '暂无','0.3', 'Kg', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (13, '鲜度', '暂无', '新鲜', '', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (13, '牛肉量', '暂无','0.5', 'Kg', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (14, '鲜度', '暂无', '新鲜', '', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (14, '虾量', '暂无','0.5', 'Kg', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (15, '鲜度', '暂无', '新鲜', '', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (15, '净重', '暂无','0.3', 'Kg', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (16, '种类', '暂无', '益母草蜜', '', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (16, '净重', '暂无','500', 'g', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (17, '蛋白质含量', '暂无', '5', '百分比', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (17, '脂肪含量', '暂无','3', '百分比', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (18, '蛋白质含量', '暂无', '12', '百分比', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (18, '净重', '暂无','1.3', 'Kg', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (19, '蛋白质含量', '暂无', '2', '百分比', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (19, '净重', '暂无','1.3', 'Kg', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (20, '用量', '暂无', '一日1-3', '次', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44'),
       (20, '净重', '暂无','25', 'mg', 99, '2022-07-08 11:30:44', '2022-07-08 11:30:44');

# 品牌类别关联表
DROP TABLE IF EXISTS ups_brand_category;
CREATE TABLE ups_brand_category
(
    id           bigint(20) unsigned DEFAULT NULL AUTO_INCREMENT COMMENT '数据id',
    brand_id     bigint(20) unsigned DEFAULT NULL COMMENT '品牌id',
    category_id  bigint(20) unsigned DEFAULT NULL COMMENT '类别id',
    gmt_create   datetime            DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified datetime            DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8mb4 COMMENT ='品牌与类别关联';


# 类别与属性模板关联
DROP TABLE IF EXISTS ups_category_attribute_template;
CREATE TABLE ups_category_attribute_template
(
    id                    bigint(20) unsigned DEFAULT NULL AUTO_INCREMENT COMMENT '数据id',
    category_id           bigint(20) unsigned DEFAULT NULL COMMENT '类别id',
    attribute_template_id bigint(20) unsigned DEFAULT NULL COMMENT '属性模版id',
    gmt_create            datetime            DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified          datetime            DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8mb4 COMMENT ='类别与属性模版关联';

# 商品SPU详情表
DROP TABLE IF EXISTS ups_spu;
CREATE TABLE ups_spu
(
    id                    bigint(20) unsigned DEFAULT NULL COMMENT '数据id',
    name                  varchar(50)         DEFAULT NULL COMMENT 'SPU名称',
    type_number           varchar(50)         DEFAULT NULL COMMENT 'SPU编号',
    title                 varchar(255)        DEFAULT NULL COMMENT '标题',
    description           varchar(255)        DEFAULT NULL COMMENT 'SPU简介',
    list_price            decimal(10, 2)      DEFAULT NULL COMMENT '价格（显示在列表中）',
    stock                 int(10) unsigned    DEFAULT '0' COMMENT '当前库存',
    unit                  varchar(50)         DEFAULT NULL COMMENT '计件单位',
    brand_id              bigint(20) unsigned DEFAULT NULL COMMENT '品牌id',
    brand_name            varchar(50)         DEFAULT NULL COMMENT '品牌名称',
    category_id           bigint(20) unsigned DEFAULT NULL COMMENT '类别id',
    category_name         varchar(50)         DEFAULT NULL COMMENT '类别名称',
    attribute_template_id bigint(20) unsigned DEFAULT NULL COMMENT '属性模版id',
    album_id              bigint(20) unsigned DEFAULT NULL COMMENT '相册id',
    pictures              varchar(500)        DEFAULT NULL COMMENT '组图URLs，使用JSON数组表示',
    keywords              varchar(255)        DEFAULT NULL COMMENT '关键词列表，各关键词使用英文的逗号分隔',
    tags                  varchar(255)        DEFAULT NULL COMMENT '标签列表，各标签使用英文的逗号分隔，原则上最多3个',
    sales                 int(10) unsigned    DEFAULT '0' COMMENT '销量（冗余）',
    sort                  tinyint(3) unsigned DEFAULT '0' COMMENT '排序序号',
    is_published          tinyint(3) unsigned DEFAULT '0' COMMENT '是否上架（发布），1=已上架，0=未上架（下架）',
    is_new_arrival        tinyint(3) unsigned DEFAULT '0' COMMENT '是否新品，1=新品，0=非新品',
    is_recommend          tinyint(3) unsigned DEFAULT '0' COMMENT '是否推荐，1=推荐，0=不推荐',
    is_checked            tinyint(3) unsigned DEFAULT '0' COMMENT '是否已审核，1=已审核，0=未审核',
    check_user            varchar(50)         DEFAULT NULL COMMENT '审核人(当前管理员)',
    gmt_check             datetime            DEFAULT NULL COMMENT '审核通过时间',
    gmt_create            datetime            DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified          datetime            DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8mb4 COMMENT ='SPU（Standard Product Unit）';

# 订单信息表
DROP TABLE IF EXISTS ups_order;
CREATE TABLE ups_order
(
    id           bigint(20) unsigned DEFAULT NULL COMMENT '数据id',
    user_id      bigint(20) unsigned DEFAULT NULL COMMENT '用户id',
    spu_id       bigint(20) unsigned DEFAULT NULL COMMENT '商品SPU详情id',
    address_id   bigint(20) unsigned DEFAULT NULL COMMENT '收货地址id',
    logistics_id bigint(20) unsigned DEFAULT NULL COMMENT '物流id',
    number       int(11) unsigned DEFAULT '0' COMMENT '数量',
    gmt_create   datetime         DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified datetime         DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8mb4 COMMENT ='订单信息';

# 物流配送表
DROP TABLE IF EXISTS ups_logistics;
CREATE TABLE ups_logistics
(
    id           bigint(20) unsigned DEFAULT NULL COMMENT '数据id',
    name         varchar(50)  DEFAULT NULL COMMENT '物流名称',
    note         varchar(255) DEFAULT NULL COMMENT '物流备注',
    gmt_create   datetime     DEFAULT NULL COMMENT '数据创建时间',
    gmt_modified datetime     DEFAULT NULL COMMENT '数据最后修改时间',
    PRIMARY KEY (id)
) DEFAULT CHARSET = utf8mb4 COMMENT ='物流配送';
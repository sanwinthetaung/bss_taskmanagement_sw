create database `pos` collate utf8_general_ci;
use pos;

set foreign_key_checks = 1;

drop table if exists orders;
create table orders (
	`id` int not null auto_increment
    , `status` tinyint(1) not null default 0 comment '0: unpaid, 1: paid'
    , `created_date` datetime not null
    , `updated_date` datetime default null
    , `created_by` int(11) not null
    , `delete_flag` tinyint(1) default 0
    , primary key(`id`)
) engine=InnoDB default charset=utf8;
insert into orders (`status`, `created_date`, `updated_date`, `created_by`, `delete_flag`) values (1, now(), null, 1, 0); #1
insert into orders (`status`, `created_date`, `updated_date`, `created_by`, `delete_flag`) values (1, now(), null, 1, 0); #2

create table `order_detail` (
	`id` int not null auto_increment
    , `orderId` int not null
    , `itemId` int not null
    , `extraId` int
    , `quantity` int not null default 0
    , primary key (`id`)
    , foreign key(`orderId`) references orders (`id`)
) engine=InnoDB default charset=utf8;
insert into `order_detail` (`orderId`, `itemId`, `extraId`, `quantity`) values (1, 1, null, 2); #item #cola
insert into `order_detail` (`orderId`, `itemId`, `extraId`, `quantity`) values (1, 2, 3 ,2); #item #chicken
insert into `order_detail` (`orderId`, `itemId`, `extraId`, `quantity`) values (1, 4, null, 1); #set #lunch

create table items (
	`id` int not null auto_increment
    , `name` varchar(255) not null
    , `price` double not null
    , primary key(`id`)
) engine=InnoDB default charset=utf8;
insert into items (`name`, `price`) values('cola', 500); #1
insert into items (`name`, `price`) values('chicken', 1000); #2
insert into items (`name`, `price`) values('coffee', 400); #3
insert into items (`name`, `price`) values('lunch set', 4000); #4

#create table sets (
#	`id` int not null auto_increment
#    , `name` varchar(255) not null
#    , `price` double not null
#    , primary key(`id`)
#) engine=InnoDB default charset=utf8;
#insert into sets (`name`, `price`) values('set1', 10);#1
#insert into sets (`name`, `price`) values('set2', 20);#2

create table set_has_item (
	`id` int not null auto_increment
    , `setId` int not null
    , `itemId` int not null
    , `itemExtraId` int
    , primary key (`id`)
    , foreign key (`setId`) references items (`id`)
    , foreign key (`itemId`) references items (`id`)
) engine=InnoDB default charset=utf8;
insert into set_has_item (`setId`, `itemId`, `itemExtraId`) values (1, 4, null);#1
insert into set_has_item (`setId`, `itemId`, `itemExtraId`) values (2, 4, 1);#2

create table extra (
	`id` int not null auto_increment
    , `name` varchar(255)
    , `price` double not null default 0
    , primary key(`id`)
) engine=InnoDB default charset=utf8;
insert into extra (`name`, `price`) values ('hot', 100);#1
insert into extra (`name`, `price`) values ('cold', 0);#2
insert into extra (`name`, `price`) values ('spicy', 100);#3
insert into extra (`name`, `price`) values ('normal', 0);#4

create table item_has_extra (
	`id` int not null auto_increment
    , `itemId` int not null
    , `extraId` int not null
    , primary key (id)
    , foreign key (itemId) references items (`id`)
    , foreign key (extraId) references extra (`id`)
) engine=InnoDB default charset=utf8;
insert into item_has_extra (itemId, extraId) values (2, 3);
insert into item_has_extra (itemId, extraId) values (2, 4);
insert into item_has_extra (itemId, extraId) values (3, 1);
insert into item_has_extra (itemId, extraId) values (3, 2);


## --------
select * from order_detail;
select * from orders;

## select all items to make order
select item.*
from items item;

## select item extra of coffee
select ext.*
from items item
left outer join item_has_extra ihe
	on ihe.itemId = item.id
left outer join extra ext
	on ext.id = ihe.extraId
where item.id = 2;

## select set included item
select *
from items item
left outer join set_has_item shi
	on shi.setId = item.id
where shi.itemId = 4;

## select order
select * from set_has_item;
select * from extra;
select item.name as itemName
	, ext.price
    , item.price
	, sum(item.price + IFNULL(ext.price , 0) 
		+ IFNULL((select ext.price from set_has_item shi left outer join extra e on e.id = shi.itemExtraId where shi.itemExtraId = e.id), 0)) as totalPrice
    , (select ext.price from set_has_item shi left outer join extra e on e.id = shi.itemExtraId where shi.itemExtraId = e.id) as extPrice
from orders odr
left outer join order_detail odd
	on odd.orderId = odr.id
left outer join items item
	on odd.itemId = item.id
left outer join extra ext
	on odd.extraId = ext.id
where odr.id = 1
group by item.id
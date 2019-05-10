create database `pos` collate utf8_general_ci;
use pos;

set foreign_key_checks = 1;

drop table if exists orders;
create table orders (
	`id` int not null auto_increment
    , `status` tinyint(1) not null default 0
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
    , `type` tinyint(1) comment '0: item, 1: set'
    , `itemId` int not null
    , `quantity` int not null default 0
    , primary key (`id`)
    , foreign key(`orderId`) references orders (`id`)
) engine=InnoDB default charset=utf8;
insert into `order_detail` (`orderId`, `type`, `itemId`, `quantity`) values (1, 0, 1, 2); #item
insert into `order_detail` (`orderId`, `type`, `itemId`, `quantity`) values (1, 1, 1, 1); #set
insert into `order_detail` (`orderId`, `type`, `itemId`, `quantity`) values (1, 0, 1, 1); #set

insert into `order_detail` (`orderId`, `type`, `itemId`, `quantity`) values (2, 0, 1, 2); #item
insert into `order_detail` (`orderId`, `type`, `itemId`, `quantity`) values (2, 0, 1, 1); #item
insert into `order_detail` (`orderId`, `type`, `itemId`, `quantity`) values (2, 0, 2, 1); #item
insert into `order_detail` (`orderId`, `type`, `itemId`, `quantity`) values (2, 0, 3, 1); #item

create table items (
	`id` int not null auto_increment
    , `name` varchar(255) not null
    , `price` double not null
    , primary key(`id`)
) engine=InnoDB default charset=utf8;
insert into items (`name`, `price`) values('coffee', 50.0); #1
insert into items (`name`, `price`) values('mouse', 100.0); #2
insert into items (`name`, `price`) values('sweets', 10.0); #3

create table sets (
	`id` int not null auto_increment
    , `name` varchar(255) not null
    , `price` double not null
    , primary key(`id`)
) engine=InnoDB default charset=utf8;
insert into sets (`name`, `price`) values('set1', 10);#1
insert into sets (`name`, `price`) values('set2', 20);#2

create table set_detail (
	`id` int not null auto_increment
    , `setId` int not null
    , `itemId` int not null
    , primary key (`id`)
    , foreign key (`setId`) references sets (`id`)
    , foreign key (`itemId`) references items (`id`)
) engine=InnoDB default charset=utf8;
insert into set_detail (`setId`, `itemId`) values (1, 1);#1
insert into set_detail (`setId`, `itemId`) values (1, 2);#2

create table extra (
	`id` int not null auto_increment
    , `name` varchar(255)
    , primary key(`id`)
) engine=InnoDB default charset=utf8;
insert into extra (`name`) values ('hot');#1
insert into extra (`name`) values ('cold');#2

insert into extra (`name`) values ('red');#3
insert into extra (`name`) values ('blue');#4

create table item_extra (
	`id` int not null auto_increment
    , `itemId` int not null
    , `extraId` int not null
    , primary key (id)
    , foreign key (itemId) references items (`id`)
    , foreign key (extraId) references extra (`id`)
) engine=InnoDB default charset=utf8;
insert into item_extra (itemId, extraId) values (1, 1);
insert into item_extra (itemId, extraId) values (1, 2);

insert into item_extra (itemId, extraId) values (2, 3);
insert into item_extra (itemId, extraId) values (2, 4);



## --------
select * from order_detail;
select * from orders;

select sets.id, items.name  
 from sets sets
left outer join set_detail sd
on sets.id = sd.setId
left outer join items items
on sd.itemId = items.id;

select odr.*
	, (case when odd.type = 0 then items.name 
			else sets.name end) as itemName
	, extra.name as extraName
	, odd.quantity as qty
from orders odr
left outer join order_detail odd
	on odr.id = odd.orderId
left outer join items items
	on items.id = odd.itemId
left outer join sets sets
	on sets.id = odd.itemId
left outer join item_extra ie
	on ie.itemId = items.id
left outer join extra extra
	on extra.Id = ie.extraId
where odr.id = 2;
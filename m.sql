alter table player alter column image set default 'assets/image/bigBing.jpg';
alter table player alter column money set default 100;
alter table player alter column attack set default 1;
alter table player alter column strength set default 1;
alter table player alter column agility set default 1;
alter table player alter column defence set default 1;
alter table player alter column attack set default 1;
alter table player alter column fatigue set default 0;
alter table player alter column hp set default 100;
alter table player alter column belong set default 0;
alter table player alter column mail set unique;

alter table things add constraint things_to_supply foreign key(pid) references supply(id) on delete cascade on update cascade;
alter table things add constraint things_to_arm foreign key(pid) references arm(id) on delete cascade on update cascade;

insert into supply (id,name,price,hp,fatigue) values(1,'大蟠桃',20,10,10);
insert into supply (id,name,price,hp,fatigue) values(2,'热翔',50,100,-20);
insert into supply (id,name,price,hp,fatigue) values(3,'鸡腿',50,30,30);
insert into supply (id,name,price,hp,fatigue) values(4,'脉动',80,-10,80);
insert into supply (id,name,price,hp,fatigue) values(5,'炫迈',200,100,100);

insert into arm (id,name,price,attack,defence) values(1,'木棍',50,100,100);
insert into arm (id,name,price,attack,defence) values(2,'倚天剑',400,1000,1000);
insert into arm (id,name,price,attack,defence) values(3,'无形装逼',20000,10000,10000);
insert into arm (id,name,price,attack,defence) values(4,'挂',1000000,999999,999999);

insert into things (id,pid,jhi_type,belong) values(1,1,0,1001);
insert into things (id,pid,jhi_type,belong) values(2,1,1,1001);


select supply.name,supply.price,supply.hp,supply.fatigue from supply inner join things on supply.id=things.pid where things.jhi_type=0 and things.belong=1001;
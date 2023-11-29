create database Dbase

create table address \
	(addrId int, street varchar, city varchar, \
	state char(2), zip int, primary key(addrId))

create table name(first varchar(10), last varchar(10), addrId integer)

insert into address values( 0,'12 MyStreet','Berkeley','CA','99999')
insert into address values( 1, '34 Quarry Ln.', 'Bedrock' , 'XX', '00000')

insert into name VALUES ('Fred',  'Flintstone', '1')
insert into name VALUES ('Wilma', 'Flintstone', '1')
insert into name (last,first,addrId) VALUES('Holub','Allen',(10-10*1))

update address set state = "AZ"  where state = "XX"
update address set zip = zip-1 where zip = (99999*1 + (10-10)/1) 

insert into name (last,first) VALUES( 'Please', 'Delete' )
delete from name where last like '%eas%'

select * from address
select * from name

create database Dbase

create table name2 \
( \
    id     integer, \
    first  varchar(10), \
    last   varchar(10), \
    addrId integer, \
    primary key (id) \
)

    insert into name2 VALUES (0, 'Fred',  'Flintstone', '1')
insert into name2 VALUES (1, 'Wilma', 'Flintstone', '1')
insert into name2 VALUES (1, 'Wilma', 'Flintstone', '1')
select * from name
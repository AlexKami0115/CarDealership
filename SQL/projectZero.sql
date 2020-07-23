drop table purchases cascade constraints;
drop table offers cascade constraints;
drop table carLot cascade constraints;
drop table appUser;


drop sequence appUser_seq;
drop sequence carLot_seq;
drop sequence offers_seq;
drop sequence purchases_seq;

-- used to store customers/employees
create table appUser (
    usrID number(5) primary key,
    username varchar2(14) not null unique,
    passwrd varchar2(14) not null,
    stringAccess varchar2(10) not null ---used to declare if employee or customer
);

-- used to store cars
create table carLot (
    cID number(5) primary key,
    carMake varchar2(14) not null,
    carModel varchar2(14) not null,
    carColor varChar2(10) not null,
    carMileage number(7) not null,
    carYear number(4) not null check(carYear>=1886),
    carPrice number(8) not null,
    ownerID number(10),
    status varChar(20) DEFAULT 'available',
    constraint fk_car_user foreign key(ownerID) references appUser(usrID)
);

-- used to store offers, references appUser and carLot for info
create table offers (
    id number(5) primary key,
    userID number(5) not null,
    carID number(5) not null,
    offer number(8) not null check(offer>=0),
    statee varChar(17) not null,
    downPayment number(8) not null,
    constraint fk_offers_appUser foreign key (userID) references appUser(usrID),
    constraint fk_offers_carLot foreign key (carID) references carLot(cID)
);

-- stores purchases, references offers table to get carLot and appUser
create table purchases (
    pID number (5) primary key,
    paidOff number(8),
    offerInfo number(5) not null,
    constraint fk_purchases_offers foreign key (offerInfo) references offers(id)
);

create sequence appUser_seq nocache;
create sequence carLot_seq nocache;
create sequence offers_seq nocache;
create sequence purchases_seq nocache;

-- Triggers
create or replace trigger appUser_pk_trig 
before insert or update on appUser
for each row
begin
    if INSERTING then
        select appUser_seq.nextVal into :new.usrID from dual;
    elsif UPDATING then
        select :old.usrID into :new.usrID from dual;
    end if;
end;
/

create or replace trigger carLot_pk_trig 
before insert or update on carLot
for each row
begin
    if INSERTING then
        select carLot_seq.nextVal into :new.cID from dual;
    elsif UPDATING then
        select :old.cID into :new.cID from dual;
    end if;
end;
/

create or replace trigger offers_pk_trig 
before insert or update on offers
for each row
begin
    if INSERTING then
        select offers_seq.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/

create or replace trigger purchases_pk_trig 
before insert or update on purchases
for each row
begin
    if INSERTING then
        select purchases_seq.nextVal into :new.pID from dual;
    elsif UPDATING then
        select :old.pID into :new.pID from dual;
    end if;
end;
/
---- INSERTING BASE DATA ----

insert into appUser (usrID, username, passwrd, stringAccess) values (1, 'alexkam123', 'p4ssw0rd', 'employee');
insert into appUser (usrID, username, passwrd, stringAccess) values (2, 'alex', 'p4ssw0rd', 'customer');
insert into appUser (usrID, username, passwrd, stringAccess) values (3, 'stephan', 'p4ssw0rd', 'customer');
insert into appUser (usrID, username, passwrd, stringAccess) values (4, 'jeff', 'p4ssw0rd', 'customer');
insert into appUser (usrID, username, passwrd, stringAccess) values (5, 'ian', 'p4ssw0rd', 'customer');


insert into carLot (cID, carMake, carModel, carColor, carMileage, carYear, carPrice) values
        (1, 'Jeep', 'Wrangler', 'Red', 50000, 2010, 11000);

insert into carLot (cID, carMake, carModel, carColor, carMileage, carYear, carPrice) values
        (2, 'Volkswagen', 'GTI', 'Black', 100000, 2008, 9000);
        
insert into carLot (cID, carMake, carModel, carColor, carMileage, carYear, carPrice) values
        (3, 'Chevrolet', 'Camaro', 'Yellow', 15000, 2016, 24000);
insert into carLot (cID, carMake, carModel, carColor, carMileage, carYear, carPrice, status, ownerID) values
        (4, 'Subaru', 'Legacy', 'White', 60000, 2013, 10000, 'notAvailable', 2);

        

insert into offers (id, userID, carID, offer, statee, downPayment) values (1, 3, 2, 7000, 'pending', 1000);
insert into offers (id, userID, carID, offer, statee, downPayment) values (2, 3, 2, 7000, 'pending', 2000);
insert into offers (id, userID, carID, offer, statee, downPayment) values (3, 4, 3, 8000, 'pending', 2000);
insert into offers (id, userID, carID, offer, statee, downPayment) values (4, 2, 2, 7000, 'pending', 1000);
        
commit;

------------------------
--- STORED PROCEDURE ---
------------------------
create or replace procedure acceptOffer(offer_id in number)
as cursor carOffers is select id from offers where carID=(select carID from offers where id=offer_id);

--START STORED PROCEDURE
begin
    for r in carOffers
    loop
        update offers set statee= 'rejected' where id=r.id; --rejecting offers put on the car
    end loop;
    -- CHANGING STATE OF OFFER TO "ACCEPTED"
    update offers set statee= 'accepted' where id=offer_id;
    -- SETTING NEW OWNER OF CAR & SET STATUS TO = NOT AVAILABLE (MEANING IT IS OWNED)
    update carLot set ownerID=(select userID from offers where id=offer_id) where cID=(select carID from offers where id=offer_id);
    update carLot set status = 'NotAvailable' where cID = (select carID from offers where id = offer_id);
    
    commit;
end;
/
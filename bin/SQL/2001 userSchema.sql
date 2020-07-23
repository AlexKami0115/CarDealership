
-- used to store customers/employees
create table appUser (
    id number(50) primary key,
    username varchar2(14) not null unique,
    passwrd varchar2(14) not null,
    stringAccess varchar2(10) not null ---used to declare if employee or customer
);

-- used to store cars
create table carLot (
    id number(50) primary key,
    carMake varchar2(14) not null,
    carModel varchar2(14) not null,
    carColor varChar2(10) not null,
    carMileage number(7) not null,
    carYear number(4) not null check(carYear>=1886),
    carPrice number(8) not null,
    VIN varChar2(14) not null unique  
);

-- used to store offers, references appUser and carLot for info
create table offers (
    id number(50) primary key,
    userID number(50) not null,
    carID number(50) not null,
    offer number(8) not null check(offer>=0),
    constraint fk_offers_appUser foreign key (userID) references appUser(id),
    constraint fk_offers_carLot foreign key (carID) references carLot(id)
);

-- stores purchases, references offers table to get carLot and appUser
create table purchases (
    id number (50) primary key,
    offerInfo number(50) not null,
    balance number(8),
    paidoff number(8),
    constraint fk_purchases_offers foreign key (offerInfo) references offers(id)
)


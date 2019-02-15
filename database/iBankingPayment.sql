create database iBankingPayment collate utf8_bin;

use iBankingPayment;

create table bank_account (
	bank_id varchar(10) primary key not null,
	name nvarchar(120) not null collate utf8_bin,
	phone varchar(10) not null,
	email text not null,
	balance int not null
);

create table account (
	bank_id varchar(10) primary key not null,
	password varchar(15) not null,
	constraint fk_acc_bacc foreign key (bank_id) references bank_account(bank_id)
);

create table student (
	bank_id varchar(10) primary key not null,
	student_id varchar(8) not null unique,
	school_fee int not null,
	constraint fk_stu_ac foreign key (bank_id) references bank_account(bank_id)
);

create table transaction (
	trans_id varchar(7) not null,
	send_id varchar(10) not null,
	receive_id varchar(10) not null,
	amount int not null,
	primary key(trans_id, send_id, receive_id),
	constraint fk_trans_bacc foreign key (send_id) references bank_account(bank_id),
	constraint fk_trans_bacc2 foreign key (receive_id) references bank_account(bank_id)
);

create table opt (
	opt_code char(5) primary key not null,
	status int not null,
	trans_id varchar(7) not null unique,
	constraint chk_status check(status = 0 or status = 1 or status = 2),
	constraint fk_opt_trans foreign key (trans_id) references transaction(trans_id)
);
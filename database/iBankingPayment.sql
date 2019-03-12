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

create table otp (
	otp_code char(5) primary key not null,
	status int not null,
	init_time datetime not null,
	constraint chk_status check(status = 0 or status = 1)
);

create table transaction (
	trans_id int primary key not null auto_increment,
	send_id varchar(10) not null,
	amount int not null,
	otp_code char(5) unique,
	constraint fk_trans_bacc foreign key (send_id) references bank_account(bank_id),
	constraint fk_trans_otp foreign key (otp_code) references otp(otp_code)
);

insert into bank_account values('batisuoc', 'Trinh Hằng Ước', '0798237964', 'zhenghengyue@gmail.com', 100000000);
insert into bank_account values('loc', 'Nguyễn Hông Lộc', '0112211221', 'batisuoc@gmail.com', 50000000);

insert into account values('batisuoc', '123456');
insert into account values('loc', 'locyolo');

insert into student values('batisuoc', '51503032', 7000000);
insert into student values('loc', '51503135', 7000000);

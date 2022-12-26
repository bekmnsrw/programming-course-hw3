drop table if exists doctor cascade ;
drop table if exists patient cascade ;
drop table if exists reception cascade ;

create table doctor (
    id bigserial primary key,
    first_name varchar(20),
    second_name varchar(20),
    speciality varchar(20)
);

create table patient (
    id bigserial primary key,
    first_name varchar(20),
    second_name varchar(20),
    phone_number varchar(12)
);

create table reception (
    id bigserial primary key,
    reception_date varchar(10),
    start_time varchar(5),
    end_time varchar(5),
    doctor_second_name varchar(20),
    patient_second_name varchar(20),
    doctor_id bigserial,
    foreign key(doctor_id) references doctor(id),
    patient_id bigserial,
    foreign key(patient_id) references patient(id)
);
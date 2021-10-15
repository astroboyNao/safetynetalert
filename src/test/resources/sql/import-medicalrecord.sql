DELETE FROM medical_record_allergies;
DELETE FROM medical_record_medications;
DELETE FROM medical_record;
DELETE FROM allergie;
DELETE FROM medication;

insert into allergie (id, name) values (301,'allergie_01');
insert into medication (id, name) values (401,'medication_01');
insert into medical_record (id, person_id) values (501,select id from person where firstname='FIRSTNAME_1');

insert into medical_record_medications (medical_record_id, medications_id) values (501, 401);
insert into medical_record_allergies (medical_record_id, allergies_id) values (501, 301);


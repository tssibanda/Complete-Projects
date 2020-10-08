-- Student Name:    Thamsanqa Sibanda
-- Student Number:  C18727971
-- Date:            22 April 2020
-- Question:        Transaction


-- Insert new Advert with a new advertisment type of your choice
-- Sequence, Substitution Variables
drop SEQUENCE adtype_sq;
create SEQUENCE adtype_sq
start with 08
increment by 1
maxvalue 99
minvalue 8
cache 3
cycle
order;
insert into HH_AdType  values(adtype_sq.nextval,'&&adtype');
insert into hh_adverts(addate,adcost,pid,adtypeid) values ('&addate','&adcost',1008,(select adtypeid from hh_adtype where adtype like '&adtype'));
undefine adtype;
drop SEQUENCE adtype_sq;
commit;

-- display results after the above transaction
select paddr as "Property Address", addate as "Advert Date", 
TO_CHAR(adcost,'L999,999,999,999.99','NLS_NUMERIC_CHARACTERS = '', '' NLS_CURRENCY = ''€'' ')"Advertisment Cost", 
Adtype as "Advertisment Type" from hh_property
join hh_adverts using (pid)
join hh_adtype using (adtypeid);

-- Student Name:    Thamsanqa Sibanda
-- Student Number:  C18727971
-- Date:            22 April 2020
-- Question:        Selects

-- display all propertie's addresses and asking prices
-- Select, Project
select paddr as "Address",
TO_CHAR(minaskprice,'L999,999,999,999.99','NLS_NUMERIC_CHARACTERS = '', '' NLS_CURRENCY = ''€'' ') "Owner's Asking Price"
from hh_property; 

-- Display Properties that have not been valued, when they are due to be valued and the agent to valuate them
-- Select, Project, Join
select appdate as "Valuation Appointment Date", paddr as "Property Address", aname as "Valuation Agent" 
from valu_app
join hh_property using (pid)
join hh_agent using (agentid);


-- Display residential properties that are not marked as sold, property owner name, property type and property category, name of the agent responsible for the sale and the agent's Branch name
-- Select, Project, Join, Minus
select c.cname as "Property Owner", paddr as "Property For Sale", s.salestatus as "Sale Status", protypename as "Property Type", catname as "Category",
TO_CHAR(s.saleprice,'L999,999,999,999.99','NLS_NUMERIC_CHARACTERS = '', '' NLS_CURRENCY = ''€'' ') "Sale Price" ,
aname as "Sales Agent", bname as "Branch" 
from HH_clients c
join p_forsale s on (s.sellerid=c.cid) join hh_property p using(pid)
join hh_agent using (agentid) join hh_branch using (bid)
join hh_protype t on (t.protypeid = p.protypeid) join hh_procat using (catid)
where catname like 'Residential'
minus
select c.cname as "Property Owner", paddr as "Property For Sale", s.salestatus as "Sale Status", protypename as "Property Type", catname as "Category",
TO_CHAR(s.saleprice,'L999,999,999,999.99','NLS_NUMERIC_CHARACTERS = '', '' NLS_CURRENCY = ''€'' ') "Sale Price" ,
aname as "Sales Agent", bname as "Branch" 
from HH_clients c
join p_forsale s on (s.sellerid=c.cid) join hh_property p using(pid)
join hh_agent using (agentid) join hh_branch using (bid)
join hh_protype t on (t.protypeid = p.protypeid) join hh_procat using (catid)
where s.salestatus like 'Sold';


-- Dsplay the properties Chris Puglia and Harry Croston are selling
-- Select, Project, Join, Union
select paddr as "Property Address", 
TO_CHAR(minaskprice,'L999,999,999,999.99','NLS_NUMERIC_CHARACTERS = '', '' NLS_CURRENCY = ''€'' ') "Client Asking Price", 
aname as "Agent Name" from hh_property join hh_agent using (agentid) where aname like 'Cris Puglia'
union
select paddr as "Property Address", 
TO_CHAR(minaskprice,'L999,999,999,999.99','NLS_NUMERIC_CHARACTERS = '', '' NLS_CURRENCY = ''€'' ') "Client Asking Price", 
aname as "Agent Name" from hh_property join hh_agent using (agentid) where aname like 'Harry%'
union
select paddr as "Property Address", 
TO_CHAR(minaskprice,'L999,999,999,999.99','NLS_NUMERIC_CHARACTERS = '', '' NLS_CURRENCY = ''€'' ') "Client Asking Price",
aname as "Agent Name" from hh_property join hh_agent using (agentid) where aname like 'Marguerite Alvarez'
ORDER by "Client Asking Price";

-- Display the total number of properties each agen is in charge of selling
-- Select, Project, Join, Aggregation
select aname as "Agent Name", count(paddr) as "Total Properties", 
TO_CHAR(sum(minaskprice),'L999,999,999,999.99','NLS_NUMERIC_CHARACTERS = '', '' NLS_CURRENCY = ''€'' ') "Value of Portfolio" 
FROM hh_property join hh_agent using (agentid) GROUP BY aname;


--Display Properties that have advertisments in the shop agency window in common but also have Newspaper advertisements or on site for sale sign
-- Select, Project, Intersect, Join
select * from hh_adverts;
select paddr as "Property Address", adType as "Type of Advertisment" from hh_property
join hh_adverts using (pid) join hh_adtype using (adtypeid) where adtype like 'Agency%' or adtype like 'Newspaper%'
intersect
select paddr, adType from hh_property join hh_adverts using (pid) 
join hh_adtype using (adtypeid) where adtype like 'Agency%' or adtype like 'On%';

select cname, count(pid) as "Number of properties bought" from hh_clients
join p_forsale on buyerid = cid
group by cname; 

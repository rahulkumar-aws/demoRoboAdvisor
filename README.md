```sql
create database stashaway;
use stashaway;  

create table customer(
email varchar(255) primary key, 
name varchar(255), 
dob varchar(255), 
country varchar(255),
isVerified bool,
isKYC bool,
confidence_level varchar(255)
)

create table portfoliofact(
email varchar(255), 
pId varchar(255), 
pType varchar(255),
amount decimal(10,3),
PRIMARY KEY (email, pType)
)

create table depositfact(
email varchar(255), 
depositId varchar(255), 
depositType varchar(255),
portfolioType varchar(255),
depositAmount decimal(10,3),
PRIMARY KEY (depositId)
)

create table wallet(
email varchar(255) ,
fund decimal(10,3),
PRIMARY KEY (email)
)
```

REST CALLS

```bash
1. Create Customer


curl \
    --header "Content-type: application/json" \
    --request POST \
    --data '{"email":"rahulkumar.aws@gmail.com", "name":"dummyoooxxx", "dob": "1985-09-06", "country": "India", "isVerified": false, "isKYC": false}' \
    http://localhost:9000/customer/add

2. Add Fund

curl \
	--header "Content-type: application/json" \
	--request POST \
	--data '{"email":"rahulkumar.aws@gmail.com","code":"some-code", "fund": 10000.0}' \
	http://localhost:9000/wallet/add 

3. Create Portfolio

-- highrisk
-- retirement 

curl \
    --header "Content-type: application/json" \
    --request POST \
    --data '{"email":"rahulkumar.aws@gmail.com", "pId":"dummypId", "pType": "highRisk", "amount": 1000.0}' \
    http://localhost:9000/portfolio/add

4. Deposit

    curl \
    --header "Content-type: application/json" \
    --request POST \
    --data '{"email":"rahul@gmail.com", "depositId":"dummypId11", "depositType": "onetime", "portfolioSplit": [{"portfolioType" : "highrisk", "amount": 1000.89}, {"portfolioType" : "retirement", "amount": 178.99}]}' \
    http://localhost:9000/deposit/add  
```

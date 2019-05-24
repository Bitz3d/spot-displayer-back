# spot-displayer-back

project in progress

1. to lunch u have to add file in path -> /src/main/resources/config.propperties with 

- database.login="database-login"
- database.password="database-password"
- target.upload.folder="folder-name"

2. Install PostgreSql

in order to generate tables in databse change application.properties -> spring.jpa.hibernate.ddl-auto=create-drop

and in table `role` 2 insterts

insert into role (id,role) values (1,"ADMIN");
insert into role (id,role) values (2,"USER");
 
3 In console type:
I. mvn package 
II. java -jar spot-displayer-0.0.1-SNAPSHOT.jar 

# You can chceck running app

1. You have to register as user

ec2-52-30-135-35.eu-west-1.compute.amazonaws.com:8080/signup

Body
JSON format
{
	"username":"<userName>",
	"password":"<password>"
}

2. You have to generate acces token

ec2-52-30-135-35.eu-west-1.compute.amazonaws.com:8080/token/generate-token

Body
JSON format
{
	"username":"<userName>",
	"password":"<password>"
}
 
3. With generated token u have access to user's end-points

ec2-52-30-135-35.eu-west-1.compute.amazonaws.com:8080/api/user

Header
Authorization Bearer <generated-token>

## this end point just return String "test user" made to test Role in application


ec2-52-30-135-35.eu-west-1.compute.amazonaws.com:8080/upload


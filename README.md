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


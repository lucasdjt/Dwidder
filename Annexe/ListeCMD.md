### WINDOWS

- dans WEB-INF : 
> pg_ctl -D "C:\Program Files\PostgreSQL\17\data" restart
> psql -U postgres -d reseau_social
> \i setup.sql

- dans WEB-INF\classes : 
> .\remove.bat
> .\compile.bat

- dans le répertoire de tomcat\bin : 
> .\catalina.bat run

### LINUX

- dans WEB-INF :
> psql -h psqlserv -U lucasdejesusteixeiraetu but2
> \i setup.sql

- dans WEB-INF\classes : 
> .\remove.sh
> .\compile.sh

- dans le répertoire de tomcat\bin : 
./catalina.sh run
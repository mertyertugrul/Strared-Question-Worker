# Description
With a given list of questions IDs as a file this app 
returns number of IDs either random or as a list.

The returned ID is selected from minimum number of 
timed solved questions. Therefor, less solved questions
prioritized.

# Usage
The resource file format is like this:
```text
29/12/2023,enthuware.ocpjp.v17.2.1136
This is very important learn it very well!,
20/12/2023,enthuware.ocpjp.v17.2.1137
Never forget,
18/12/2023,enthuware.ocpjp.v17.2.1140
Needed to know.,
```
The app also requires a simple database server.
The table initialization script is provided.

The credentials are read from config.properties file
```properties
db.url=jdbc:postgresql://localhost:5432/DBNAME
db.user=DBUSER
db.password=SECRETPASSWORD
```
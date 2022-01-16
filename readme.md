# Description
Basic program that analyze app usage data for a hypothetical menu planning calendar app where  a user can plan multiple meals, and each meal may have multiple dishes.  

`/src/main/resources/data` directory contains file with userId.json.   

Based on the json structure return number of users with given type active(meal count >=5), superactive(meal count >=10), bored(active before and not active for given period) for a given date range.

# Technology used
- Java 17
- Springboot

# Build
``` 
./gradlew bootJar 
```

# Run 
``` 
 java -jar build/libs/menu-planner-0.0.1-SNAPSHOT.jar active 2016-09-01 2016-09-08
 java -jar build/libs/menu-planner-0.0.1-SNAPSHOT.jar superactive 2016-09-01 2016-09-08
 java -jar build/libs/menu-planner-0.0.1-SNAPSHOT.jar bored 2016-09-01 2016-09-08
```

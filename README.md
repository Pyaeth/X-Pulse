# X-Pulse project
#### --> Backend of the project (current): [BO-XPulse](https://github.com/Pyaeth/BO-XPulse)
#### --> Frontend of the project (in Angular 6): [xpulseFrontEnd](https://github.com/Pyaeth/xpulseFrontEnd)

### Project description
#### General purpose
X-Pulse is a work-in-progress web app which is meant to facilitate monitoring your user’s finances. Simply put, Xpulse should give you statistical data based on your bank account statements, which can be uploaded in the app. A work-in-progress step here is to introduce a machine-learning algorithm in order to improve data analysis, so that the entire statistical system will eventually work accurately regardless of the bank (and therefore, of the statement formatting).
#### Technical specs
X-Pulse is a full-stack Spring Boot web application designed in respect with MVC architecture, component separation on the Angular side, use of node packages such as Chart.js and MDBootstrap. 

_**Maven**_ profiles are used to differentiate between development and production environments so that _**application.properties**_ are specific to the environment. In addition to this, _**log4j2**_ is configured to use a system environment variable so that the logging folder is environment based (e.g.: development happens on a _**Windows 10**_ machine while production is held on a Linux-based system and logs are stored on an external HDD in order to reduce SD-card usage of the _**Raspberry Pi**_).
#### Technologies used
- **Front-End**: Angular 6, Material-Design Bootstrap, Chartjs, HTML&CSS, node.js, npm
- **Back-End**: Spring Boot App, Java 8
- **Build tool**: Maven 3
- **Application server**: Apache Tomcat 9
- **Version management**: Git
- **Database**: MariaDB
#### Presented statistical data
-	Selected timeframe of reference (daily, monthly, yearly) comparisons of transactions and balance
-	Transactions can be shown in terms of number of transactions or in terms of amounts transactioned
-	Chart data contains categories of spendings (built using Chart.js)
- Selectable currency
### Further feature development
-	Several bank accounts per user
-	Tailored experience: get individual tips based on statistics to improve savings
-	Starting from a default list of categories for chart data, this list will later become user-customizable and each user will get to choose if he wants to keep this custom-made category private or share it with the other users
- Implementation of machine-learning mechanism to support several types of bank account statements
- Upload of shopping receipt for a more detailed report of spendings (also by phone -> to be discussed: mobile app development)

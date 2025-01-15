#####  Microservices  #######

1. Install and run config-server on port 8071. Validate accessing the url http://localhost:8071/accounts/qa  
2. Install and run eureka-server on port 8070. Validate accessing the url http://localhost:8070/
3. Install and run loans-ms on port 8090.
4. Install and run accounts-ms on port 8080.
5. Install and run cards-ms on port 8080.
6. Install and run message-ms on port 9010.
7. Install and run gateway-server on port 8072.
8. Install and run other servers i.e. kafka, prometheus , grafana and keycloak etc. 
9. Open the keycloak to setup the user and client for grant type flow with credentials. http://localhost:7080/admin/master/console/#/master/clients 
   a. Register client "easybank-callcenter-cc" for client credentials grant type flow. with Client authentication on and Service accounts roles checked.
      and save and get the client-id and secret from Credentials. i.e 
            client-id: easybank-callcenter-cc
            client-secret: cvBaI71JRIpgTC6scfDrWtWFlRAXDbXI
      
   b. Create and Add the Role for authorization Realm-Roles->Create-Role & Client->Service-accounts-roles->Assign role
   c. Update the post-man request and verify gateway-security->Account_POST_ClientCredential
10. Open the keycloak to setup the user and client for grant type flow with auth code. (keycloak-ui redirect)
   a. Register "easybank-callcenter-ac" enable Client-authentication & Check only standard flow and add * to redirection URL and web origins
    and save and get the 
          client-id: easybank-callcenter-ac
          client-secret: Btw5xoPMitGHh8bcWaPXDz4TSegUeNtJ
    create the user Users->Add User and set the password. abhishek/abhishek
    Add the role ACCOUNTS,CARDS,LOANS to the user.
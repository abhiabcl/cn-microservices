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
   a. Register client "easybank-callcenter-cc" for client credentials grant type flow. 
         with **Client authentication** on and **Service accounts roles** checked.
      and save and get the client-id and secret from Credentials. i.e 
            client-id: eazybank-callcenter-cc
            client-secret: 3x7RydFjimIIdAoudpsQdu3W019jOkJ5
      
   b. Create and Add the Role for authorization Realm-Roles->Create-Role & Client-**>Service-accounts-roles**->Assign role
   c. Update the post-man request and verify gateway-security->Account_POST_ClientCredential
10. Open the keycloak to setup the user and client for grant type flow with auth code. (keycloak-ui redirect)
   a. Register "easybank-callcenter-ac" enable **Client-authentication** & **Check only standard flow**
       and add * to **redirection URL** and **web origins**
    and save and get the 
          client-id: eazybank-callcenter-ac
          client-secret: d5oILZW9sJNotUU5gp8Nhw84CcP4fPyJ
    create the user Users->Add User and set the password. abhishek/abhishek
    Add the role ACCOUNTS,CARDS,LOANS to the user.
11. Open the prometheus to verify http://localhost:9090/ and search the metric name i.e. http_client_requests_seconds_sum .
12. Open the http://localhost:3000/ and check the datasource for prometheus, loki and tempo. And explore the datasource to verify the 
    metric, logs and traceid ( extraction expression configured in loki datasource).
13. Grafana
    1. Get the application URL by running these commands:
       echo "Browse to http://127.0.0.1:8080"
       kubectl port-forward svc/grafana 8080:3000 &

    2. Get the admin credentials:

       echo "User: admin"
       echo "Password: $(kubectl get secret grafana-admin --namespace default -o jsonpath="{.data.GF_SECURITY_ADMIN_PASSWORD}" | base64 -d)"
    # Note: Do not include grafana.validateValues.database here. See https://github.com/bitnami/charts/issues/20629



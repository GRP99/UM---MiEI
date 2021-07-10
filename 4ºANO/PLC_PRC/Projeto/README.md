# PRC 2021
<div align="center">
    <img src="Images/favicon.ico">
    <h1>Platform to store and consult "Cocktails"</h1>
    <br>
</div>


## How to run
1. Start GraphDB
    1. Create a repository with the name "projectPRC";
    2. Import this [file](/Ontologies/ontology-inferred.ttl) into the created repository;
    3. Make sure GraphDB is listening on port 7200.
2. Start API-Server
    1. Open terminal and access the following directory "[api-server](/App/api-server)";
    2. Run <code>npm i</code>

    3. Run <code>npm start</code>
    
    4. Check if API-Server is listening at the port 7300.
3. Start View-Server
    1. Open terminal and access the following directory "[view-server](/App/view-server)";
    2. Run <code>npm i</code>

    3. Run <code>npm start</code>
    
    4. Check if API-Server is listening at the port 7301.

---
## Dependencies
* **[GraphDB](https://www.ontotext.com/products/graphdb/graphdb-free/)**
* **[Node](https://nodejs.org/en/)**

---
<div dir="rtl"> 
    <b>Authors:</b> Gonçalo Pinto (A83732) and Luís Ribeiro (A85954)
</div>

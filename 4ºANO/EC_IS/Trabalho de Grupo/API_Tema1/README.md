# EHR REST API

## Electronic Health Records (EHR) management system based on the OpenEHR clinical model.

---

### How to Run
1. Start EHR-management
    1. Open the terminal and access the following folder "[EHR-management](https://github.com/GRP99/IS/tree/master/Trabalho%20Pr%C3%A1tico/EHR-management)";
    2. Execute ```npm i```;
    3. Execute ```npm start```;
    4. Verify that EHR-management is running on port 2021;
    5. Also check if the connection to MongoDB was successful.

---

1. [Management of EHRs](https://specifications.openehr.org/releases/ITS-REST/latest/ehr.html#ehr):
    1. Create EHR : ```POST /ehr```;
    2. Create EHR with id : ```PUT /ehr/{ehr_id}```;
    3. Get EHR summary by id : ```GET /ehr/{ehr_id}```;
    4. Get EHR summary by subject id : ```GET /ehr{?subject_id,subject_namespace}```.

2. [Management of the directory FOLDER resource](https://specifications.openehr.org/releases/ITS-REST/latest/ehr.html#directory):
    1. Create directory : ```POST /ehr/{ehr_id}/directory```;
    2. Update directory : ```PUT /ehr/{ehr_id}/directory```;
    3. Delete directory : ```DELETE /ehr/{ehr_id}/directory```;
    4. Get folder in directory versionGET : ```GET /ehr/{ehr_id}/directory/{version_uid}{?path}```;
    5. Get folder in directory : ```GET /ehr/{ehr_id}/directory{?path}```.

3. [Management of CONTRIBUTION resources](https://specifications.openehr.org/releases/ITS-REST/latest/ehr.html#contribution):
    1. Create contribution ```POST /ehr/{ehr_id}/contribution```;
    2. Get contribution by id ```GET /ehr/{ehr_id}/contribution/{contribution_uid}```.

---
## Swagger
<p> Through EHR-management, we can manage EHRs, the directory FOLDER resource and CONTRIBUTION resources. However, to access them they need clear documentation.
<p> In order to solve this problem was used <a href="http://localhost:2021/api-docs/"><i>Swagger UI</i></a> that is the largest framework for designing APIs using a common language and enabling the development across the whole API lifecycle, including documentation, design, testing, and deployment.

---

## Dependencies
* **[Node](https://nodejs.org/en/)**
* **[Mongo](https://www.mongodb.com/try/download/community)**

---

<div dir="rtl"> 
    <b>Note:</b> All information created and presented respects the formal specification presented in the <a href="https://specifications.openehr.org/releases/RM/latest/ehr.html"><i>EHR Information Model</i></a>
</div>

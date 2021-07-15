const checkConnection = module.exports

'use strict';
const oracledb = require('oracledb');
oracledb.initOracleClient({ libDir: 'C:\\Oracle\\instantclient_19_9' });

const oracleDbRelease = function(conn) {
    conn.release(function (err) {
        if (err){
            console.log(err.message);
        }      
    });
}

function queryObject(sql, bindParams, options) {
    //console.log(sql);
    options['outFormat'] = oracledb.OBJECT;

    return new Promise(function(resolve, reject) {       
        oracledb.getConnection(
                  {
                    user          : "monitor",
                    password      : "database",
                    connectString : "//localhost:1521/dbmonitor.localdomain"
                })
        .then(function(connection){
            connection.execute(sql, bindParams, options)
            .then(function(results) {
                // console.log(results)
                resolve(results);
                // console.log(results)
                process.nextTick(function() {
                    oracleDbRelease(connection);
                });
            })
            .catch(function(err) {
                console.log(err)
                reject(err);
 
                process.nextTick(function() {
                    oracleDbRelease(connection);
                });
            });
        })
        .catch(function(err) {
            reject(err);
        });
    });
}

checkConnection.getUsers = () => {
    return queryObject("SELECT * FROM Users", {}, {outFormat: ""});
} 

checkConnection.getUsersTimeStamps = (tableS) => {
    return queryObject("SELECT (DISTINCT USERNAME),TIMESTAMP FROM USERS WHERE DEFAULT_TABLESPACE='"+tableS+"'", {}, {outFormat: ""});
}  

checkConnection.getTablespaces = () => {
    return queryObject("SELECT TABLESPACE_NAME,TABLESPACE_SIZE,STATUS,TIMESTAMP FROM TABLESPACES", {}, {outFormat: ""});
}

checkConnection.getTimeStamps = (table) => {
    return queryObject("SELECT DISTINCT TIMESTAMP FROM "+table, {}, {outFormat: ""});
}

checkConnection.getTablespaceData = (tName,tTS) => {
    return queryObject("SELECT * FROM TABLESPACES T WHERE T.TABLESPACE_NAME='"+tName+"' AND T.TIMESTAMP=to_date('"+tTS+"','DD-MM-YYYY HH24:MI:SS')", {}, {outFormat: ""});
}

checkConnection.getLastTimestamps = (tName) => {
    return queryObject("SELECT TIMESTAMP, FREE, USED FROM TABLESPACES T WHERE T.TABLESPACE_NAME='"+tName+"' ORDER BY TIMESTAMP DESC FETCH NEXT 5 ROWS ONLY", {}, {outFormat: ""});
}

checkConnection.getDataFilesTablespace = (tName, tTS) => {
    return queryObject("SELECT * FROM DATAFILES D WHERE D.TABLESPACE_NAME= '"+tName+"' AND D.TIMESTAMP=to_date('"+tTS+"','DD-MM-YYYY HH24:MI:SS')", {}, {outFormat: ""});
}

checkConnection.getDataFiles = () => {
    return queryObject("SELECT * FROM DATAFILES", {}, {outFormat: ""});
}

checkConnection.getDataFileTimeStamp = (fName, TS) => {
    var nid = fName.replace(/!/g,"/");
    return queryObject("SELECT * FROM DATAFILES D WHERE FILENAME= '"+nid+"' AND D.TIMESTAMP=to_date('"+TS+"','DD-MM-YYYY HH24:MI:SS')", {}, {outFormat: ""});
}

checkConnection.getUsersTableSpaceTimeStamp = (tableSpace, TS) => {
    return queryObject("SELECT USERNAME,ACCOUNT_STATUS FROM USERS D WHERE DEFAULT_TABLESPACE= '"+tableSpace+"' AND D.TIMESTAMP=to_date('"+TS+"','DD-MM-YYYY HH24:MI:SS')", {}, {outFormat: ""});
}

checkConnection.getDataFilesBytes = (fName) => {
    var nid = fName.replace(/!/g,"/");
    return queryObject("SELECT TIMESTAMP, BYTES, MAXBYTES FROM DATAFILES D WHERE FILENAME= '"+nid+"' ORDER BY TIMESTAMP DESC FETCH NEXT 5 ROWS ONLY", {}, {outFormat: ""});
}

checkConnection.getDataFilesBlocks = (fName) => {
    var nid = fName.replace(/!/g,"/");
    return queryObject("SELECT TIMESTAMP, BLOCKS, MAXBLOCKS FROM DATAFILES D WHERE FILENAME= '"+nid+"' ORDER BY TIMESTAMP DESC FETCH NEXT 5 ROWS ONLY", {}, {outFormat: ""});
}

checkConnection.getCPU = (user) => {
    return queryObject("SELECT * FROM CPU WHERE USERNAME='"+user+"' ORDER BY TIMESTAMP DESC FETCH NEXT 10 ROWS ONLY", {}, {outFormat: ""});
}

checkConnection.getCPUUsers = () => {
    return queryObject("SELECT DISTINCT USERNAME FROM CPU ", {}, {outFormat: ""});
}

checkConnection.getSystemGlobalArea = () => {
    return queryObject("SELECT * FROM System_Global_Area ORDER BY TIMESTAMP DESC FETCH NEXT 10 ROWS ONLY", {}, {outFormat: ""});
}

checkConnection.getProgramGlobalArea = () => {
    return queryObject("SELECT * FROM Program_Global_Area ORDER BY TIMESTAMP DESC FETCH NEXT 10 ROWS ONLY", {}, {outFormat: ""});
}

checkConnection.getSessionsTimestampUser = (user) => {
    return queryObject("SELECT * FROM SESSIONS WHERE USERNAME='"+user+"' ORDER BY TIMESTAMP DESC FETCH NEXT 10 ROWS ONLY", {}, {outFormat: ""});
}

checkConnection.getSession = (sid,TS) => {
    return queryObject("SELECT * FROM SESSIONS WHERE USERNAME='"+sid+"' AND TIMESTAMP=to_date('"+TS+"','DD-MM-YYYY HH24:MI:SS')", {}, {outFormat: ""});
}

checkConnection.getSessions = () => {
    return queryObject("SELECT * FROM SESSIONS", {}, {outFormat: ""});
}

checkConnection.getSessionsUser = (user) => {
    return queryObject("SELECT USERNAME, TIMESTAMP FROM SESSIONS WHERE USERNAME='"+ user +"'", {}, {outFormat: ""});
}

checkConnection.getRolesTS = (sid,TS) => {
    return queryObject("SELECT * FROM ROLES JOIN USERS_ROLES ON ROLES.ROLE=USERS_ROLES.ROLE_NAME WHERE USErS_ROLES.USERNAME='"+sid+"' AND TIMESTAMP=to_date('"+TS+"','DD-MM-YYYY HH24:MI:SS')", {}, {outFormat: ""});
}

checkConnection.getUserNameTS = (name,TS) => {
    return queryObject("SELECT * FROM USERS WHERE USERS.USERNAME='"+name+"' AND TIMESTAMP=to_date('"+TS+"','DD-MM-YYYY HH24:MI:SS')", {}, {outFormat: ""});
}


module.exports.queryObject = queryObject;
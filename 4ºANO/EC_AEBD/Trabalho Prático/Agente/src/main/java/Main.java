import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        ConnectToRead connectToRead = new ConnectToRead();
        Class.forName("oracle.jdbc.OracleDriver");
        connectToRead.setConnection();

        ConnectToWrite connectToWrite = new ConnectToWrite();
        Class.forName("oracle.jdbc.OracleDriver");
        connectToWrite.setConnection();

        if (connectToRead.getConnection() != null) {
            System.out.println("Connected with :" + connectToRead.getUrl());
            if (connectToWrite.getConnection() != null) {
                System.out.println("Connected with :" + connectToWrite.getUrl());
                try (Statement stmt = connectToRead.getConnection().createStatement()) {
                    try (Statement stmtwriter = connectToWrite.getConnection().createStatement()) {
                        String query;
                        boolean first_time = true;
                        int id = 0;

                        while (true) {
                            Date date = new Date();
                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                            String timestamp = formatter.format(date);

                            /* INFO */
                            if (first_time) {
                                ResultSet resultSet = stmtwriter.executeQuery("SELECT MAX(ID) FROM DB_MONITOR");
                                while (resultSet.next()) {
                                    id = resultSet.getInt("MAX(ID)");
                                }
                                id = id + 1;
                                query = "INSERT INTO DB_MONITOR VALUES ( " + id + ", '" + connectToWrite.getUrl() + "','" + connectToWrite.getUser() + "')";
                                stmtwriter.executeQuery(query);
                                first_time = false;
                            } else {
                                ResultSet resultSet = stmtwriter.executeQuery("SELECT MAX(ID) FROM DB_MONITOR");
                                while (resultSet.next()) {
                                    id = resultSet.getInt("MAX(ID)");
                                }
                            }

                            ResultSet resultSet = stmtwriter.executeQuery("SELECT * FROM ROLES");
                            if (!resultSet.next()) {
                                /* ROLES */
                                query = "SELECT DISTINCT ROLE_ID , ROLE , AUTHENTICATION_TYPE FROM DBA_ROLES";
                                ResultSet resultSetRoles = stmt.executeQuery(query);
                                while (resultSetRoles.next()) {
                                    int role_id = resultSetRoles.getInt("ROLE_ID");
                                    String role = resultSetRoles.getString("ROLE");
                                    String authentication_type = resultSetRoles.getString("AUTHENTICATION_TYPE");

                                    query = "INSERT INTO ROLES VALUES ( " + role_id + ",'" + role + "','" + authentication_type + "')";
                                    stmtwriter.executeQuery(query);
                                }
                            }

                            /* TABLESPACES */
                            query = "SELECT DISTINCT T.TABLESPACE_NAME, ROUND((M.TABLESPACE_SIZE*8/1024),2) AS TABLESPACE_SIZE, ROUND((M.TABLESPACE_SIZE-M.USED_SPACE)*8/1024,2) AS FREE, ROUND(M.USED_SPACE*8/1024,2) AS USED, ROUND(100-M.USED_PERCENT,2) AS PERC_FREE, ROUND(M.USED_PERCENT,2) AS PERC_USED, T.STATUS, T.ALLOCATION_TYPE, T.CONTENTS, T.SEGMENT_SPACE_MANAGEMENT \n" +
                                    "    FROM DBA_TABLESPACES T LEFT JOIN DBA_TABLESPACE_USAGE_METRICS M ON T.TABLESPACE_NAME = M.TABLESPACE_NAME ORDER BY TABLESPACE_NAME ";
                            ResultSet resultSetTablespaces = stmt.executeQuery(query);
                            while (resultSetTablespaces.next()) {
                                String tablespace_name = resultSetTablespaces.getString("TABLESPACE_NAME");
                                double size = resultSetTablespaces.getDouble("TABLESPACE_SIZE");
                                double free = resultSetTablespaces.getDouble("FREE");
                                double used = resultSetTablespaces.getDouble("USED");
                                double perc_free = resultSetTablespaces.getDouble("PERC_FREE");
                                double perc_used = resultSetTablespaces.getDouble("PERC_USED");
                                String status = resultSetTablespaces.getString("STATUS");
                                String allocation_type = resultSetTablespaces.getString("ALLOCATION_TYPE");
                                String contents = resultSetTablespaces.getString("CONTENTS");
                                String segment_space_management = resultSetTablespaces.getString("SEGMENT_SPACE_MANAGEMENT");

                                query = "INSERT INTO TABLESPACES VALUES ('" + tablespace_name + "'," + size + "," + free + "," + used + "," + perc_free + "," + perc_used + ",'" + status + "','" + allocation_type + "','" + contents + "','" + segment_space_management + "',TO_DATE('" + timestamp + "','dd-mm-yyyy hh24:mi:ss')," + id + ")";
                                stmtwriter.executeQuery(query);

                            }


                            /* DATAFILES */
                            query = "SELECT FILE_NAME , FILE_ID , TABLESPACE_NAME , BYTES , BLOCKS , STATUS , AUTOEXTENSIBLE , MAXBYTES , MAXBLOCKS , ONLINE_STATUS FROM DBA_DATA_FILES";
                            ResultSet resultSetDatafiles = stmt.executeQuery(query);
                            while (resultSetDatafiles.next()) {
                                String file_name = resultSetDatafiles.getString("FILE_NAME");
                                int file_id = resultSetDatafiles.getInt("FILE_ID");
                                String tablespace_name = resultSetDatafiles.getString("TABLESPACE_NAME");
                                int bytes = resultSetDatafiles.getInt("BYTES");
                                int blocks = resultSetDatafiles.getInt("BLOCKS");
                                String status = resultSetDatafiles.getString("STATUS");
                                String autoextensible = resultSetDatafiles.getString("AUTOEXTENSIBLE");
                                long maxbytes = resultSetDatafiles.getLong("MAXBYTES");
                                int maxblocks = resultSetDatafiles.getInt("MAXBLOCKS");
                                String online_status = resultSetDatafiles.getString("ONLINE_STATUS");

                                query = "INSERT INTO DATAFILES VALUES ('" + file_name + "'," + file_id + ",'" + tablespace_name + "'," + bytes + "," + blocks + ",'" + status + "','" + autoextensible + "'," + maxbytes + "," + maxblocks + ",'" + online_status + "',TO_DATE('" + timestamp + "','dd-mm-yyyy hh24:mi:ss'))";
                                stmtwriter.executeQuery(query);
                            }

                            /* USERS */
                            query = "SELECT USERNAME , ACCOUNT_STATUS , EXPIRY_DATE , DEFAULT_TABLESPACE , TEMPORARY_TABLESPACE , PROFILE , CREATED , COMMON FROM DBA_USERS";
                            ResultSet resultSetUsers = stmt.executeQuery(query);
                            while (resultSetUsers.next()) {
                                String username = resultSetUsers.getString("USERNAME");
                                String account_status = resultSetUsers.getString("ACCOUNT_STATUS");
                                Date expiry_date = resultSetUsers.getDate("EXPIRY_DATE");
                                String default_tablespace = resultSetUsers.getString("DEFAULT_TABLESPACE");
                                String temporary_tablespace = resultSetUsers.getString("TEMPORARY_TABLESPACE");
                                String profile = resultSetUsers.getString("PROFILE");
                                Date created = resultSetUsers.getDate("CREATED");
                                String common = resultSetUsers.getString("COMMON");

                                query = "INSERT INTO USERS VALUES ('" + username + "','" + account_status + "','" + expiry_date + "','" + default_tablespace + "','" + temporary_tablespace + "','" + profile + "','" + created + "','" + common + "',TO_DATE('" + timestamp + "','dd-mm-yyyy hh24:mi:ss'))";
                                stmtwriter.executeQuery(query);
                            }

                            /* SESSIONS */
                            query = "SELECT SID , USERNAME , STATUS , SERVER , SCHEMANAME , OSUSER , MACHINE , PORT , TYPE , WAIT_TIME_MICRO , LOGON_TIME FROM V$SESSION WHERE USERNAME IS NOT NULL ";
                            ResultSet resultSetSessions = stmt.executeQuery(query);
                            while (resultSetSessions.next()) {
                                int sid = resultSetSessions.getInt("SID");
                                String username = resultSetSessions.getString("USERNAME");
                                String status = resultSetSessions.getString("STATUS");
                                String server = resultSetSessions.getString("SERVER");
                                String schemaname = resultSetSessions.getString("SCHEMANAME");
                                String osuser = resultSetSessions.getString("OSUSER");
                                String machine = resultSetSessions.getString("MACHINE");
                                int port = resultSetSessions.getInt("PORT");
                                String type = resultSetSessions.getString("TYPE");
                                int wait_time_micro = resultSetSessions.getInt("WAIT_TIME_MICRO");
                                Date logon_time = resultSetSessions.getDate("LOGON_TIME");

                                query = "INSERT INTO SESSIONS VALUES (" + sid + ",'" + username + "','" + status + "','" + server + "','" + schemaname + "','" + osuser + "','" + machine + "'," + port + ",'" + type + "'," + wait_time_micro + ",'" + logon_time + "',TO_DATE('" + timestamp + "','dd-mm-yyyy hh24:mi:ss'))";
                                stmtwriter.executeQuery(query);
                            }

                            /* USERS_ROLES */
                            query = "SELECT GRANTED_ROLE, GRANTEE FROM DBA_ROLE_PRIVS WHERE GRANTEE in (select USERNAME from DBA_USERS)";
                            ResultSet resultSetUsersRoles = stmt.executeQuery(query);
                            while (resultSetUsersRoles.next()) {
                                String granted_role = resultSetUsersRoles.getString("GRANTED_ROLE");
                                String grantee = resultSetUsersRoles.getString("GRANTEE");

                                query = "INSERT INTO USERS_ROLES VALUES ('" + granted_role + "','" + grantee + "',TO_DATE('" + timestamp + "','dd-mm-yyyy hh24:mi:ss'))";
                                stmtwriter.executeQuery(query);
                            }

                            /*PGA*/
                            query = "SELECT NAME, VALUE FROM V$PGASTAT WHERE NAME='total PGA inuse' OR NAME='total PGA allocated'";
                            ResultSet resultSetPGA = stmt.executeQuery(query);
                            while (resultSetPGA.next()) {
                                String name = resultSetPGA.getString("NAME");
                                int value = resultSetPGA.getInt("VALUE");

                                query = "INSERT INTO PROGRAM_GLOBAL_AREA VALUES ('" + name + "'," + value + ",TO_DATE('" + timestamp + "','dd-mm-yyyy hh24:mi:ss')," + id + ")";
                                stmtwriter.executeQuery(query);
                            }

                            /*SGA*/
                            query = "SELECT NAME, VALUE FROM V$SGA ";
                            ResultSet resultSetSGA = stmt.executeQuery(query);
                            while (resultSetSGA.next()) {
                                String name = resultSetSGA.getString("NAME");
                                int value = resultSetSGA.getInt("VALUE");

                                query = "INSERT INTO SYSTEM_GLOBAL_AREA VALUES ('" + name + "'," + value + ",TO_DATE('" + timestamp + "','dd-mm-yyyy hh24:mi:ss')," + id + ")";
                                stmtwriter.executeQuery(query);
                            }

                            /*CPU*/
                            query = "SELECT S.USERNAME, SUM(VALUE/100) as \"CPU USAGE (SECONDS)\" FROM V$SESSION S, V$SESSTAT t, V$STATNAME N \n" +
                                    "    WHERE T.STATISTIC# = N.STATISTIC# AND NAME LIKE '%CPU used by this session%' AND T.SID = S.SID \n" +
                                    "        AND S.STATUS='ACTIVE' AND S.USERNAME IS NOT NULL \n" +
                                    "    GROUP BY USERNAME, T.SID, S.SERIAL#";
                            ResultSet resultSetCPU = stmt.executeQuery(query);
                            while (resultSetCPU.next()) {
                                String username = resultSetCPU.getString("USERNAME");
                                double usage = resultSetCPU.getDouble("CPU USAGE (SECONDS)");
                                query = "INSERT INTO CPU VALUES ('" + username + "'," + usage + ",TO_DATE('" + timestamp + "','dd-mm-yyyy hh24:mi:ss')," + id + ")";
                                stmtwriter.executeQuery(query);
                            }

                            System.out.println("Going to sleep for 30 seconds ...");
                            Thread.sleep(30000);
                            System.out.println("Woke up ...");
                        }
                    } catch (SQLException | InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Connection Failed");
            }
        } else {
            System.out.println("Connection Failed");
        }
    }
}


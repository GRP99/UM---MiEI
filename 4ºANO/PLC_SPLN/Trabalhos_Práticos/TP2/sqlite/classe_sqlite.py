###########################################################################
import sqlite3
import traceback
from pprint import pprint

class Database:

    def __init__(self, name=None):
        
        self.conn = None
        self.cursor = None

        if name:
            self.open(name)

    
    def open(self,name):
        
        try:
            self.conn = sqlite3.connect(name)
            self.conn.execute("PRAGMA foreign_keys = 1")
            self.cursor = self.conn.cursor()

        except sqlite3.Error as e:
            print("Error connecting to database!")

        if self.conn:
            self.conn.commit()

    def commit(self):
        if self.conn:
            self.conn.commit()

    def close(self):
        
        if self.conn:
            self.conn.commit()
            self.cursor.close()
            self.conn.close()


    def __enter__(self):
        
        return self

    def __exit__(self,exc_type,exc_value,traceback):
        
        self.close()


    def runsql(self,query,params=None,debug=False,close=False):
        rows = None
        try:
            if "INSERT" in query:
                if params:
                    self.inssql(query,params)
                else:
                    self.inssql(query)
            elif "UPDATE" in query:
                self.cursor.execute(query)
                self.commit()
                print("\tUpdated:",query)
            elif "DELETE" in query:
                self.cursor.execute(query)
                self.commit()
                print("\tDeleted:",query)
            else:   #SELECT
                if params:
                    self.cursor.execute(query,params)
                else:
                    self.cursor.execute(query)
                rows=self.cursor.fetchall() 

        except Exception as e:
            print("\tERROR ON QUERY:",query)
            print("\t",e)
            rows=None
        if close:
            self.close()
        return rows #self.cursor.fetchall()
    #######################################################################
    def inssql(self,query,params=None):
        try:
            if params:
                print("PARAMS")
                pprint(params)
                self.cursor.execute(query,params)
            else: 
                self.cursor.execute(query)
            self.commit()
            print("\tInserted:",query)
        except Exception as e:
            print("\tERROR ON QUERY:",query)
            print(f"\t{e}")
            rows=None

    #######################################################################
class autodb(Database):
    def __init__(self, dbname , query, params=None, debug=False):
        super().__init__(dbname)
        self.query=query
        self.params=params
        self.debug=debug

    def corresql(self):
        rows=self.runsql(self.query, self.params, self.debug, close=True)
        return(rows)

#!usr/bin/python3

# Importação dos módulos
from pymongo import MongoClient
from bson.objectid import ObjectId
import pprint 
import datetime

""" 
The code bellow will connect on the default host and port.
We also can can change that to specified the host and port:
	 client = MongoClient('localhost',27017)
Or use the MongoDB URI format: 
	 client = Mongoclient('mongodb://localhost:27017')
"""
client = MongoClient()

"""
A single instance of MongoDB can support multiple independent 
databases. When working with PyMongo you acess databases using
attributes style acess on MongoClient instances:
"""
# Select the SPLN in database 
spln = client.SPLN

# Select the work collection in SPLN database .
students = spln.students
students.delete_many({})

"""
Data in MongoDB is represented(and stored) using JSON-style documents.
In PyMongo we use dictionaries to represent documents.
"""
student = {
		"number":"A76089",
		"name":"Etienne Costa",
		"course":"MIEI",
		"date":datetime.datetime.now()
	}

spln_students = [
	{
		"number":"A85954",
		"name":"Luís Ribeiro",
		"course":"MIEI",
		"date":datetime.datetime.now()
	},
	{
		"number":"A83732",
		"name":"Gonçalo Pinto",
		"course":"MIEI",
		"date":datetime.datetime.now()
	},
	{
		"number":"A20200",
		"name":"Albert Einstein",
		"course":"MEI",
		"date":datetime.datetime.now()
	}
]

# To insert a document into a collection we can use the insert_one() method:
students.insert_one(student)

# To insert multiples documents into a collection we can use the insert_many() method:
students.insert_many(spln_students)


#Pretty print of a single document with find_one .
#find_one : method returns a single document matching a query 
#		   or None if there are no matches.

print("======================== find_one ===========================")
pprint.pprint(students.find_one())
print("=============================================================\n")

print("========================  find  =============================")
for s in students.find():
	pprint.pprint(s)
print("=============================================================\n")

print("===================== find by course ========================")
for s in students.find({"course": "MEI"}):
	  pprint.pprint(s)
print("=============================================================\n")
"""
	When a document is inserted a special key, “_id”, is automatically added if the
	document doesn’t already contain an “_id” key. 
	The value of “_id” must be unique across the collection.
"""
# Verify all of the collections in our database:
collections=spln.list_collection_names()
print("============== Collections of SPLN database =================")
for c in collections:
	print(c)
print("=============================================================\n")


print("======================== Counting ===========================")
print("Number of students in SPLN : ",students.count_documents({}))
print("Number of students in SPLN from MIEI : ",students.count_documents({"course":"MIEI"}))
print("=============================================================\n")


"""
A common task in web applications is to get an ObjectId from the request URL 
and find the matching document. 
It’s necessary in this case to convert the ObjectId from a string before passing it 
to find_one:
"""
# The web framework gets post_id from the URL and passes it as a string
def get(querystring:str):
    # Convert from string to ObjectId:
	return ObjectId(querystring)

# Function to get all students in SPLN course
def get_all_students():
	return students.find()

# Function to get students by name
def get_students_by_name(name:str):
		return students.find({'name':{'$regex':name}})
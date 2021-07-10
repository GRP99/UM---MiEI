from flask import Flask, request,jsonify
import mongo



## Flask  Requiring something from url .
app = Flask(__name__)


@app.route('/students',methods=['GET'])

def list_students():
	name = (request.args['name'])
	result=""
	if name=="*":
		students=list(mongo.get_all_students())
	else :
		students=list(mongo.get_students_by_name(name))

	for s in students :
		result+="""
		 	  <tr>
			    <td>{id}</td>
			    <td>{name}</td>
			    <td>{course}</td>
				<td>{date}</td>
			  </tr>
				""".format(id=s["number"], name=s["name"],course=s["course"],date=s["date"])	

	return """	
			
			<!DOCTYPE html>
<html>
<title>W3.CSS</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<style>
    table,th,td {
        border: 1px solid black;
        border-collapse: collapse;
    }
    h1,h5{
        text-align: center;
    }
    th,td {
        padding: 15px;
        text-align: left;
    }
</style>

<body>
    <div class="w3-container">
        <div class="w3-card-4" style="width:100%;">
            <header class="w3-container w3-blue">
                <h1>SPLN Students</h1>
            </header>

            <div class="w3-container">
                <table style="width: 100%;">
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Course</th>
                        <th>Date</th>
                    </tr>
					"""+result+"""
					</table>
					
			<footer class="w3-container w3-blue">
                		<h5>PyMongo Tutorial</h5>
     	    </footer>
        	</div>
    	</div>
	</body>
</html>
		"""
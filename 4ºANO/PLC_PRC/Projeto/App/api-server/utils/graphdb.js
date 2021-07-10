var axios = require("axios");

var prefixes = `
    PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>
	PREFIX xml: <http://www.w3.org/XML/1998/namespace>
	PREFIX wgs: <http://www.w3.org/2003/01/geo/wgs84_pos#>
	PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
	PREFIX owl: <http://www.w3.org/2002/07/owl#>
	PREFIX gn: <http://www.geonames.org/ontology#>
    PREFIX : <http://www.di.uminho.pt/prc2021/projeto#>
`;

exports.execQuery = async function (query) {
	var getLink = "http://localhost:7200/repositories/projectPRC?query=";
	var encoded = encodeURIComponent(prefixes + query);
	var result = await axios.get(getLink + encoded);
	return result.data;
};

exports.execTransaction = async function (query) {
	var postLink = "http://localhost:7200/repositories/projectPRC/statements";
	var encoded = encodeURIComponent(prefixes + query);
	var response;
	try {
		response = await axios.post(postLink, `update=${encoded}`);
		return response.data;
	} catch (error) {
		throw error;
	}
};

exports.execSize = async function () {
	var postLink = "http://localhost:7200/repositories/projectPRC/size";
	var response;
	try {
		response = await axios.get(postLink);
		return response.data;
	} catch (error) {
		throw error;
	}
};

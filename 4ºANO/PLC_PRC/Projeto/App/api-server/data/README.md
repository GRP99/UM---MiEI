### Comando para importar para o Mongo

mongoimport --db cocktailsDB --collection bars --drop --file bars.json --jsonArray
mongoimport --db cocktailsDB --collection bartenders --drop --file bartenders.json --jsonArray
mongoimport --db cocktailsDB --collection cocktails --drop --file cocktails.json --jsonArray
mongoimport --db cocktailsDB --collection locations --drop --file locations.json --jsonArray
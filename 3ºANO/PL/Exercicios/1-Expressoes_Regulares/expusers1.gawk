	BEGIN 				{ FS=":"; conta=0 }
	NR==1 				{ print "A processar o ficheiro: " FILENAME }
	NR>=1 && NR<=10 	{ print $1 }
	END					{ print conta " - " NR }
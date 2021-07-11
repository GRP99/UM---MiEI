	BEGIN				{ FS=":"; conta=0 }
	/rita/				{ conta++; print $1 " -> " $6 }
	/prh|jcr/			{ conta++; print $1 " -> " $6 }
	/uucp/,/rpm/		{ conta++; print }
	/x.*sbin/ && $3>40 	{ conta++; print "Em sbin: " $0 }
	$1 ~ "nuno"			{ conta++; print $1 " =  " $NF }
	END					{ print conta " - " NR " =3.1 " conta/NR }
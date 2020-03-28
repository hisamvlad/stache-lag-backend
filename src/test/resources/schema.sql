CREATE TABLE teams (
	name VARCHAR(100),
	marker INT,
	serial INT
	PRIMARY KEY (serial)
);

CREATE TABLE positions (
	id INT,
	serial INT,
	gpsAt TIMESTAMP,
	txtAt TIMESTAMP,
	latitude DOUBLE,
	logitude DOUBLE,
	altitude INT,
	alert BOOLEAN,
	type VARCHAR(10)
	dtfKm DOUBLE,
	dtfNm DOUBLE,
	sogKmph DOUBLE,
	sogKnots DOUBLE,
	battery INT,
	cog INT,
	PRIMARY KEY(id), FOREIGN KEY (serial) REFERENCES teams(serial)
);

CREATE TABLE sightings(
	serial INT,
	serial_spotted INT,
	spottedAt TIMESTAMP,
	FOREIGN KEY (serial) REFERENCES teams(serial),
	FEREIGN KEY (serial_spotted) REEFRENCES teams(serial)
); 
CREATE TABLE address (
	address VARCHAR(30) NOT NULL PRIMARY KEY,
	city VARCHAR(10) NOT NULL,
	state_province VARCHAR(10) NOT NULL,
	postal_code VARCHAR(5) NOT NULL,
	country VARCHAR(15) NOT NULL
) Engine=InnoDB;

CREATE TABLE station (
	station_name VARCHAR(30) NOT NULL PRIMARY KEY,
	mac_address VARCHAR(19) NOT NULL,
	org_name VARCHAR (20) NOT NULL,
	latitude FLOAT NOT NULL,
	longitude FLOAT NOT NULL,
	address_name VARCHAR(30) NOT NULL,
	FOREIGN KEY (address_name) REFERENCES address(address)
) ENGINE=InnoDB;

CREATE TABLE port (
	port_type VARCHAR(7) NOT NULL,
	port_number TINYINT(1) UNSIGNED NOT NULL,
	plug_type VARCHAR(10) NOT NULL,
	station_name VARCHAR(30) NOT NULL,
	PRIMARY KEY (port_number, station_name),
	FOREIGN KEY (station_name) REFERENCES station(station_name),
	INDEX (station_name)
) ENGINE=InnoDB;

CREATE TABLE event (
	plug_in_event_id BIGINT UNSIGNED NOT NULL,
	start_date DATETIME NOT NULL,
	start_time_zone VARCHAR(3) NOT NULL,
	end_date DATETIME NOT NULL,
	end_time_zone VARCHAR(3) NOT NULL,
	transaction_date DATETIME NOT NULL,
	total_duration TIME NOT NULL,
	charging_time TIME NOT NULL,
	energy FLOAT NOT NULL,
	ghg_savings FLOAT NOT NULL,
	gasoline_savings FLOAT NOT NULL,
	currency VARCHAR(3),
	fee TINYINT(1),
	ended_by VARCHAR(30),
	user_id INT,
	driver_postal_code VARCHAR(5),
	station_name VARCHAR(30) NOT NULL,
	port_number TINYINT(1) UNSIGNED NOT NULL,
	PRIMARY KEY (plug_in_event_id, station_name),
	FOREIGN KEY (port_number, station_name) REFERENCES port(port_number, station_name),
	INDEX (station_name),
	INDEX (user_id)
) ENGINE=InnoDB;

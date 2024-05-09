DROP DATABASE if exists my_next_trip_test;
CREATE DATABASE my_next_trip_test;
USE my_next_trip_test;

-- Creating Schema

CREATE TABLE region (
    region_id INT AUTO_INCREMENT,
    region_name VARCHAR(20) NOT NULL,
    PRIMARY KEY (region_id)
);

CREATE TABLE cost_of_living (
    cost_of_living_id INT AUTO_INCREMENT,
    cost_of_living_name VARCHAR(20) NOT NULL,
    PRIMARY KEY (cost_of_living_id)
);

CREATE TABLE season (
    season_id INT AUTO_INCREMENT,
    season_name VARCHAR(20) NOT NULL,
    PRIMARY KEY (season_id)
);

CREATE TABLE tag (
    tag_id INT AUTO_INCREMENT,
    tag_name VARCHAR(20) NOT NULL,
    PRIMARY KEY (tag_id)
);

CREATE TABLE country (
    country_id INT AUTO_INCREMENT,
    country_name VARCHAR(40) NOT NULL,
	country_image VARCHAR(255),
    country_description VARCHAR(300),
    cost_of_living_id INT,
    region_id INT,
    PRIMARY KEY (country_id),
    FOREIGN KEY (cost_of_living_id) REFERENCES cost_of_living(cost_of_living_id),
    FOREIGN KEY (region_id) REFERENCES region(region_id)
);

CREATE TABLE attraction (
    attraction_id INT AUTO_INCREMENT,
    attraction_name VARCHAR(50) NOT NULL,
    attraction_description VARCHAR(300),
    attraction_image VARCHAR(255),
    country_id INT,
    PRIMARY KEY (attraction_id),
    FOREIGN KEY (country_id) REFERENCES country(country_id)
);

CREATE TABLE city (
    city_id INT AUTO_INCREMENT,
    city_name VARCHAR(50) NOT NULL,
    city_description VARCHAR(300),
    city_image VARCHAR(255),
    country_id INT,
    PRIMARY KEY (city_id),
    FOREIGN KEY (country_id) REFERENCES country(country_id)
);

CREATE TABLE season_to_avoid (
    country_id INT,
    season_id INT,
    PRIMARY KEY (country_id, season_id),
    FOREIGN KEY (country_id) REFERENCES country(country_id),
    FOREIGN KEY (season_id) REFERENCES season(season_id)
);

delimiter //
create procedure set_known_good_state()
begin

	delete from season_to_avoid;
    alter table season_to_avoid auto_increment = 1;
	delete from city;
	alter table city auto_increment = 1;
	delete from attraction;
	alter table attraction auto_increment = 1;
	delete from country;
    alter table country auto_increment = 1;
	delete from cost_of_living;
    alter table cost_of_living auto_increment = 1;
	delete from region;
    alter table region auto_increment = 1;

INSERT INTO region (region_name) VALUES
('Africa'),
('Asia'),
('Europe'),
('North America'),
('Central America'),
('South America'),
('Oceania'),
('Middle East');


INSERT INTO cost_of_living (cost_of_living_name) VALUES
('Low'),
('Moderate'),
('High');

INSERT INTO country (country_name, cost_of_living_id, region_id, country_image, country_description) VALUES
('France', 3, 3, 'https://my-next-trip.s3.us-west-1.amazonaws.com/France.webp', 'France is renowned for its romantic ambiance, exquisite cuisine, and iconic landmarks such as the Eiffel Tower and Louvre Museum, making it a top destination for art, culture, and gastronomy enthusiasts worldwide.'),
('Spain', 2, 3, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Spain.webp', 'With vibrant cities like Barcelona and Madrid, stunning beaches along the Costa del Sol, and rich cultural heritage reflected in its flamenco dancing and historic architecture, Spain offers visitors a diverse and dynamic experience blending tradition with modernity.'),
('United States', 3, 4, 'https://my-next-trip.s3.us-west-1.amazonaws.com/UnitedStates.webp', 'From the bustling streets of New York City to the natural wonders of the Grand Canyon and Yellowstone National Park, the United States boasts a diverse landscape, vibrant cities, and iconic landmarks, offering endless opportunities for exploration and adventure.');

INSERT INTO attraction (attraction_name, attraction_description, attraction_image, country_id) VALUES
('Attraction1', 'Attraction1', 'Attraction1', 1),
('Attraction2', 'Attraction2', 'Attraction2', 1),
('Attraction3', 'Attraction3', 'Attraction3', 2);

INSERT INTO city (city_name, city_description, city_image, country_id) VALUES
('City1', 'City1', 'City1', 1),
('City2', 'City2', 'City2', 1),
('City3', 'City3', 'City3', 2);

end //
delimiter ;




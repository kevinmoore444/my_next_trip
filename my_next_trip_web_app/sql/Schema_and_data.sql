
-- Creating Database

CREATE DATABASE my_next_trip;
USE my_next_trip;

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
    country_description VARCHAR(600),
    cost_of_living_id INT,
    region_id INT,
    PRIMARY KEY (country_id),
    FOREIGN KEY (cost_of_living_id) REFERENCES cost_of_living(cost_of_living_id),
    FOREIGN KEY (region_id) REFERENCES region(region_id)
);


CREATE TABLE attraction (
    attraction_id INT AUTO_INCREMENT,
    attraction_name VARCHAR(50) NOT NULL,
    attraction_description VARCHAR(600),
    attraction_image VARCHAR(255),
    country_id INT,
    PRIMARY KEY (attraction_id),
    FOREIGN KEY (country_id) REFERENCES country(country_id)
);



CREATE TABLE city (
    city_id INT AUTO_INCREMENT,
    city_name VARCHAR(50) NOT NULL,
    city_description VARCHAR(600),
    city_image VARCHAR(255),
    country_id INT,
    PRIMARY KEY (city_id),
    FOREIGN KEY (country_id) REFERENCES country(country_id)
);


CREATE TABLE country_has_tag (
    country_id INT,
    tag_id INT,
    PRIMARY KEY (country_id, tag_id),
    FOREIGN KEY (country_id) REFERENCES country(country_id),
    FOREIGN KEY (tag_id) REFERENCES tag(tag_id)
);

CREATE TABLE season_to_avoid (
    country_id INT,
    season_id INT,
    PRIMARY KEY (country_id, season_id),
    FOREIGN KEY (country_id) REFERENCES country(country_id),
    FOREIGN KEY (season_id) REFERENCES season(season_id)
);




-- Seeding Data

INSERT INTO season (season_name) VALUES
('Winter'),
('Spring'),
('Summer'),
('Fall');

INSERT INTO region (region_name) VALUES
('Africa'),
('Asia'),
('Europe'),
('North America'),
('Central America'),
('South America'),
('Oceania'),
('Middle East');

INSERT INTO tag (tag_name) VALUES
('Historic'),
('Religious'),
('Cultural'),
('Museum'),
('Iconic'),
('Nature'),
('Adventure');

INSERT INTO cost_of_living (cost_of_living_name) VALUES
('Low'),
('Moderate'),
('High');

INSERT INTO country (country_name, cost_of_living_id, region_id, country_image) VALUES
('France', 3, 3, 'https://my-next-trip.s3.us-west-1.amazonaws.com/France.webp'),
('Spain', 2, 3, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Spain.webp'),
('United States', 3, 4, 'https://my-next-trip.s3.us-west-1.amazonaws.com/UnitedStates.webp'),
('China', 2, 2, 'https://my-next-trip.s3.us-west-1.amazonaws.com/China.webp'),
('Italy', 3, 3, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Italy.webp'),
('Turkey', 1, 2, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Turkey.webp'),
('Mexico', 1, 5, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Mexico.webp'),
('Thailand', 1, 2, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Thailand.webp'),
('Germany', 2, 3, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Germany.webp'),
('United Kingdom', 3, 3, 'https://my-next-trip.s3.us-west-1.amazonaws.com/UnitedKingdom.webp'),
('Japan', 3, 2, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Japan.webp'),
('Austria', 3, 3, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Austria.webp'),
('Greece', 2, 3, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Greece.webp'),
('Hong Kong', 3, 2, 'https://my-next-trip.s3.us-west-1.amazonaws.com/HongKong.webp'),
('Portugal', 2, 3, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Portugal.webp'),
('Canada', 3, 4, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Canada.webp'),
('Poland', 1, 3, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Poland.webp'),
('Netherlands', 3, 3, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Netherlands.webp'),
('South Korea', 3, 2, 'https://my-next-trip.s3.us-west-1.amazonaws.com/SouthKorea.webp'),
('Switzerland', 3, 3, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Switzerland.webp'),
('Croatia', 2, 3, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Croatia.webp'),
('India', 1, 2, 'https://my-next-trip.s3.us-west-1.amazonaws.com/India.webp'),
('Singapore', 3, 2, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Singapore.webp'),
('Australia', 3, 7, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Australia.webp'),
('Egypt', 1, 8, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Egypt.webp'),
('Morocco', 1, 1, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Morocco.webp'),
('Czech Republic', 2, 3, 'https://my-next-trip.s3.us-west-1.amazonaws.com/CzechRepublic.webp'),
('Indonesia', 1, 7, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Indonesia.webp'),
('Ireland', 3, 3, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Ireland.webp'),
('South Africa', 2, 1, 'https://my-next-trip.s3.us-west-1.amazonaws.com/SouthAfrica.webp'),
('Philippines', 1, 7, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Phillippines.webp'),
('Belgium', 3, 3, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Belgium.webp'),
('Vietnam', 1, 2, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Vietnam.webp'),
('Argentina', 1, 6, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Argentina.webp'),
('Sweden', 3, 3, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Sweden.webp'),
('Norway', 3, 3, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Norway.webp'),
('Hungary', 2, 3, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Hungary.webp'),
('Brazil', 1, 6, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Brazil.webp'),
('Israel', 3, 8, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Israel.webp'),
('Denmark', 3, 3, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Denmark.webp'),
('New Zealand', 3, 7, 'https://my-next-trip.s3.us-west-1.amazonaws.com/NewZealand.webp'),
('Colombia', 2, 6, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Colombia.webp'),
('Taiwan', 2, 2, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Taiwan.webp'),
('Peru', 2, 6, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Peru.webp'),
('Nepal', 1, 2, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Nepal.webp'),
('Jordan', 2, 8, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Jordan.webp'),
('Iceland', 3, 3, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Iceland.webp'),
('Panama', 2, 5, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Panama.webp'),
('Chile', 2, 6, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Chile.webp'),
('Cuba', 1, 4, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Cuba.webp'),
('Ethiopia', 1, 1, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Ethiopia.webp'),
('Slovenia', 2, 3, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Slovenia.webp'),
('Guatemala', 1, 5, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Guatemala.webp'),
('Costa Rica', 2, 5, 'https://my-next-trip.s3.us-west-1.amazonaws.com/CostaRica.webp'),
('Lebanon', 2, 8, 'https://my-next-trip.s3.us-west-1.amazonaws.com/Lebanon.webp');

UPDATE country
SET country_description = CASE
    WHEN country_name = 'France' THEN 'France is renowned for its romantic ambiance, exquisite cuisine, and iconic landmarks such as the Eiffel Tower and Louvre Museum, making it a top destination for art, culture, and gastronomy enthusiasts worldwide.'
    WHEN country_name = 'Spain' THEN 'With vibrant cities like Barcelona and Madrid, stunning beaches along the Costa del Sol, and rich cultural heritage reflected in its flamenco dancing and historic architecture, Spain offers visitors a diverse and dynamic experience blending tradition with modernity.'
    WHEN country_name = 'United States' THEN 'From the bustling streets of New York City to the natural wonders of the Grand Canyon and Yellowstone National Park, the United States boasts a diverse landscape, vibrant cities, and iconic landmarks, offering endless opportunities for exploration and adventure.'
    WHEN country_name = 'China' THEN 'With its ancient history, majestic landmarks such as the Great Wall and Forbidden City, and modern metropolises like Shanghai and Beijing, China offers travelers a captivating blend of tradition and innovation, making it a fascinating destination for cultural immersion and discovery.'
    WHEN country_name = 'Italy' THEN 'Italy''s timeless charm, world-renowned cuisine, and rich cultural heritage, embodied by cities like Rome, Florence, and Venice, draw visitors from around the globe to experience its iconic landmarks, picturesque landscapes, and warm Mediterranean hospitality.'
    WHEN country_name = 'Turkey' THEN 'From the historic wonders of Istanbul''s Hagia Sophia and Ephesus to the stunning landscapes of Cappadocia and Pamukkale, Turkey offers a captivating blend of East and West, ancient and modern, providing travelers with a unique and enriching journey through time and culture.'
    WHEN country_name = 'Mexico' THEN 'With its vibrant colors, rich traditions, and diverse landscapes ranging from pristine beaches to ancient ruins like Chichen Itza and Teotihuacan, Mexico invites visitors to immerse themselves in its lively culture, flavorful cuisine, and warm hospitality.'
    WHEN country_name = 'Thailand' THEN 'Thailand''s exotic allure, from the bustling streets of Bangkok to the tranquil beaches of Phuket and the ancient temples of Ayutthaya, captivates travelers with its vibrant street life, flavorful cuisine, and rich cultural heritage, offering a sensory feast for the adventurous soul.'
    WHEN country_name = 'Germany' THEN 'From the historic charm of medieval towns like Rothenburg ob der Tauber to the vibrant energy of Berlin and the scenic beauty of the Bavarian Alps, Germany beckons visitors with its diverse landscapes, rich history, and renowned festivals like Oktoberfest, promising a memorable journey through the heart of Europe.'
    WHEN country_name = 'United Kingdom' THEN 'With its iconic landmarks like Big Ben and Buckingham Palace, cultural treasures such as the British Museum and Shakespeare''s Globe Theatre, and picturesque countryside dotted with historic castles and charming villages, the United Kingdom offers travelers a quintessential blend of history, tradition, and modernity.'
    WHEN country_name = 'Japan' THEN 'Japan''s unique blend of ancient traditions and cutting-edge technology, showcased in cities like Tokyo and Kyoto, along with its breathtaking natural beauty and exquisite cuisine, make it a must-visit destination for travelers seeking a truly immersive cultural experience.'
    WHEN country_name = 'Austria' THEN 'From the imperial grandeur of Vienna to the stunning Alpine landscapes of Tyrol and Salzburg''s musical heritage, Austria enchants visitors with its rich history, Baroque architecture, and world-class skiing, offering a perfect blend of cultural exploration and outdoor adventure.'
    WHEN country_name = 'Greece' THEN 'Greece''s timeless allure, with its ancient ruins such as the Acropolis and Delphi, idyllic islands like Santorini and Mykonos, and delicious Mediterranean cuisine, beckons travelers to immerse themselves in its rich history, mythological wonders, and sun-drenched landscapes.'
    WHEN country_name = 'Hong Kong' THEN 'With its dazzling skyline, bustling street markets, and vibrant cultural scene, Hong Kong is a dynamic fusion of East and West, offering visitors a sensory overload of sights, sounds, and flavors in one of the world''s most exciting urban playgrounds.'
	WHEN country_name = 'Portugal' THEN 'Portugal''s charming cities like Lisbon and Porto, golden beaches of the Algarve, and historic landmarks such as the Jerónimos Monastery and Belém Tower, invite travelers to discover its rich maritime history, delectable cuisine, and laid-back coastal vibes.'
	WHEN country_name = 'Canada' THEN 'From the stunning natural beauty of Banff National Park and Niagara Falls to the multicultural metropolises of Toronto and Vancouver, Canada offers visitors an abundance of outdoor adventures, cultural experiences, and warm hospitality across its vast and diverse landscape.'
	WHEN country_name = 'Poland' THEN 'With its rich history, medieval architecture, and cultural gems like Kraków''s Old Town and Wawel Castle, Poland captivates visitors with its vibrant cities, scenic landscapes, and enduring spirit of resilience and renewal.'
	WHEN country_name = 'Netherlands' THEN 'The Netherlands charms travelers with its picturesque canals, iconic windmills, and vibrant tulip fields, while cities like Amsterdam and Utrecht offer a lively cultural scene, historic charm, and world-class museums, making it a delightful destination for exploration and relaxation.'
	WHEN country_name = 'South Korea' THEN 'South Korea''s blend of ancient traditions and modern innovation, from the historic palaces of Seoul to the stunning natural beauty of Jeju Island, along with its dynamic pop culture and delectable cuisine, offers visitors a captivating journey through past and present.'
	WHEN country_name = 'Switzerland' THEN 'Switzerland''s breathtaking Alpine scenery, charming villages like Zermatt and Interlaken, and cosmopolitan cities such as Zurich and Geneva, beckon travelers with outdoor adventures, cultural experiences, and world-class hospitality in the heart of Europe.'
	WHEN country_name = 'Croatia' THEN 'Croatia''s stunning Adriatic coastline, medieval walled cities like Dubrovnik and Split, and picturesque islands such as Hvar and Korčula, offer visitors a Mediterranean paradise steeped in history, natural beauty, and warm hospitality.'
	WHEN country_name = 'India' THEN 'India''s kaleidoscope of colors, cultures, and landscapes, from the iconic Taj Mahal and bustling streets of Delhi to the serene backwaters of Kerala and vibrant markets of Jaipur, invites travelers on a sensory journey through one of the world''s most diverse and enchanting destinations.'
	WHEN country_name = 'Singapore' THEN 'Singapore''s modern skyline, lush gardens, and multicultural heritage, showcased in neighborhoods like Chinatown and Little India, along with its culinary delights and world-class attractions such as Gardens by the Bay, make it a dynamic and captivating city-state.'
	WHEN country_name = 'Australia' THEN 'Australia''s vast Outback, pristine beaches of the Great Barrier Reef and Bondi, and cosmopolitan cities like Sydney and Melbourne, offer travelers a diverse range of experiences, from adventure and wildlife encounters to cultural immersion and relaxation.'
	WHEN country_name = 'Egypt' THEN 'Egypt''s ancient wonders, from the pyramids of Giza and temples of Luxor to the tranquil Nile River and vibrant bazaars of Cairo, captivate visitors with their timeless allure, rich history, and mystical charm.'
	WHEN country_name = 'Morocco' THEN 'Morocco''s exotic charm, with its bustling souks, labyrinthine medinas, and majestic desert landscapes, offers travelers a sensory journey steeped in history, culture, and the warm hospitality of its people.'
	WHEN country_name = 'Czech Republic' THEN 'From the fairy-tale charm of Prague''s Old Town to the medieval castles of Český Krumlov and the refreshing taste of Czech beer, the Czech Republic invites visitors to explore its rich heritage, picturesque landscapes, and vibrant arts scene.'
	WHEN country_name = 'Indonesia' THEN 'Indonesia''s breathtaking natural beauty, from the idyllic beaches of Bali to the lush rainforests of Borneo and the ancient temples of Java, captivates travelers with its diverse landscapes, vibrant culture, and warm hospitality.'
	WHEN country_name = 'Ireland' THEN 'Ireland''s rugged coastlines, verdant landscapes, and timeless charm, epitomized by the Cliffs of Moher, Ring of Kerry, and lively pubs of Dublin, beckon visitors to experience its rich history, folklore, and legendary craic.'
	WHEN country_name = 'South Africa' THEN 'With its stunning safari parks, vibrant cities like Cape Town and Johannesburg, and dramatic landscapes ranging from Table Mountain to the Kruger National Park, South Africa offers travelers a diverse and unforgettable adventure amidst its rich cultural tapestry.'
	WHEN country_name = 'Philippines' THEN 'The Philippines'' pristine beaches, vibrant coral reefs, and stunning natural wonders like the Banaue Rice Terraces and Chocolate Hills invite visitors to experience its tropical paradise, warm hospitality, and vibrant local culture.'
	WHEN country_name = 'Belgium' THEN 'Belgium''s charming medieval towns, world-class chocolates, and iconic landmarks such as the Grand Place in Brussels and medieval city of Bruges enchant visitors with its rich history, culinary delights, and artistic heritage.'
	WHEN country_name = 'Vietnam' THEN 'Vietnam''s captivating blend of ancient traditions, breathtaking landscapes like Ha Long Bay, and bustling cities such as Hanoi and Ho Chi Minh City, offers travelers a journey through history, culture, and culinary delights.'
	WHEN country_name = 'Argentina' THEN 'Argentina''s passionate tango, breathtaking landscapes from the Andes to Patagonia, and vibrant capital city of Buenos Aires, invite travelers to immerse themselves in its rich culture, natural beauty, and legendary hospitality.'
	WHEN country_name = 'Sweden' THEN 'Sweden''s picturesque landscapes, innovative design, and cultural treasures such as Stockholm''s Gamla Stan and the Northern Lights in Lapland, invite travelers to explore its modern cities, pristine nature, and progressive spirit.'
	WHEN country_name = 'Norway' THEN 'Norway''s majestic fjords, dramatic landscapes, and charming cities like Oslo and Bergen, offer travelers a scenic paradise of outdoor adventures, cultural experiences, and the magic of the midnight sun or Northern Lights.'
	WHEN country_name = 'Hungary' THEN 'Hungary''s rich history, architectural splendor, and thermal spas such as the iconic Széchenyi Thermal Bath in Budapest, invite visitors to indulge in its vibrant culture, culinary delights, and romantic Danube River views.'
	WHEN country_name = 'Brazil' THEN 'Brazil''s diverse landscapes, from the Amazon Rainforest to the iconic beaches of Rio de Janeiro and the vibrant rhythms of Carnaval, offer travelers an exhilarating fusion of nature, culture, and adventure.'
	WHEN country_name = 'Israel' THEN 'Israel''s ancient sites, religious landmarks such as Jerusalem''s Western Wall and the serene shores of the Dead Sea, beckon visitors to explore its rich history, diverse culture, and spiritual significance.'
	WHEN country_name = 'Denmark' THEN 'Denmark''s fairy-tale castles, picturesque countryside, and vibrant cities like Copenhagen, with its colorful Nyhavn district and iconic Little Mermaid statue, invite travelers to experience its blend of modernity, history, and hygge.'
	WHEN country_name = 'New Zealand' THEN 'New Zealand''s breathtaking landscapes, from the snow-capped peaks of the Southern Alps to the pristine beaches of the Bay of Islands, offer travelers an adventure-filled playground of outdoor activities, Maori culture, and awe-inspiring scenery.'
	WHEN country_name = 'Colombia' THEN 'Colombia''s vibrant cities, lush coffee plantations, and diverse landscapes ranging from the Caribbean coast to the Amazon rainforest, invite travelers to discover its rich cultural heritage, friendly locals, and adventurous spirit.'
	WHEN country_name = 'Taiwan' THEN 'Taiwan''s bustling night markets, scenic landscapes like Taroko Gorge, and modern metropolis of Taipei with its iconic Taipei 101 skyscraper, invite travelers to explore its vibrant street food, rich history, and natural beauty.'
	WHEN country_name = 'Peru' THEN 'Peru''s ancient Inca ruins of Machu Picchu, vibrant capital city of Lima, and breathtaking landscapes from the Andes to the Amazon, offer travelers a journey through history, culture, and natural wonders.'
	WHEN country_name = 'Nepal' THEN 'Nepal''s majestic Himalayan peaks, spiritual landmarks such as Boudhanath Stupa and Swayambhunath Temple in Kathmandu, and rich cultural heritage, invite travelers to embark on a spiritual and adventurous journey amidst its stunning landscapes.'
	WHEN country_name = 'Jordan' THEN 'Jordan''s ancient city of Petra, dramatic desert landscapes of Wadi Rum, and the healing waters of the Dead Sea, offer travelers a glimpse into its rich history, Bedouin culture, and timeless beauty.'
	WHEN country_name = 'Iceland' THEN 'Iceland''s otherworldly landscapes of glaciers, waterfalls, and volcanic terrain, along with the chance to witness the Northern Lights, invite travelers to experience its raw natural beauty and adventurous spirit.'
	WHEN country_name = 'Panama' THEN 'Panama''s cosmopolitan capital of Panama City, lush rainforests of the Darien Gap, and engineering marvel of the Panama Canal, offer travelers a diverse blend of culture, nature, and history in Central America.'
	WHEN country_name = 'Chile' THEN 'Chile''s diverse landscapes, from the otherworldly Atacama Desert to the rugged beauty of Patagonia and the stunning fjords of the Chilean Lake District, offer travelers a journey of discovery amidst nature''s grandeur and cultural richness.'
	WHEN country_name = 'Cuba' THEN 'Cuba''s colorful streets, vintage cars, and lively music scene captivate visitors, while its historic architecture, pristine beaches, and vibrant culture provide a glimpse into a bygone era, making it a timeless destination full of charm and allure.'
	WHEN country_name = 'Ethiopia' THEN 'Ethiopia''s ancient history, cultural treasures like the rock-hewn churches of Lalibela and the medieval castles of Gondar, coupled with its breathtaking landscapes and diverse wildlife, offer travelers an immersive experience in one of Africa''s most intriguing destinations.'
	WHEN country_name = 'Slovenia' THEN 'Slovenia''s pristine nature, with its emerald-green lakes, majestic mountains, and charming medieval towns like Ljubljana and Bled, beckons visitors to explore its hidden gems and outdoor adventures, making it a delightful destination for nature lovers and culture enthusiasts alike.'
	WHEN country_name = 'Guatemala' THEN 'Guatemala''s rich Mayan heritage, colonial architecture, and lush rainforests, punctuated by volcanic landscapes and serene Lake Atitlán, offer travelers a tapestry of history, culture, and natural beauty, creating an unforgettable journey through the heart of Central America.'
	WHEN country_name = 'Costa Rica' THEN 'Costa Rica''s abundant biodiversity, with its tropical rainforests, pristine beaches, and diverse wildlife, invites travelers to experience eco-adventures like zip-lining through the jungle canopy and exploring volcanic landscapes, making it a paradise for nature lovers and outdoor enthusiasts.'
	WHEN country_name = 'Lebanon' THEN 'Lebanon''s rich history, seen in its ancient ruins like Baalbek and Byblos, coupled with its vibrant culture, cosmopolitan cities, and Mediterranean coastline, offer travelers a captivating blend of antiquity, modernity, and natural beauty, making it a hidden gem of the Middle East.'
END;


-- Creating BucketList Functionality 
-- Join table to link app_user and city
CREATE TABLE app_user_city (
    app_user_id INT NOT NULL,
    city_id INT NOT NULL,
    PRIMARY KEY (app_user_id, city_id),
    FOREIGN KEY (app_user_id) REFERENCES app_user(app_user_id),
    FOREIGN KEY (city_id) REFERENCES city(city_id)
);

-- Join table to link app_user and attraction
CREATE TABLE app_user_attraction (
    app_user_id INT NOT NULL,
    attraction_id INT NOT NULL,
    PRIMARY KEY (app_user_id, attraction_id),
    FOREIGN KEY (app_user_id) REFERENCES app_user(app_user_id),
    FOREIGN KEY (attraction_id) REFERENCES attraction(attraction_id)
);

-- Join table to link app_user and country
CREATE TABLE app_user_country (
    app_user_id INT NOT NULL,
    country_id INT NOT NULL,
    PRIMARY KEY (app_user_id, country_id),
    FOREIGN KEY (app_user_id) REFERENCES app_user(app_user_id),
    FOREIGN KEY (country_id) REFERENCES country(country_id)
);




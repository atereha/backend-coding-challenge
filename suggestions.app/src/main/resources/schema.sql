CREATE TABLE Suggestions (
    geonameid INTEGER PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    latitude DOUBLE PRECISION NOT NULL,
    longitude DOUBLE PRECISION NOT NULL
);

CREATE INDEX SuggestionsNameIndex ON Suggestions(NAME);

INSERT INTO Suggestions(geonameid, name, latitude, longitude) VALUES (1, 'London, ON, Canada', 42.98339, -81.23304);
INSERT INTO Suggestions(geonameid, name, latitude, longitude) VALUES (2, 'London, OH, USA', 39.88645, -83.44825);
INSERT INTO Suggestions(geonameid, name, latitude, longitude) VALUES (3, 'London, KY, USA', 7.12898, -84.08326);
INSERT INTO Suggestions(geonameid, name, latitude, longitude) VALUES (4, 'Londontowne, MD, USA', 38.93345, -76.54941);
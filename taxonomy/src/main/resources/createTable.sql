DROP TABLE IF EXISTS KINGDOM;
CREATE TABLE KINGDOM (
	ID INTEGER PRIMARY KEY AUTOINCREMENT,
	CODE TEXT UNIQUE NOT NULL,
	NAME TEXT NOT NULL
);

DROP TABLE IF EXISTS INDEX_;
CREATE TABLE INDEX_ (
	ID INTEGER PRIMARY KEY AUTOINCREMENT,
	VALUE TEXT UNIQUE NOT NULL
);

DROP TABLE IF EXISTS TAG;
CREATE TABLE TAG (
	ID INTEGER PRIMARY KEY AUTOINCREMENT,
	NAME TEXT UNIQUE NOT NULL,
	EXPLAINTION TEXT
);

/**
 *	NAME PATTERN: [NAME_LOCALE] (exam: en)
 *	VALUE PATTERN: NAME_LOCALE::VALUE_LOCALE (exam: root)
 */
DROP TABLE IF EXISTS LOCALES;
CREATE TABLE LOCALES (
	ID INTEGER PRIMARY KEY AUTOINCREMENT,
	NAME TEXT NOT NULL,
	VALUE TEXT
);

/**
 *	LOCALE IDS SPEARATE BY SYMBOL '::' (exam: 1::5::15::9)
 */
DROP TABLE IF EXISTS FAMILY;
CREATE TABLE FAMILY (
	ID INTEGER PRIMARY KEY AUTOINCREMENT,
	KINGDOM_ID INTEGER NOT NULL,
	NAME TEXT NOT NULL,
	LOCALE_IDS TEXT,
	DESCRIPTION TEXT,
	AVATAR TEXT,
	FOREIGN KEY (KINGDOM_ID) REFERENCES KINGDOM(ID)
);

/*
 *	SAME AS FAMILY
 */
DROP TABLE IF EXISTS GENUS;
CREATE TABLE GENUS (
	ID INTEGER PRIMARY KEY AUTOINCREMENT,
	KINGDOM_ID INTEGER NOT NULL,
	NAME TEXT NOT NULL,
	LOCALE_IDS TEXT,
	DESCRIPTION TEXT,
	AVATAR TEXT,
	VARIANT_IDS TEXT,
	FOREIGN KEY (KINGDOM_ID) REFERENCES KINGDOM(ID)
);

DROP TABLE IF EXISTS SPECIES;
CREATE TABLE SPECIES (
	ID INTEGER PRIMARY KEY AUTOINCREMENT,
	NAME TEXT NOT NULL,
	VARIANT_IDS TEXT
);

/**
 *	LOCALE IDS SPEARATE BY SYMBOL '::' (exam: 1::5::15::9)
 */
DROP TABLE IF EXISTS GLOSSARY;
CREATE TABLE GLOSSARY (
	ID INTEGER PRIMARY KEY AUTOINCREMENT,
	NAME TEXT NOT NULL,
	LOCALE_IDS TEXT,
	EXPLAINTION TEXT,
	EXAMPLE TEXT
);

/* TYPE = 1 AS 'S' | TYPE = 2 AS 'O' */
DROP TABLE IF EXISTS VARIANT;
CREATE TABLE VARIANT (
	ID INTEGER PRIMARY KEY AUTOINCREMENT,
	VALUE TEXT,
	TYPE NUMERIC	
);

/* EN_NAMES and VN_NAMES contain multi value and separate by symbol '::' */
DROP TABLE IF EXISTS [OBJECT];
CREATE TABLE [OBJECT]
(
   ID INTEGER PRIMARY KEY AUTOINCREMENT,
   KINGDOM_ID INTEGER NOT NULL,
   FAMILY_IDS TEXT NOT NULL,
   INDEX_IDS TEXT,
   GENUS_ID INTEGER NOT NULL,
   SPECIES_ID INTEGER NOT NULL,
   EN_NAMES TEXT,
   VN_NAMES TEXT,
   TAG_IDS TEXT,
   CREATE_DATE TEXT,
   MODIFIY_DATE TEXT,
   REFERENCE TEXT,
   DES TEXT,
   AVATAR TEXT,
   FOREIGN KEY (KINGDOM_ID) REFERENCES KINGDOM(ID)
);

/**
 * INIT KINGDOM DATA
 */
INSERT INTO KINGDOM VALUES(NULL, 'B', 'Botanical');
INSERT INTO KINGDOM VALUES(NULL, 'Z', 'Zoological');



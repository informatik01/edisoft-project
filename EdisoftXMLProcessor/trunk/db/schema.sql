-- Table: header

-- DROP TABLE IF EXISTS header;

CREATE TABLE header
(
  uid character varying NOT NULL,
  document_type character varying NOT NULL,
  receiver_system_type character varying NOT NULL,
  document_number bigint NOT NULL,
  document_date1 date NOT NULL,
  document_date2 date NOT NULL,
  sender_iln bigint NOT NULL,
  sender_code_by_receiver integer NOT NULL,
  sender_name character varying NOT NULL,
  sender_address character varying NOT NULL,
  receiver_iln bigint NOT NULL,
  receiver_code_by_receiver integer NOT NULL,
  receiver_name character varying NOT NULL,
  receiver_address character varying NOT NULL,
  last_modified bigint NOT NULL,
  CONSTRAINT pk_header PRIMARY KEY (uid )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE header
  OWNER TO postgres;

  
-- Table: details

-- DROP TABLE IF EXISTS details;

CREATE TABLE details
(
  id serial NOT NULL,
  line_number integer NOT NULL,
  supplier_item_code integer NOT NULL,
  item_description character varying,
  invoice_quantity real NOT NULL,
  invoice_unit_net_price numeric NOT NULL,
  header_uid character varying NOT NULL,
  CONSTRAINT pk_details PRIMARY KEY (id ),
  CONSTRAINT fk_details_ref_header FOREIGN KEY (header_uid)
      REFERENCES header (uid) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE details
  OWNER TO postgres;

  
-- Table: users

-- DROP TABLE users;

CREATE TABLE users
(
  id serial NOT NULL,
  fisrt_name character varying,
  last_name character varying,
  email character varying NOT NULL,
  password character varying(64) NOT NULL,
  salt character varying(64) NOT NULL,
  CONSTRAINT pk_user PRIMARY KEY (id ),
  CONSTRAINT uq_user UNIQUE (email )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE users
  OWNER TO postgres;

INSERT INTO users (id, first_name, last_name, email, password, salt) VALUES (1, 'Ivan', 'Ivanov', 'ivan.ivanov@mail.ru', 'df15cdb4e7dffcec6a69b90257c5cbd0f630f6d1d5ba0f0c422cf709009644db', 'fc0b2c19d32c66d3f0b53a59f4b8ed4476577396d35bd3b09a174e7b7c21a3cb');
INSERT INTO users (id, first_name, last_name, email, password, salt) VALUES (2, 'Anton', 'Antonov', 'anton@mail.ru', '2cf45080814af6e513022cbf220afc376afbe965a8d4779714706ba7646a28fb', '6553465d63e2538ce6ce9618adcfb387a2f1e48ef9bd99c8caa4897cae681a2a');
INSERT INTO users (id, first_name, last_name, email, password, salt) VALUES (4, 'Afanasij', 'Borschov', 'afonja@mail.ru', '784330edcee88f3ad26cb75264b54bcada98381d70a024f474181bfc60cc9485', '7d29161959df3c97e7c63f0265e026a0d8fc507bacb8b5c7e61228d458713ec9');

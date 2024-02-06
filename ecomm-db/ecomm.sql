CREATE SCHEMA IF NOT EXISTS "public";

CREATE  TABLE "public".address ( 
	id                   integer  NOT NULL GENERATED  ALWAYS AS IDENTITY ,
	street_address       varchar(200)    ,
	city                 varchar(100)    ,
	"state"              varchar(100)    ,
	postal_code          varchar    ,
	country              varchar    ,
	name                 varchar(100)    ,
	CONSTRAINT pk_address PRIMARY KEY ( id )
 );

CREATE  TABLE "public".brand ( 
	id                   integer  NOT NULL GENERATED  ALWAYS AS IDENTITY ,
	name                 varchar(100)    ,
	brand_category       varchar    ,
	CONSTRAINT pk_brand PRIMARY KEY ( id )
 );

CREATE  TABLE "public".category ( 
	id                   integer  NOT NULL GENERATED  ALWAYS AS IDENTITY ,
	name                 varchar(100)    ,
	parent_id            integer    ,
	CONSTRAINT pk_category PRIMARY KEY ( id ),
	CONSTRAINT fk_category_category FOREIGN KEY ( parent_id ) REFERENCES "public".category( id )   
 );

CREATE UNIQUE INDEX unq_category ON "public".category ( name, parent_id );

CREATE  TABLE "public".feature_template ( 
	id                   integer  NOT NULL GENERATED  ALWAYS AS IDENTITY ,
	category_id          integer    ,
	features             jsonb    ,
	CONSTRAINT pk_feature_template PRIMARY KEY ( id ),
	CONSTRAINT unq_feature_template UNIQUE ( category_id ) ,
	CONSTRAINT fk_feature_template_category FOREIGN KEY ( category_id ) REFERENCES "public".category( id )   
 );

CREATE  TABLE "public".master_variant ( 
	id                   integer  NOT NULL GENERATED  ALWAYS AS IDENTITY ,
	category_id          integer    ,
	feature_names        json    ,
	CONSTRAINT pk_master_variant PRIMARY KEY ( id ),
	CONSTRAINT fk_master_variant_category FOREIGN KEY ( category_id ) REFERENCES "public".category( id )   
 );

CREATE  TABLE "public".payment_method ( 
	id                   integer  NOT NULL GENERATED  ALWAYS AS IDENTITY ,
	payment_type         varchar    ,
	CONSTRAINT pk_payements PRIMARY KEY ( id )
 );

CREATE  TABLE "public".product ( 
	id                   integer  NOT NULL GENERATED  ALWAYS AS IDENTITY ,
	name                 varchar(100)  NOT NULL  ,
	brand_id             integer    ,
	category_id          integer    ,
	features             jsonb    ,
	description          varchar    ,
	category_tree        varchar    ,
	CONSTRAINT pk_product PRIMARY KEY ( id ),
	CONSTRAINT fk_product_brand FOREIGN KEY ( brand_id ) REFERENCES "public".brand( id )   ,
	CONSTRAINT fk_product_category FOREIGN KEY ( category_id ) REFERENCES "public".category( id )   
 );

CREATE  TABLE "public".product_images ( 
	id                   integer  NOT NULL GENERATED  ALWAYS AS IDENTITY ,
	image_url            varchar    ,
	"type"               varchar    ,
	product_id           integer    ,
	CONSTRAINT pk_product_images PRIMARY KEY ( id ),
	CONSTRAINT fk_product_images_product FOREIGN KEY ( product_id ) REFERENCES "public".product( id )   
 );

CREATE  TABLE "public".product_variant ( 
	id                   integer  NOT NULL GENERATED  ALWAYS AS IDENTITY ,
	variant_id           integer    ,
	feature_values       jsonb    ,
	price                numeric(16,2)    ,
	product_id           integer    ,
	sku                  varchar    ,
	CONSTRAINT pk_variants PRIMARY KEY ( id ),
	CONSTRAINT fk_product_variants FOREIGN KEY ( variant_id ) REFERENCES "public".master_variant( id )   ,
	CONSTRAINT fk_product_variants_product FOREIGN KEY ( product_id ) REFERENCES "public".product( id )   
 );

CREATE  TABLE "public".promotion ( 
	id                   integer  NOT NULL GENERATED  ALWAYS AS IDENTITY ,
	name                 varchar(100)    ,
	description          varchar    ,
	applied_to_type      varchar    ,
	applied_to           varchar    ,
	expires_in           smallint    ,
	status               varchar    ,
	percentage_discount  integer    ,
	CONSTRAINT pk_promotion PRIMARY KEY ( id )
 );

CREATE  TABLE "public".users ( 
	id                   integer  NOT NULL GENERATED  ALWAYS AS IDENTITY ,
	first_name           varchar(100)  NOT NULL  ,
	last_name            varchar(100)  NOT NULL  ,
	email                varchar    ,
	mobile               varchar    ,
	created_at           timestamp DEFAULT CURRENT_TIMESTAMP   ,
	updated_at           timestamp DEFAULT CURRENT_TIMESTAMP   ,
	provider             varchar    ,
	status               varchar    ,
	passwd               varchar    ,
	subject              varchar    ,
	CONSTRAINT pk_users PRIMARY KEY ( id ),
	CONSTRAINT unq_users UNIQUE ( email ) 
 );

CREATE  TABLE "public".cart ( 
	id                   integer  NOT NULL GENERATED  ALWAYS AS IDENTITY ,
	user_id              integer    ,
	status               varchar    ,
	total_price          numeric(16,2)    ,
	discount             numeric(16,2)    ,
	created_at           timestamp DEFAULT CURRENT_TIMESTAMP   ,
	updated_at           timestamp DEFAULT CURRENT_TIMESTAMP   ,
	CONSTRAINT pk_cart PRIMARY KEY ( id ),
	CONSTRAINT fk_cart_users FOREIGN KEY ( user_id ) REFERENCES "public".users( id )   
 );

CREATE  TABLE "public".cart_item ( 
	id                   integer  NOT NULL GENERATED  ALWAYS AS IDENTITY ,
	cart_id              integer    ,
	product_id           integer    ,
	quantity             smallint    ,
	variant_id           integer  NOT NULL  ,
	CONSTRAINT pk_cart_item PRIMARY KEY ( id ),
	CONSTRAINT fk_cart_item_product_variant FOREIGN KEY ( variant_id ) REFERENCES "public".product_variant( id )   ,
	CONSTRAINT fk_cart_item_product FOREIGN KEY ( product_id ) REFERENCES "public".product( id )   ,
	CONSTRAINT fk_cart_item_cart FOREIGN KEY ( cart_id ) REFERENCES "public".cart( id )   
 );

CREATE UNIQUE INDEX unq_cart_item_cart_id ON "public".cart_item ( cart_id, variant_id, product_id );

CREATE  TABLE "public".inventory ( 
	id                   integer  NOT NULL GENERATED  ALWAYS AS IDENTITY ,
	product_id           integer    ,
	sku                  varchar    ,
	quantity_available   smallint    ,
	quantity_reserved    smallint    ,
	quantity_sold        smallint    ,
	CONSTRAINT pk_inventory PRIMARY KEY ( id ),
	CONSTRAINT fk_inventory_product FOREIGN KEY ( product_id ) REFERENCES "public".product( id )   
 );

CREATE  TABLE "public".orders ( 
	id                   integer  NOT NULL GENERATED  ALWAYS AS IDENTITY ,
	order_no             uuid  NOT NULL  ,
	user_id              integer  NOT NULL  ,
	order_placed_on      timestamp DEFAULT CURRENT_TIMESTAMP   ,
	status               varchar    ,
	total_price          numeric(16,2)    ,
	currency             varchar    ,
	delivered_on         timestamp    ,
	address_id           integer    ,
	payment_method       varchar    ,
	delivery_status      varchar    ,
	CONSTRAINT pk_orders PRIMARY KEY ( id ),
	CONSTRAINT fk_orders_address FOREIGN KEY ( address_id ) REFERENCES "public".address( id )   ,
	CONSTRAINT fk_orders_users FOREIGN KEY ( user_id ) REFERENCES "public".users( id )   
 );

CREATE  TABLE "public".user_address ( 
	id                   integer  NOT NULL GENERATED  ALWAYS AS IDENTITY ,
	user_id              integer    ,
	address_id           integer    ,
	default_address      boolean    ,
	CONSTRAINT pk_user_address PRIMARY KEY ( id ),
	CONSTRAINT fk_user_address_address FOREIGN KEY ( address_id ) REFERENCES "public".address( id )   ,
	CONSTRAINT fk_user_address_user_id FOREIGN KEY ( user_id ) REFERENCES "public".users( id )   
 );

CREATE  TABLE "public".user_preferences ( 
	id                   integer  NOT NULL GENERATED  ALWAYS AS IDENTITY ,
	user_id              integer  NOT NULL  ,
	"language"           varchar    ,
	two_factor_enabled   boolean    ,
	two_factor_mode      varchar    ,
	currency             char(2)    ,
	CONSTRAINT pk_user_preferences PRIMARY KEY ( id ),
	CONSTRAINT fk_user_preferences_users FOREIGN KEY ( user_id ) REFERENCES "public".users( id )   
 );

CREATE  TABLE "public".user_roles ( 
	id                   integer  NOT NULL GENERATED  ALWAYS AS IDENTITY ,
	user_id              integer    ,
	"role"               varchar(100)    ,
	CONSTRAINT pk_roles PRIMARY KEY ( id ),
	CONSTRAINT fk_user_roles_users FOREIGN KEY ( user_id ) REFERENCES "public".users( id )   
 );

CREATE  TABLE "public".invoices ( 
	id                   integer  NOT NULL GENERATED  ALWAYS AS IDENTITY ,
	invoice_no           varchar    ,
	order_id             integer    ,
	url                  varchar    ,
	CONSTRAINT pk_invoices PRIMARY KEY ( id ),
	CONSTRAINT fk_invoices_orders FOREIGN KEY ( order_id ) REFERENCES "public".orders( id )   
 );

CREATE  TABLE "public".order_line ( 
	id                   integer  NOT NULL GENERATED  ALWAYS AS IDENTITY ,
	order_id             integer  NOT NULL  ,
	product_id           integer    ,
	quantity             smallint    ,
	variant_id           integer    ,
	CONSTRAINT pk_order_line PRIMARY KEY ( id ),
	CONSTRAINT fk_order_line_product_variant FOREIGN KEY ( variant_id ) REFERENCES "public".product_variant( id )   ,
	CONSTRAINT fk_order_line_product FOREIGN KEY ( product_id ) REFERENCES "public".product( id )   ,
	CONSTRAINT fk_order_line_orders FOREIGN KEY ( order_id ) REFERENCES "public".orders( id )   
 );

COMMENT ON TABLE "public".feature_template IS 'This table provides template for all features for a particular category';

COMMENT ON COLUMN "public".master_variant.feature_names IS 'list of feature names for a particular category';

COMMENT ON COLUMN "public".users.subject IS 'used for userinfor in oauth';


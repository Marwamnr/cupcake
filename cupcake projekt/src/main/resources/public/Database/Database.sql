-- This script was generated by the ERD tool in pgAdmin 4.
-- Please log an issue at https://github.com/pgadmin-org/pgadmin4/issues/new/choose if you find any bugs, including reproduction steps.
BEGIN;


CREATE TABLE IF NOT EXISTS public.bund
(
    bund_name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    bund_id integer NOT NULL,
    bund_price double precision,
    CONSTRAINT bund_pkey PRIMARY KEY (bund_id)
    );

CREATE TABLE IF NOT EXISTS public."order"
(
    order_id integer NOT NULL,
    antal integer NOT NULL,
    sum double precision NOT NULL,
    user_id integer,
    CONSTRAINT order_pkey PRIMARY KEY (order_id)
    );

CREATE TABLE IF NOT EXISTS public.orderline
(
    orderline_id serial NOT NULL,
    antal integer NOT NULL,
    price integer NOT NULL,
    topping_id integer NOT NULL,
    bund_id integer NOT NULL,
    order_id integer NOT NULL
);

CREATE TABLE IF NOT EXISTS public.topping
(
    topping_name character varying(20) COLLATE pg_catalog."default" NOT NULL,
    topping_id integer NOT NULL,
    topping_price double precision,
    CONSTRAINT topping_pkey PRIMARY KEY (topping_id)
    );

CREATE TABLE IF NOT EXISTS public.users
(
    user_id bigint NOT NULL DEFAULT nextval('users_id_seq'::regclass),
    email character varying COLLATE pg_catalog."default",
    password character varying COLLATE pg_catalog."default",
    role character varying COLLATE pg_catalog."default",
    balance integer NOT NULL DEFAULT 0,
    CONSTRAINT users_pkey1 PRIMARY KEY (user_id)
    );

ALTER TABLE IF EXISTS public."order"
    ADD CONSTRAINT fk_user_order FOREIGN KEY (user_id)
    REFERENCES public.users (user_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.orderline
    ADD CONSTRAINT orderline_bund_id_fkey FOREIGN KEY (bund_id)
    REFERENCES public.bund (bund_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.orderline
    ADD CONSTRAINT orderline_order_id_fkey FOREIGN KEY (order_id)
    REFERENCES public."order" (order_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.orderline
    ADD CONSTRAINT orderline_topping_id_fkey FOREIGN KEY (topping_id)
    REFERENCES public.topping (topping_id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION
    NOT VALID;

END;
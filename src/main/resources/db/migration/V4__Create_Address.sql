-- Criação da tabela address
CREATE TABLE public.address (
    id serial4 NOT NULL,
    active bool NOT NULL,
    address varchar(150) NOT NULL,
    address_complete varchar(150) NOT NULL,
    created_at timestamp NOT NULL,
    latitude numeric(10, 8) NOT NULL,
    longitude numeric(11, 8) NOT NULL,
    type_address varchar(150) NOT NULL,
    updated_at timestamp NOT NULL,
    zip_code varchar(150) NOT NULL,
    city_id int4 NULL,
    district_id int4 NULL,
    region_id int4 NULL,
    state_id int4 NULL,
    CONSTRAINT address_pkey PRIMARY KEY (id),
    CONSTRAINT uk_j86ha8yeiogoly4mgxhfv0pab UNIQUE (zip_code),
    CONSTRAINT fk1uk1eurcj7mhj5tpql2yurw32 FOREIGN KEY (state_id) REFERENCES public.states(id),
    CONSTRAINT fka6p4hdfq92oyy92gb5ra8xiw7 FOREIGN KEY (city_id) REFERENCES public.cities(id),
    CONSTRAINT fkixhc8i9e7j7ktwckfvi954h5v FOREIGN KEY (region_id) REFERENCES public.regions(id),
    CONSTRAINT fkq9uo60rtdod4kgwhsbuf31nb2 FOREIGN KEY (district_id) REFERENCES public.districts(id)
);

-- Índices para address
CREATE INDEX idx_address_address ON public.address(address);
CREATE INDEX idx_address_type_address ON public.address(type_address);
CREATE INDEX idx_address_city_id ON public.address(city_id);
CREATE INDEX idx_address_district_id ON public.address(district_id);
CREATE INDEX idx_address_region_id ON public.address(region_id);
CREATE INDEX idx_address_state_id ON public.address(state_id);

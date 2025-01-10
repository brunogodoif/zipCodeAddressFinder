-- Criação da tabela districts
CREATE TABLE public.districts (
    id int4 NOT NULL,
    created_at timestamp NOT NULL,
    district varchar(150) NOT NULL,
    latitude numeric(10, 8) NOT NULL,
    longitude numeric(11, 8) NOT NULL,
    updated_at timestamp NOT NULL,
    city_id int4 NULL,
    CONSTRAINT districts_pkey PRIMARY KEY (id),
    CONSTRAINT fk3g7x8w4lc7qxth7ibrr5j73mn FOREIGN KEY (city_id) REFERENCES public.cities(id)
);

-- Índices para districts
CREATE INDEX idx_districts_district ON public.districts(district);
CREATE INDEX idx_districts_city_id ON public.districts(city_id);

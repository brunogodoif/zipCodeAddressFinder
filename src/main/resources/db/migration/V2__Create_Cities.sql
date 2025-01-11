-- Criação da tabela cities
CREATE TABLE public.cities (
    id serial4 NOT NULL,
    city varchar(150) NOT NULL,
    created_at timestamp NOT NULL,
    ddd_code int4 NOT NULL,
    latitude numeric(10, 8) NOT NULL,
    longitude numeric(11, 8) NOT NULL,
    updated_at timestamp NOT NULL,
    state_id int4 NULL,
    CONSTRAINT cities_pkey PRIMARY KEY (id),
    CONSTRAINT fksu54e1tlhaof4oklvv7uphsli FOREIGN KEY (state_id) REFERENCES public.states(id)
);

-- Índices para cities
CREATE INDEX idx_cities_city ON public.cities(city);
CREATE INDEX idx_cities_state_id ON public.cities(state_id);

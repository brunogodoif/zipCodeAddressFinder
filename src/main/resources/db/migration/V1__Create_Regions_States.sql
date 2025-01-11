-- Criação da tabela regions
CREATE TABLE public.regions (
    id serial4 NOT NULL,
    created_at timestamp NOT NULL,
    region varchar(150) NOT NULL,
    updated_at timestamp NOT NULL,
    CONSTRAINT regions_pkey PRIMARY KEY (id)
);

-- Índice para regions
CREATE INDEX idx_regions_region ON public.regions(region);

-- Criação da tabela states
CREATE TABLE public.states (
    id serial4 NOT NULL,
    capital varchar(150) NOT NULL,
    created_at timestamp NOT NULL,
    latitude numeric(10, 8) NOT NULL,
    longitude numeric(11, 8) NOT NULL,
    state varchar(150) NOT NULL,
    uf varchar(3) NOT NULL,
    updated_at timestamp NOT NULL,
    region_id int4 NULL,
    CONSTRAINT states_pkey PRIMARY KEY (id),
    CONSTRAINT fkiqk9q1vsgds6qmfuu3690mre8 FOREIGN KEY (region_id) REFERENCES public.regions(id)
);

-- Índices para states
CREATE INDEX idx_states_capital ON public.states(capital);
CREATE INDEX idx_states_uf ON public.states(uf);

FROM postgres:15.1-alpine


COPY *.sql /docker-entrypoint-initdb.d/

 FROM postgres:14.7
#ENV POSTGRES_DB prototype1
#ENV POSTGRES_PASSWORD avizva9372
COPY ./install-pgcrypto.sh /docker-entrypoint-initdb.d/
RUN chown -R postgres:postgres /docker-entrypoint-initdb.d/

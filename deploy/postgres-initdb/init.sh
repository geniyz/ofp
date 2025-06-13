#!/bin/sh
echo "password_encryption=md5" >> /var/lib/postgresql/data/postgresql.conf
echo "" >> /var/lib/postgresql/data/pg_hba.conf
echo "host all all all md5" >> /var/lib/postgresql/data/pg_hba.conf


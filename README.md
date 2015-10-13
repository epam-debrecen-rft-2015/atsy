# Atsy [![Build Status](https://travis-ci.org/epam-debrecen-rft-2015/atsy.svg?branch=master)](https://travis-ci.org/epam-debrecen-rft-2015/atsy)
Applicant Tracking System

Building the project:
=========================
As easy as:

     mvn clean install

Running Atsy locally:
=========================

    mvn tomcat7:run

Pre: U have mysql locally which has a database called atsy, and a user in it with details:
     travis - no password

Running integration tests as part of the build:
===============================================

    mvn clean install -Pintegration

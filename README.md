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

Definition of Done
==================

1. Code is well documented:
  * All public service methods have JavaDoc comments. 
  * Where necessary for understanding, non-public methods have JavaDoc as well. 
1. Data container objects have (Lombok can be used for method generation)
  * private fields
  * getter methods, 
  * setter methods,
  * toString() method overridden, 
  * hashCode() method overridden, 
  * equals() method overridden.
1. Logging is added
  * when an exepction happens,
  * when a significant event happens (user is deleted, candidate added, etc.). 
1. Database migrations scripts are honored:
  * Migration script is added if the changes require database modifications.
1. Project documentation is kept up-to-date:
  * README.md is updated if there are some changes in the application setup process.
1. Version control is kept clean:
  * Code is placed in a task related branch (feature branch)
  * Code for subtasks have a separate branch (sub-task branch)
  * Before pushing changes in branch which is not tracked remotely use git rebase
1. Pull request is created
1. Functionality does not break:
  * mvn tomcat7:run works (manual check)
  * changed feature does not break (manual check)
  * All unit tests pass
  * All integration tests pass
  * CI server could build the project
  * CI server could run tests
1. Functionality is complete:
  * Acceptance criteria is met

Lombok
======

1. home page:
https://projectlombok.org/index.html

1. features:
https://projectlombok.org/features/

1. download:
https://projectlombok.org/download.html

1. installion details:
https://github.com/mplushnikov/lombok-intellij-plugin

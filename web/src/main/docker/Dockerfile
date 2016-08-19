FROM tomcat:8.0-jre8

RUN rm -f $CATALINA_HOME/webapps/ROOT.war
RUN rm -rf $CATALINA_HOME/webapps/ROOT

COPY context.xml $CATALINA_HOME/conf/context.xml
COPY provided-jars/* $CATALINA_HOME/lib/
COPY atsy.war $CATALINA_HOME/webapps/atsy.war
COPY wait-for-it.sh /wait-for-it.sh

#====================================================================
#	DOCKERFILE FOR CREATING INSTANCE TO GRADE CODE
#====================================================================
#FROM : where you want to get the image from
#	https://hub.docker.com/_/amazonlinux/
#	We are retrieving it from the OFFICIAL repo
FROM amazonlinux:2017.03

#RUN : commands  to run while instanciating
#	install the development tools 
#		c++
#		c
#		etc.
RUN yum groupinstall 'Development Tools' -y

#COPY : copy our java rpm into a file called java
#	copies to our new container
#	NEED TO HAVE SAME JAVA YOU HAVE ON EC2 
#		SINCE CODE IS COMPILED THERE
COPY jdk-8u121-linux-x64.rpm ./java

#RUN : install the java rpm file we copied above
RUN rpm -ivh ./java

#RUN : sets our JAVA_HOME variable to the file
RUN export JAVA_HOME=/usr/java

#COPY : copies our bashscript file over to the container
COPY TheHulq.sh .

#======= FILES SHOULD GO HERE  ====

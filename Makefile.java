.SUFFIXES : .java .class
JAVAC=javac
PKG_DIR=jSrc

SRCS =  \
	${PKG_DIR}/dbLayer.java \
	${PKG_DIR}/XmlIf.java \
	${PKG_DIR}/dbLayerXml.java \
	${PKG_DIR}/dbLayerXmlControllerLayout.java \
	${PKG_DIR}/ButtonLayout.java \
	${PKG_DIR}/GameButton.java \
	${PKG_DIR}/dbLayerXmlGameButtons.java \
	${PKG_DIR}/XboxButtonMapping.java 

CLASSES = ${SRCS:.java=.class}



all: dist/XboxButtonMapping.jar

dist/XboxButtonMapping.jar: ${CLASSES}
	jar -cfm $@ Manifest.txt $?



MainCli.class: MainCli.java
        javac MainCli.java -classpath dist/SqlconstructorMlXml.jar:dist/Factory.jar
        echo "test using: java MainCli -jar dist/SqlconstructorMlXml.jar"

.java.class :
	${JAVAC} -classpath ./${PKG_DIR} $<

clean:
	- rm *~
	- rm ${PKG_DIR}/*.class
	- rm dist/*.jar
	- rm *.png


release:
	echo "Release"
	
pictures:
	


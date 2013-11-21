mvn install:install-file -Dfile=com.ibm.mq.jar -DgroupId=com.ibm -DartifactId=mq -Dversion=7.5.0.0 -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=com.ibm.mqjms.jar -DgroupId=com.ibm -DartifactId=mqjms -Dversion=7.5.0.0 -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=com.ibm.mq.pcf.jar -DgroupId=com.ibm -DartifactId=mq.pcf -Dversion=7.5.0.0 -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=dhbcore.jar -DgroupId=com.ibm -DartifactId=dhbcore -Dversion=7.5.0.0 -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=com.ibm.mq.commonservices.jar -DgroupId=com.ibm -DartifactId=mq.commonservices -Dversion=7.5.0.0 -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=com.ibm.mq.headers.jar -DgroupId=com.ibm -DartifactId=mq.headers -Dversion=7.5.0.0 -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=com.ibm.mq.jmqi.jar -DgroupId=com.ibm -DartifactId=mq.jmqi -Dversion=7.5.0.0 -Dpackaging=jar -DgeneratePom=true
HOME=/home/frantik/project/server-mobile-dp

echo "Starting tomcat7:redeploy"

cd $HOME

echo "mvn clean package tomcat7:redeploy -P production -X"

mvn clean package tomcat7:redeploy -P production -X

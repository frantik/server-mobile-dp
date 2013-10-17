HOME=/home/frantik/project/server-mobile-dp

echo "Starting deploy"

cd $HOME

echo "mvn clean package tomcat7:redeploy -P production"

mvn clean package tomcat7:redeploy -P production

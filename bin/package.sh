HOME=/home/frantik/project/sever-mobile-dp

echo "Starting package"

cd $HOME

echo "mvn clean package -P production"

mvn clean package -P production

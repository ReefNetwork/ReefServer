move build\libs\ReefSeichi-1.0-SNAPSHOT-all.jar D:\docker\nukkit\plugins
cd D:\docker\nukkit\plugins
@echo StartingServer...
docker-compose up -d
docker attach reef
@echo Initializing...
docker-compose down
@cd D:\code\nukkitPlugin\ReefSeichi
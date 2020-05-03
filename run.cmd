move build\libs\ReefSeichi-1.0-SNAPSHOT-all.jar D:\code\nukkitPlugin\Nukkit\data\plugins
cd D:\code\nukkitPlugin\Nukkit\data\plugins
@echo StartingServer...
docker-compose up -d
docker attach nukkit_coral_reef_1
@echo Initializing...
docker-compose down
@cd D:\code\nukkitPlugin\ReefSeichi
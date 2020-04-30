move build\libs\ReefSeichi-1.0-SNAPSHOT-all.jar D:\code\nukkitPlugin\Nukkit\data\plugins
cd D:\code\nukkitPlugin\Nukkit\data\plugins
@color 0a
@echo StartingServer...
docker-compose start
@color 00
@echo Initializing...
@cd D:\code\nukkitPlugin\ReefSeichi
# CVE-Hub

## 项目打包

+ 使用maven对项目进行打包

```bash
mvn package
```



## 制作镜像

+ 当使用maven将项目打包在`targer/cvehub-0.0.1-SNAPSHOT.jar`位置，可以使用项目自带的Dockerfile文件制作docker镜像

```bash
docker build -t cvehub:v1 .
```

+ 使用如下命令查看是否打包成功

```bash
docker image list
```

如果镜像列表中有`cvehub:v1`的镜像，说明镜像制作成功



## docker 运行项目

+ 当docker进行制作完毕后，运行如下命令启动项目

```bash
docker run -d --name cvehub -p 8080:8080 cvehub:v1
```

+ 查看项目是否成功运行

```bash
docker ps
```

+ 停止项目

```bash
docker stop cvehub
```

+ 删除项目容器

```bash
docker rm cvehub
```


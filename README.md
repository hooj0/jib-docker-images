# `JIB` docker image package plugin

`JIB` 是 google 的镜像打包插件，基于 Maven 和 Gradle 都能完成镜像打包。JIB 支持将打包好的镜像推送到远程仓库；或者将打包推送到本地 docker 主机；或者将镜像打包成 tar 包，用加载的方式导入镜像。

## 构建镜像

```sh
$ mvn compile jib:build
```

## 构建镜像 TAR 包

```sh
$ mvn compile jib:buildTar

# 导入 tar 镜像包
$ docker load --input jib-image.tar
```

## 构建到守护进程主机

```sh
$ mvn compile jib:dockerBuild
```

## 绑定在 Maven 插件上

可以绑定`jib:build`到`Maven`生命周期，例如`package`，通过向`jib-maven-plugin`定义添加以下执行：

```
<plugin>
  <groupId>com.google.com.tools</groupId>
  <artifactId>jib-maven-plugin</artifactId>
  ...
  <executions>
    <execution>
      <phase>package</phase>
      <goals>
        <goal>build</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

然后，可以通过运行以下命令来构建容器映像：

```
$ mvn package
```

## 导出到`Docker`上下文

利用docker构建镜像，Jib 还可以导出 `Docker`上下文，以便可以根据需要使用Docker进行构建。

```
$ mvn compile jib:exportDockerContext
```

默认情况下，Docker上下文将在`target/jib-docker-context`中创建。可以使用`targetDir`配置选项或`jibTargetDir`参数更改此目录：

```
$ mvn compile jib:exportDockerContext -DjibTargetDir=my/docker/context/
```

然后，可以使用Docker构建映像：

```
$ docker build -t myimage my/docker/context/
```

## 设置超时时间

使用`jib.httpTimeout`系统属性为**镜像仓库**交互配置`HTTP`连接/读取超时，通过命令行以**毫秒**为单位进行配置（默认为`20000`可以将其设置`0`为无限超时）：

```
$ mvn compile jib:build -Djib.httpTimeout=3000
```
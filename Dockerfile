#基础镜像通过java8来的
FROM openjdk:8
#将当前文件中所有*.jar  拷贝到项目的app.jar中（这个app.jar是自己生成的）
COPY target/myExperiment-0.0.1-SNAPSHOT.jar /app.jar
COPY *.json /home/
#映射地址
CMD ["--server.port=8080"]
#暴露端口
EXPOSE 8080
#执行命令java  -jar
ENTRYPOINT ["java","-jar","/app.jar"]

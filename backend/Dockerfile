# Backend Dockerfile
FROM maven:3.9.5-amazoncorretto-21-al2023 AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src

# Maven 빌드
RUN mvn clean package -DskipTests

# 실행 환경
FROM amazoncorretto:21-alpine

WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=builder /app/target/*.jar app.jar

# 로그 디렉토리 생성
RUN mkdir logs && \
    chmod 777 logs

# 환경 변수 설정
ENV JAVA_OPTS="-Xms512m -Xmx512m -Djava.security.egd=file:/dev/./urandom"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
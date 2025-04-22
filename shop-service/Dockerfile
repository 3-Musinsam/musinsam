# 베이스 이미지
FROM openjdk:17-jdk-alpine

# 빌드 결과 jar 파일 경로 설정
ARG JAR_FILE=build/libs/*.jar

# JAR 복사
COPY ${JAR_FILE} app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
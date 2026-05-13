# 1. ビルド用環境（Java 17）
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app
COPY . .
# Windowsの改行コード対策と権限付与
RUN sed -i 's/\r$//' gradlew
RUN chmod +x ./gradlew
# アプリをビルド（テストはスキップ）
RUN ./gradlew bootJar -x test
# 邪魔なファイルを削除
RUN rm -f build/libs/*-plain.jar

# 2. 実行用環境
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
# ビルドしたjarファイルをコピー
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
# 起動コマンド
ENTRYPOINT ["java", "-jar", "app.jar"]
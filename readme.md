# 项目简介

基于Spring boot的简易笔记平台，类似知乎，csdn类的

## 技术栈

- Spring Boot 3.5.15+ JDK 21
- Gradle 构建
- MySQL 8.3.0
- MyBatis-Plus
- Redis（缓存 + 分布式锁）
- AOP（日志记录）

数据库见  `biji_lite.sql`

# 启动

1. 创建数据库
2. 修改 `application.yml` 中的数据库密码
3. 启动 Redis 服务
4. 执行 `./gradlew bootRun` 或在 IDEA 中运行


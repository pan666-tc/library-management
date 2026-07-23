# 📚 图书管理系统

基于 Spring Boot + MyBatis + MySQL + Redis 的图书信息管理后端系统，提供 RESTful API 接口，支持图书的增删改查、模糊搜索，并引入 Redis 缓存优化查询性能。

## 🚀 在线演示

**访问地址**：http://39.108.160.176:8080/book/1

**返回示例**：
```json
{"code":200,"message":"success","data":[{"id":1,"title":"测试书","author":"测试作者","price":39.9,"stock":10}]}
✨ 功能特性
图书信息增删改查

按书名模糊搜索

Redis 缓存热点数据，降低数据库压力

统一 API 响应格式

项目已部署至阿里云服务器，公网可访问

🛠️ 技术栈
技术	用途
Spring Boot 3.5.14	后端框架
MyBatis	ORM 框架
MySQL	数据库
Redis	缓存
Maven	项目构建
Git	版本控制
📊 性能优化
引入 Redis 缓存后，接口响应时间从 1.15s 降至 24ms，性能提升约 48 倍

使用 @Cacheable 缓存查询结果，@CacheEvict 保证缓存与数据库一致性

📁 项目结构
text
src/main/java/com/example/demo/
├── controller/    # 控制器层
├── service/       # 业务逻辑层
├── mapper/        # 数据访问层
├── entity/        # 实体类
└── config/        # 配置类
🔧 本地运行
克隆项目

配置 MySQL 和 Redis

修改 application.properties 中的数据库连接信息

执行 mvn clean package 打包

运行 java -jar target/demo-0.0.1-SNAPSHOT.jar

📦 部署信息
服务器：阿里云轻量应用服务器（Ubuntu 22.04）

部署方式：JAR 包独立运行

访问端口：8080

📝 接口示例
方法	路径	说明
GET	/book/list	查询所有图书
GET	/book/{id}	根据 ID 查询图书
POST	/book/add	新增图书
PUT	/book/update	修改图书
DELETE	/book/{id}	删除图书
GET	/book/search?title=xxx	按书名搜索

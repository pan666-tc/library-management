# 图书管理系统（Library Management System）

基于 Spring Boot + MyBatis + Redis 的图书管理后端系统，提供完整的图书信息增删改查 API，支持按书名模糊搜索，集成 Redis 缓存优化查询性能。

**项目已部署至阿里云服务器，可在线访问接口。**


## 技术栈

- **后端框架**：Spring Boot 2.7.x
- **持久层框架**：MyBatis
- **数据库**：MySQL
- **缓存中间件**：Redis
- **构建工具**：Maven
- **API 测试**：Postman
- **部署环境**：阿里云服务器（Linux）

---

## 主要功能

- 查询所有图书
- 根据 ID 查询单本图书（Redis 缓存支持）
- 新增图书
- 修改图书信息
- 删除图书
- 按书名模糊搜索

---

## 性能优化

- 基于 Redis 缓存实现热点数据缓存，查询接口响应时间从 **1.15秒** 降低至 **24毫秒**，性能提升约 **98%**。

---

## 项目结构
src/main/java/com/example/demo/
├── controller/ # 控制器层，处理HTTP请求
├── service/ # 业务逻辑层（含Redis缓存逻辑）
├── mapper/ # 数据访问层，MyBatis Mapper接口
├── entity/ # 实体类
└── common/ # 通用工具类（统一返回结果 Result）

## 快速启动

### 1. 克隆项目

git clone https://github.com/pan666-tc/library-management.git
2. 修改配置文件
打开 src/main/resources/application.properties，修改数据库和 Redis 连接信息：

properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_db?useUnicode=true&characterEncoding=utf-8
spring.datasource.username=root

spring.data.redis.host=localhost
spring.data.redis.port=6379
3. 创建数据库
执行以下 SQL 创建数据库和表：

sql
CREATE DATABASE library_db;
USE library_db;

CREATE TABLE book (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(50),
    price DOUBLE,
    stock INT
);
4. 启动项目
运行 DemoApplication.java 中的 main 方法。

5. 在线访问（部署版）
项目已部署至阿里云服务器，可通过以下地址访问接口：
http://39.108.160.176:8080/book/1
核心 API 示例
功能	请求方式	URL	请求体示例
查询所有图书	GET	/book/list	无
根据ID查询	GET	/book/{id}	无
新增图书	POST	/book/add	{"title":"Spring Boot实战", "author":"Craig Walls", "price":79.9, "stock":10}
修改图书	PUT	/book/update	{"id":1, "title":"Java编程思想", "price":99.9}
删除图书	DELETE	/book/{id}	无
模糊搜索	GET	/book/search?title=Java	无
接口文档
启动项目后访问 Swagger UI：

text
http://localhost:8080/swagger-ui/index.html
部署信息
服务器：阿里云

操作系统：Linux

部署方式：JAR 包运行

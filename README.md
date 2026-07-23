图书管理系统（Library Management System）
基于 Spring Boot + MyBatis + Redis 的图书管理后端系统，提供完整的图书信息增删改查 API，支持按书名模糊搜索，集成 Redis 缓存优化查询性能。

项目已部署至阿里云服务器，可在线访问接口。

在线演示地址：http://39.108.160.176:8080/book/1

返回示例：
{"code":200,"message":"success","data":[{"id":1,"title":"测试书","author":"测试作者","price":39.9,"stock":10}]}

技术栈
后端框架：Spring Boot
持久层框架：MyBatis
数据库：MySQL
缓存中间件：Redis
构建工具：Maven
API 测试：Postman
版本控制：Git
部署环境：阿里云服务器（Ubuntu）

主要功能
查询所有图书
根据 ID 查询单本图书（Redis 缓存支持）
新增图书
修改图书信息
删除图书
按书名模糊搜索

性能优化
引入 Redis 缓存后，接口响应时间从 1.15s 降至 24ms
使用 @Cacheable 缓存查询结果，@CacheEvict 保证缓存与数据库一致性

项目结构
src/main/java/com/example/demo/
├── controller/ 控制层，处理 HTTP 请求
├── service/ 业务层，实现核心业务逻辑
├── mapper/ 数据访问层，MyBatis Mapper 接口
├── entity/ 实体类
└── Result/ 统一返回格式封装

本地运行
克隆项目到本地

配置 MySQL 和 Redis 服务

修改 application.properties 中的数据库连接信息

执行 mvn clean package 打包

运行 java -jar target/demo-0.0.1-SNAPSHOT.jar

接口示例
GET /book/list 查询所有图书
GET /book/{id} 根据 ID 查询图书
POST /book/add 新增图书
PUT /book/update 修改图书
DELETE /book/{id} 删除图书
GET /book/search?title=xxx 按书名模糊搜索

部署信息
服务器：阿里云轻量应用服务器
操作系统：Ubuntu 22.04
部署方式：JAR 包独立运行
访问端口：8080

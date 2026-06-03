# 图书管理系统 (Library Management System)

基于 Spring Boot + MyBatis + MySQL 开发的图书管理后端系统，提供完整的图书信息增删改查 API。

## 技术栈

- **后端框架**: Spring Boot 2.7.x
- **持久层框架**: MyBatis
- **数据库**: MySQL
- **构建工具**: Maven
- **API 测试**: Postman

## 主要功能

- 查询所有图书
- 根据 ID 查询单本图书
- 新增图书
- 修改图书信息
- 删除图书
- 按书名模糊搜索

## 项目结构
src/main/java/com/example/demo/
├── controller/ # 控制器层，处理HTTP请求
├── service/ # 业务逻辑层
├── mapper/ # 数据访问层，MyBatis Mapper接口
├── entity/ # 实体类
└── common/ # 通用工具类（如统一返回结果 Result）


## 如何运行

1.  **创建数据库**：在 MySQL 中执行 `schema.sql` 文件创建 `library_db` 数据库及相关表。
2.  **修改配置**：配置 `src/main/resources/application.properties` 中的数据库连接信息。
3.  **启动项目**：运行 `DemoApplication.java` 中的 `main` 方法。
4.  **测试接口**：使用 Postman 导入 API 集合进行测试。

## 核心 API 示例

| 功能 | 请求方式 | URL | 请求体示例 |
|---|---|---|---|
| 查询所有图书 | GET | `/book/list` | 无 |
| 根据ID查询 | GET | `/book/{id}` | 无 |
| 新增图书 | POST | `/book/add` | `{"title":"Spring Boot实战", "author":"Craig Walls", "price":79.9, "stock":10}` |
| 修改图书 | PUT | `/book/update` | `{"id":1, "title":"Java编程思想", "price":99.9}` |
| 删除图书 | DELETE | `/book/{id}` | 无 |
| 模糊搜索 | GET | `/book/search?title=Java` | 无 |

# 图书管理系统（Library Management System）

基于 Spring Boot + MyBatis + MySQL + Redis 的图书管理后端系统，提供完整的图书信息增删改查 API，支持按书名模糊搜索，集成 Redis 缓存优化查询性能，具备全局异常处理与参数校验能力。

**项目已部署至阿里云服务器，可在线访问接口。**

**在线演示地址**：http://39.108.160.176:8080/book/1

**返回示例**：
```json
{"code":200,"message":"success","data":[{"id":1,"title":"测试书","author":"测试作者","price":39.9,"stock":10}]}
```

## 技术栈

- **后端框架**: Spring Boot 3.x / Java 17
- **持久层框架**: MyBatis
- **数据库**: MySQL 5.7
- **缓存中间件**: Redis
- **构建工具**: Maven
- **参数校验**: Spring Validation (JSR 303)
- **API 测试**: Postman
- **版本控制**: Git
- **部署环境**: 阿里云服务器（Ubuntu）

## 主要功能

- 查询所有图书
- 根据 ID 查询单本图书（Redis 缓存支持）
- 新增图书（自动校验参数）
- 修改图书信息（自动校验参数）
- 删除图书
- 按书名模糊搜索

## 性能优化

- 引入 Redis 缓存后，接口响应时间从 1.15s 降至 24ms
- 使用 @Cacheable 缓存查询结果，@CacheEvict 保证缓存与数据库一致性

## v1.2 更新内容

1. **全局异常处理**：基于 `@RestControllerAdvice` + `@ExceptionHandler` 实现统一异常捕获，避免将错误堆栈直接暴露给前端
2. **自定义业务异常**：新增 `BusinessException`，支持在业务逻辑中主动抛出携带错误码的异常
3. **参数校验**：基于 JSR 303 注解（`@NotBlank`、`@NotNull`、`@Min`、`@DecimalMin`）实现字段级校验
4. **校验分组**：通过 `AddGroup` / `UpdateGroup` 区分新增和更新场景的校验规则（新增不校验 id，更新必须传 id）
5. **数据库自增主键**：insert 语句不再插入 id，由数据库 AUTO_INCREMENT 生成

## 项目结构

```
src/main/java/com/example/demo/
├── controller/     # 控制器层，处理HTTP请求
├── service/        # 业务逻辑层
├── mapper/         # 数据访问层，MyBatis Mapper接口
├── entity/         # 实体类（含校验注解）
├── exception/      # 全局异常处理器 + 自定义业务异常
├── validation/     # 校验分组（AddGroup / UpdateGroup）
├── Result/         # 统一返回结果
└── DemoApplication.java
```

## 如何运行

1. 克隆项目到本地
2. 配置 MySQL 和 Redis 服务
3. 修改 `application.properties` 中的数据库连接信息
4. 运行 `DemoApplication.java` 中的 `main` 方法
5. 使用 Postman 测试 API

## 核心 API 示例

| 功能 | 请求方式 | URL | 请求体示例 |
|---|---|---|---|
| 查询所有图书 | GET | `/book/list` | 无 |
| 根据ID查询 | GET | `/book/{id}` | 无 |
| 新增图书 | POST | `/book/add` | `{"title":"Spring Boot实战", "author":"Craig Walls", "price":79.9, "stock":10}` |
| 修改图书 | PUT | `/book/update` | `{"id":1, "title":"Java编程思想", "price":99.9}` |
| 删除图书 | DELETE | `/book/{id}` | 无 |
| 模糊搜索 | GET | `/book/search?title=Java` | 无 |

## 部署信息

- 服务器：阿里云轻量应用服务器
- 操作系统：Ubuntu 22.04
- 部署方式：JAR 包独立运行
- 访问端口：8080

## 版本历史

- **v1.0** - 基础 CRUD 功能
- **v1.1** - 修复 SQL 语法错误、类型不匹配等问题，集成 Redis 缓存
- **v1.2** - 新增全局异常处理、参数校验、校验分组

## License

MIT

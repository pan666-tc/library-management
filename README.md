# 图书管理系统（Library Management System）

基于 Spring Boot + MyBatis + MySQL + Redis 的图书管理后端系统，提供完整的图书信息增删改查 API，支持读者借书/还书多表事务操作、按书名模糊搜索、集成 Redis 缓存优化查询性能，具备全局异常处理与参数校验能力，基于 Spring Security + JWT 实现用户认证与接口权限控制。

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
- **分页插件**: PageHelper
- **安全认证**: Spring Security + JWT
- **API 测试**: Postman
- **版本控制**: Git
- **部署环境**: 阿里云服务器（Ubuntu）

## 主要功能

- 查询所有图书（支持分页）
- 根据 ID 查询单本图书（Redis 缓存支持）
- 新增图书（自动校验参数）
- 修改图书信息（自动校验参数）
- 删除图书
- 按书名模糊搜索
- 读者管理（新增读者、查询读者）
- 借书（多表事务：扣库存 + 插借阅记录）
- 还书（多表事务：更状态 + 加库存）
- 借阅历史查询（支持分页）
- 用户注册（BCrypt 密码加密）
- 用户登录（JWT token 认证）
- 接口权限控制（未登录返回 401）

## 性能优化

- 引入 Redis 缓存后，接口响应时间从 1.15s 降至 24ms
- 使用 @Cacheable 缓存查询结果，@CacheEvict 保证缓存与数据库一致性
- 采用 Cache-Aside Pattern（旁路缓存模式），更新时先更数据库再删缓存

## v1.6 更新内容

1. **Redis 缓存完善**：为图书列表分页查询、单本查询、模糊搜索全部添加 `@Cacheable` 缓存
2. **缓存一致性**：新增/修改/删除图书时用 `@CacheEvict(allEntries=true)` 清除所有相关缓存，保证数据一致
3. **缓存配置**：新增 `CacheConfig`，设置缓存默认过期时间 30 分钟（TTL）
4. **Cache-Aside Pattern**：查询先查缓存再查数据库，写操作先更数据库再删缓存

## v1.5 更新内容

1. **Spring Security + JWT 认证**：集成 Spring Security，实现基于 JWT 的无状态身份认证，前后端分离架构
2. **用户注册与登录**：新增 `AuthController` 提供注册/登录接口，密码使用 BCrypt 加盐加密存储
3. **JWT 过滤器**：自定义 `JwtAuthenticationFilter`，拦截请求从 Header 提取 token，验证后存入 SecurityContext
4. **接口权限控制**：`SecurityConfig` 配置登录/注册接口放行（permitAll），其他接口需认证（authenticated），未认证返回 401 JSON
5. **身份自动获取**：借书/还书/借阅历史接口不再需要前端传 userId，从 SecurityContext 自动提取当前登录用户

## v1.4 更新内容

1. **分页查询**：集成 PageHelper 分页插件，实现图书列表和借阅历史的分页查询
2. **PageHelper 拦截器机制**：基于 MyBatis 拦截器自动改写 SQL（追加 LIMIT），并额外执行 COUNT 查询统计总数
3. **PageInfo 分页封装**：返回 `PageInfo` 对象，包含 total、pages、hasNextPage 等前端分页组件所需的全部信息
4. **驼峰映射**：开启 `map-underscore-to-camel-case=true`，自动将数据库下划线命名（`user_id`）映射为 Java 驼峰命名（`userId`）
5. **借阅历史接口**：新增 `GET /borrow/history?userId=xx&pageNum=1&pageSize=10`，按借书时间倒序分页展示读者借阅记录

## v1.3 更新内容

1. **多表关联设计**：新增 `user`（读者）和 `borrow_record`（借阅记录）两张表，通过中间表实现读者-图书多对多关系
2. **事务管理**：基于 `@Transactional` 实现借书/还书多表操作的事务一致性，避免数据不一致（扣库存成功但记录未插入）
3. **SQL 原子扣库存**：使用 `update book set stock = stock - 1 where id = #{id} and stock > 0` 实现并发安全的库存扣减，防止超卖
4. **借阅状态管理**：借阅记录 status 字段（0=借阅中，1=已归还），支持查询未归还记录，防止重复还书
5. **借阅业务接口**：新增 `BorrowController` 提供借书、还书 API

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
│   ├── AuthController.java     # 注册/登录接口
│   ├── BorrowController.java   # 借书/还书接口
│   └── UserController.java     # 图书CRUD接口
├── service/        # 业务逻辑层
│   ├── AuthService.java        # 注册/登录业务
│   ├── BorrowService.java      # 借书/还书事务业务
│   └── BookService.java        # 图书业务
├── mapper/         # 数据访问层，MyBatis Mapper接口
│   ├── BorrowRecordMapper.java # 借阅记录CRUD
│   ├── BookMapper.java         # 图书CRUD
│   └── UserMapper.java         # 读者CRUD
├── entity/         # 实体类（含校验注解）
│   ├── BorrowRecord.java       # 借阅记录实体
│   ├── Book.java               # 图书实体
│   └── User.java               # 读者实体
├── security/       # 安全认证
│   ├── JwtUtil.java            # JWT token 工具类
│   ├── JwtAuthenticationFilter.java # JWT 过滤器
│   └── SecurityConfig.java     # Spring Security 配置
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
| 查询图书列表（分页） | GET | `/book/list?pageNum=1&pageSize=10` | 无 |
| 根据ID查询 | GET | `/book/{id}` | 无 |
| 新增图书 | POST | `/book/add` | `{"title":"Spring Boot实战", "author":"Craig Walls", "price":79.9, "stock":10}` |
| 修改图书 | PUT | `/book/update` | `{"id":1, "title":"Java编程思想", "price":99.9}` |
| 删除图书 | DELETE | `/book/{id}` | 无 |
| 模糊搜索 | GET | `/book/search?title=Java` | 无 |
| 借书 | POST | `/borrow/borrow?bookId=1` | 需登录（Header带token） |
| 还书 | POST | `/borrow/return?bookId=1` | 需登录（Header带token） |
| 借阅历史（分页） | GET | `/borrow/history?pageNum=1&pageSize=10` | 需登录（Header带token） |
| 用户注册 | POST | `/auth/register?username=xxx&password=xxx&phone=xxx` | 无 |
| 用户登录 | POST | `/auth/login?username=xxx&password=xxx` | 无 |

## 部署信息

- 服务器：阿里云轻量应用服务器
- 操作系统：Ubuntu 22.04
- 部署方式：JAR 包独立运行
- 访问端口：8080

## 版本历史

- **v1.0** - 基础 CRUD 功能
- **v1.1** - 修复 SQL 语法错误、类型不匹配等问题，集成 Redis 缓存
- **v1.2** - 新增全局异常处理、参数校验、校验分组
- **v1.3** - 新增多表关联（读者/借阅记录）、事务管理、借书还书业务
- **v1.4** - 新增 PageHelper 分页查询、驼峰映射配置
- **v1.5** - 新增 Spring Security + JWT 认证、用户注册登录、接口权限控制
- **v1.6** - 完善 Redis 缓存：全查询缓存、写操作清缓存、Cache-Aside Pattern、TTL 过期配置

## License

MIT

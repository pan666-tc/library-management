# 图书管理系统（Library Management System）

基于 **Spring Boot + Vue3** 的前后端分离图书管理系统，后端使用 Spring Boot + MyBatis + MySQL + Redis + Spring Security + JWT，前端使用 Vue3 + Vite + Element Plus + Axios + Vue Router。提供完整的图书增删改查、借书还书事务操作、用户认证鉴权、Redis 缓存加速等功能。

**在线演示**：
- 🌐 前端页面：http://39.108.160.176:8080/ （注册账号即可登录）
- 📖 API 文档：http://39.108.160.176:8080/swagger-ui/index.html

## 技术栈

### 后端
- Spring Boot 3.x / Java 17
- MyBatis
- MySQL 5.7 / Redis
- Spring Security + JWT
- PageHelper（分页）
- SpringDoc OpenAPI（API 文档）

### 前端
- Vue 3 + Vite 5.x
- Element Plus（UI 组件库）
- Axios（HTTP 客户端）
- Vue Router 4（路由 + 路由守卫）

## 项目结构

```
图书管理项目/
├── src/                    # Spring Boot 后端
│   └── main/java/com/example/demo/
│       ├── controller/         # 控制层
│       │   ├── AuthController.java     # 注册/登录
│       │   ├── BorrowController.java   # 借书/还书/历史
│       │   └── UserController.java     # 图书 CRUD
│       ├── service/            # 业务层（含 @Transactional）
│       ├── mapper/             # MyBatis Mapper
│       ├── entity/             # 实体类
│       ├── security/           # Spring Security + JWT
│       ├── exception/          # 全局异常处理
│       ├── config/             # Redis + Security 配置
│       └── DemoApplication.java
├── frontend/               # Vue3 前端
│   ├── src/
│   │   ├── api/            # 后端接口封装
│   │   │   ├── request.js       # Axios 实例（拦截器 + token）
│   │   │   ├── auth.js          # 登录/注册
│   │   │   ├── book.js          # 图书列表/详情/搜索
│   │   │   └── borrow.js        # 借书/还书/历史
│   │   ├── router/         # Vue Router + 路由守卫
│   │   ├── views/          # 页面组件
│   │   │   ├── Login.vue        # 登录
│   │   │   ├── Register.vue      # 注册
│   │   │   ├── Layout.vue       # 带导航的主布局
│   │   │   ├── BookList.vue     # 图书列表（分页）
│   │   │   ├── BookDetail.vue   # 图书详情
│   │   │   ├── BookSearch.vue   # 按书名搜索
│   │   │   └── BorrowHistory.vue # 借阅历史
│   │   ├── App.vue
│   │   └── main.js
│   ├── vite.config.js      # 含 proxy 代理 /api → 后端8080
│   └── package.json
└── pom.xml
```

## 前后端分离架构

```
浏览器 (localhost:5173)
    │
    │  HTTP 请求（Axios 自动带 JWT）
    ↓
Vite Dev Server
    │  /api/* 代理转发
    ↓
Spring Boot (localhost:8080)
    │
    ├── JwtAuthenticationFilter  ← 解析 token、鉴权
    ├── Controller                ← 接收请求
    ├── Service + @Transactional  ← 业务逻辑 + 事务
    ├── @Cacheable/@CacheEvict    ← Redis 缓存
    └── MyBatis + MySQL           ← 持久化
```

**Vite Proxy 配置**（开发环境解决跨域）：
```js
proxy: {
  '/api': {
    target: 'http://localhost:8080',
    changeOrigin: true,
    rewrite: (path) => path.replace(/^\/api/, '')
  }
}
```

## 主要功能

- ✅ 用户注册（BCrypt 加盐加密）
- ✅ 用户登录（JWT token 认证，24h 有效）
- ✅ 路由守卫（未登录自动跳登录页）
- ✅ 图书列表（分页 + Redis 缓存）
- ✅ 图书详情（支持借书/还书）
- ✅ 图书搜索（按书名模糊匹配）
- ✅ 借书/还书（@Transactional 保证事务原子性）
- ✅ 并发安全（SQL 原子扣库存 `WHERE stock > 0` 防超卖）
- ✅ 借阅历史（分页展示）
- ✅ 全局异常处理（@RestControllerAdvice）
- ✅ 参数校验（JSR 303 注解）
- ✅ API 文档（SpringDoc OpenAPI）

## 快速开始

### 后端启动

```bash
# 1. 确保 MySQL 和 Redis 已运行
# 2. 修改 application.properties 中的数据库/Redis 连接信息
# 3. 运行 DemoApplication.java 的 main 方法
# 4. 后端启动在 http://localhost:8080
```

### 前端启动

```bash
cd frontend
npm install
npm run dev
# 前端启动在 http://localhost:5173
```

### 开发环境前提

- **Node.js** v20+ （zip 解压版，已安装在 D:\nodejs）
- **Java** 17+
- **MySQL** 5.7+
- **Redis**

## 核心技术点（面试可讲）

| 技术点 | 对应模块 | 关键实现 |
|--------|---------|---------|
| Spring Security + JWT | 用户认证 | BCrypt 加密、JwtAuthenticationFilter 过滤器、SecurityContext |
| @Transactional | 借书还书事务 | 扣库存 + 插记录原子性、READ_COMMITTED 隔离级别 |
| SQL 原子操作 | 并发控制 | `UPDATE book SET stock = stock-1 WHERE id=? AND stock>0` |
| Cache-Aside Pattern | Redis 缓存 | @Cacheable 查缓存、@CacheEvict 写操作清缓存、TTL 30min |
| PageHelper | 分页查询 | MyBatis 拦截器改写 SQL LIMIT、PageInfo 封装 |
| Vite Proxy | 跨域解决 | 开发环境 /api → localhost:8080 |
| Axios 拦截器 | Token 管理 | 请求拦截加 Bearer token、响应拦截处理 401 跳登录 |
| Vue Router 守卫 | 前端权限 | 未登录用户自动跳 /login |

## 版本历史

- **v1.0** - 基础 CRUD
- **v1.2** - 全局异常处理 + 参数校验
- **v1.3** - 多表关联 + 事务管理 + 借书还书
- **v1.4** - PageHelper 分页
- **v1.5** - Spring Security + JWT 认证
- **v1.6** - Redis 缓存完善
- **v1.7** - SpringDoc API 文档
- **v2.0** - 🎉 **前后端分离架构**：新增 Vue3 + Vite + Element Plus 前端，完整 UI 界面

## License

MIT

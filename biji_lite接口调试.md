---
title: 默认模块
language_tabs:
  - shell: Shell
  - http: HTTP
  - javascript: JavaScript
  - ruby: Ruby
  - python: Python
  - php: PHP
  - java: Java
  - go: Go
toc_footers: []
includes: []
search: true
code_clipboard: true
highlight_theme: darkula
headingLevel: 2
generator: "@tarslib/widdershins v4.0.30"

---

# 默认模块

Base URLs:

* <a href="http://localhost:8080">开发环境: http://localhost:8080</a>

# Authentication

# 注册登录

## POST 注册

POST /api/user/register

> Body 请求参数

```json
{
    "username": "admin",
    "password": "admin123",
    "nickname": "管理员"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 是 |none|

> 返回示例

> 200 Response

```json
{"code":200,"message":"注册成功","data":null}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## POST 登录

POST /api/user/login

> Body 请求参数

```json
{
    "username": "admin",
    "password": "admin123"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 是 |none|

> 返回示例

> 200 Response

```json
{"code":200,"message":"登录成功","data":{"id":1,"username":"admin","password":null,"nickname":"管理员","avatar":null,"email":null,"createTime":"2026-06-21T02:49:55"}}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## GET 用户信息

GET /api/user/info

> 返回示例

> 200 Response

```json
{"code":200,"message":"操作成功","data":{"id":1,"username":"admin","password":null,"nickname":"管理员","avatar":null,"email":null,"createTime":"2026-06-21T02:49:55"}}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## GET id获取用户信息

GET /api/user/1

> 返回示例

> 200 Response

```json
{"code":200,"message":"操作成功","data":{"id":1,"username":"admin","password":null,"nickname":"管理员","avatar":null,"email":null,"createTime":"2026-06-21T02:49:55"}}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

# 分类模块

## POST 创建分类

POST /api/category

> Body 请求参数

```json
{
    "name": "Spring Boot"
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 是 |none|

> 返回示例

> 200 Response

```json
{"code":200,"message":"创建成功","data":1}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## GET 获取分类列表

GET /api/category/list

> 返回示例

> 200 Response

```json
{"code":200,"message":"操作成功","data":[{"id":2,"userId":1,"name":"Spring Boot","createTime":"2026-06-21T03:09:48"},{"id":1,"userId":1,"name":"Java学习","createTime":"2026-06-21T03:07:31"}]}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## DELETE 删除分类

DELETE /api/category/1

> 返回示例

> 200 Response

```json
{"code":200,"message":"删除成功","data":null}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

# 笔记模块

## POST 创建笔记

POST /api/note

> Body 请求参数

```json
{
    "title": "Spring Boot 入门笔记",
    "content": "Spring Boot 是一个基于 Spring 框架的快速开发框架...",
    "categoryId": 1,
    "isPublic": 1
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 是 |none|

> 返回示例

> 200 Response

```json
{"code":200,"message":"创建成功","data":1}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## GET  获取笔记列表

GET /api/note/list

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|page|query|string| 否 |none|
|size|query|string| 否 |none|

> 返回示例

> 200 Response

```json
{"code":200,"message":"操作成功","data":{"records":[{"id":1,"userId":1,"categoryId":1,"categoryName":null,"title":"Spring Boot 入门笔记","content":"Spring Boot 是一个基于 Spring 框架的快速开发框架...","isPublic":1,"viewCount":0,"likeCount":0,"createTime":"2026-06-21T03:11:56","updateTime":"2026-06-21T03:11:56"}],"total":0,"size":10,"current":1,"pages":0}}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## GET 获取笔记详情

GET /api/note/1

> 返回示例

> 200 Response

```json
{"code":200,"message":"操作成功","data":{"id":1,"userId":1,"categoryId":1,"categoryName":null,"title":"Spring Boot 入门笔记","content":"Spring Boot 是一个基于 Spring 框架的快速开发框架...","isPublic":1,"viewCount":0,"likeCount":0,"createTime":"2026-06-21T03:11:56","updateTime":"2026-06-21T03:11:56"}}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## PUT 更新笔记

PUT /api/note/1

> Body 请求参数

```json
{
    "id": 1,
    "title": "Spring Boot 进阶笔记",
    "content": "更新后的内容...",
    "categoryId": 1,
    "isPublic": 0
}
```

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|body|body|object| 是 |none|

> 返回示例

> 200 Response

```json
{"code":200,"message":"更新成功","data":null}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## DELETE 删除笔记

DELETE /api/note/1

> 返回示例

> 200 Response

```json
{"code":200,"message":"删除成功","data":null}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

# 广场模块

## GET 公开笔记列表

GET /api/square/list

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|page|query|string| 否 |none|
|size|query|string| 否 |none|

> 返回示例

> 200 Response

```json
{"code":200,"message":"操作成功","data":{"records":[{"id":2,"userId":1,"categoryId":1,"title":"Spring Boot 入门笔记","summary":"Spring Boot 是一个基于 Spring 框架的快速开发框架...","viewCount":0,"likeCount":0,"createTime":"2026-06-21T03:16:36","updateTime":"2026-06-21T03:16:36","authorName":"管理员","authorAvatar":null,"categoryName":null}],"total":0,"size":10,"current":1,"pages":0}}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## GET  搜索公开笔记

GET /api/square/search

### 请求参数

|名称|位置|类型|必选|说明|
|---|---|---|---|---|
|keyword|query|string| 否 |none|
|page|query|string| 否 |none|
|size|query|string| 否 |none|

> 返回示例

> 200 Response

```json
{"code":200,"message":"操作成功","data":{"records":[{"id":2,"userId":1,"categoryId":1,"title":"Spring Boot 入门笔记","summary":"Spring Boot 是一个基于 Spring 框架的快速开发框架...","viewCount":0,"likeCount":0,"createTime":"2026-06-21T03:16:36","updateTime":"2026-06-21T03:16:36","authorName":"管理员","authorAvatar":null,"categoryName":null}],"total":0,"size":10,"current":1,"pages":0}}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## GET  获取公开笔记详情

GET /api/square/2

> 返回示例

> 200 Response

```json
{"code":200,"message":"操作成功","data":{"id":2,"userId":1,"categoryId":1,"title":"Spring Boot 入门笔记","content":"Spring Boot 是一个基于 Spring 框架的快速开发框架...","viewCount":1,"likeCount":0,"createTime":"2026-06-21T03:16:36","updateTime":"2026-06-21T03:16:36","authorName":"管理员","authorAvatar":null,"categoryName":null}}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

# 点赞模块

## POST 点赞

POST /api/like/2

> 返回示例

> 200 Response

```json
{"code":200,"message":"点赞成功","data":null}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构

## DELETE 取消点赞

DELETE /api/like/2

> 返回示例

> 200 Response

```json
{"code":200,"message":"取消点赞成功","data":null}
```

### 返回结果

|状态码|状态码含义|说明|数据模型|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|none|Inline|

### 返回数据结构


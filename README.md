# 云 API 托管服务

需要启动 backend 下的服务和 interface 模拟接口服务

SDK 服务、GateWay 服务、Common 服务已打成 Jar 包引入

## 使用技术点

版本 Spring Boot 2.7.6 

- MySQL 保存项目数据
- MyBatis 数据访问层框架
- MyBatis Plus MyBatis增强版，提供基础的CRUD
- Spring Session Redis 实现分布式登录
- Apache Commons Lang3 工具类
- Lombok 注解
- Swagger + Knife4j 接口文档
- Spring Boot 提供项目处理器和调试工具
- Spring Cloud GateWay 引入的拦截器，实现用户鉴权、请求参数处理、日志获取

项目启动后 Swagger 地址：localhost:7529/api/doc.html 
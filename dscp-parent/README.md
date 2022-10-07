# dscp-parent

# 数字化营销 后端parent包
# 项目使用 openjdk11 ！！！


数字供应链管理平台分为PC端、管理后台。
项目结构
# 后端项目分为四个模块：
dscp-parent：parent主模块(其他模块基础模块)，包含common、entity、interface、mapper、service模块；

​	-->>dscp-common：基础模块，包含工具类，自定义异常等；

​	-->>dscp-entity：实体对象模块，枚举、包含对应数据库表对象的eo对象；
            -- dto    rest层交互对象类 包含请求(request)dto对 象，响应(response)dto对象
            -- entity eo类 数据库交互对象类
            -- enums  枚举对象类

​    -->>dscp- interface：调用第三方接口的模块(各种外部系统, redis, mongodb, rabbitMQ, 短信, 邮件, AWSS3等等)；
            -- redis
            -- mongodb
            -- rabbitMQ
            -- meilisearch
            -- 其他第三方接口

​    -->>dscp- mapper：编写mapper接口和mapper.xml模块

​       	base表示基础，biz表示业务, ref表示关联对象、sys表示系统基础

​    -->>dscp-service：业务层接口模块，包含service接口以及service接口实现类。

dscp-extApi：对外接口模块，rest包下开发Controller接口，继承BaseController，该接口用于外部第三方系统调用我们系统。

dscp-cms：管理后台以及PC端对接模块，rest包下开发Controller接口，继承BaseController。

dscp-app：APP端对接模块，rest包下开发Controller接口，继承BaseController。


# Controller 接口遵循 restful风格 继承 BaseController 返回 Message(status, content)类
BaseController类 提供从上下文中获取当前登录人userId, 当前登录绑定企业id 等方法

原则上 rest层中请求参数使用 **ReqDto  而返回参数使用 **RespDto
数据库交互则应使用对应entity类

entity类 创建应继承 BaseEntity (内含id, createTime等5个基础属性)(sql建表脚本也应包含此5个字段)
若涉及业务需要商家数据隔离 则应继承 MultitenantEntity类
同样DTO类 也有BaseDto与MultitenantDto类

枚举类 可以通过 @EnumValue 标记数据库存的值 具体可以参考 BaseUser 的 UserTypeEnum枚举


# 遇到业务逻辑错误时，可直接抛出 BizException 类型错误，或其他自定义错误 并在全局异常处理类 GlobalExceptionHandler中处理


# 加解密 && 脱敏
需要加密/脱敏的字段 添加注解 @FieldDES
加解密工具类：RSADESUtils (在Service层按需调用)
脱敏工具类: DataMaskingUtils 
(需要脱敏列表/单体数据在Controller层 通过调用 return MessageForDMData(obj) 统一处理)
可以参考 BaseUserController  BaseUserServiceImpl   BaseUserReqDto


# 本地接口调试
dev/test环境 可通过修改 yml配置文件参数 authentication.authority 为false 跳过登录与访问鉴权判断


# 集成了 Liquibase 数据库版本更改管理
spring.liquibase 配置 test/dev环境是false   prod环境是true

db/changelog-master.xml -- 主变更版本配置 (用于引入子版本)
db/sql/** -- 子版本变更内容sql

sql脚本文件命名规则 vyyyymmdd_当天发版次数_说明.sql
发版完成后 历史脚本sql不能修改和删除！！！


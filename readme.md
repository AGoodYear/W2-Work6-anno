# FruityLimiter

一个简单的限流注解中间件，使用于Springboot3

## 使用

在需要限流的接口上添加@Limiter注解

## 属性

* 标识前缀key

* 频控时间范围

* 频控时间单位

* 单位频控时间范围内最大访问次数

## application.properties

```properties
flimiter.method = 1  (切换算法，未完成的功能)
flimiter.defaultTime = 5000 （默认窗口大小）
flimiter.defaultNum = 10 （默认允许通过的请求）
flimiter.errorException = Please try again later! （限流时返回的信息）
```
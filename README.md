# 1. 简介
* http接口自动化工具
* 纯yaml驱动

## Getting started

### 2. 核心文件说明

#### 2.1 总控workflow文件: [example.workflow.yaml](./src/test/resources/workflow/example.workflow.yaml)

* 测试执行的总控文件,标识了执行的脚本内容与顺序,执行标签

```
name: example #总控,需要对应有文件夹包含测试文件
steps:  #具体的测试步骤
  - test: case1-http.yaml # http接口请求描述文件
  - test: case2-http.yaml # http接口请求描述文件
tags:
  - example # 标记,用来控制运行哪些总控
desc: workflow example
```

#### 1.2 mtop脚本文件: [case1-http.yaml](./src/test/resources/workflow/example/case1-http.yaml)

* 描述了具体要执行的http请求、断言、提参等信息

```
request:
  path: /query # 请求路径
  method: GET  #接口的方法
  params: # 参数
    type: shunfeng
    postid: 11111111111
  body: '{ "offset": 1,"limit": 10 }' # body内容,需要用单引号标实为一个字符串
  headers: # 请求头
    contentType: application/json
validation: # 断言信息
  expressions:
    - path: $.data.data[0].code  # json path表达式
      value: ok  # 预期值
responseParam:
  expressions:
    - path: $.data.data[0].code  # json path表达式
      value: postid  # 取值后存在hashMap中,key的名字
waitTime: 3 # 接口执行后等待时间
desc: check post id
```

### 3. 运行命令

```
mvn clean test -Dtag=example
```

或者在IDEA中添加参数
```
VM OPTION = -ea -Dtag=example
```


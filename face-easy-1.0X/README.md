<p align="center">
  <a href="https://gitee.com/susantyp/face-easy">
    <img src="https://img-blog.csdnimg.cn/5776aada5ab946b384c92e8adb69892d.png#pic_center?x-oss-process=" width="81" alt="FACE-UI">
  </a>
</p>
<h1 align="center">FACE-UI</h1>

FACE-UI 基于前后端分离Web端项目，主要实现了网页版的人脸登录，通过调取前端摄像头拍照，传入后台进行跟数据库人脸库的相似度比对，技术的用点：Springboot，Mysql，JWT，VUE 2.X 等等技术实现，主要功能点：人脸列表CRUD，日志列表CRUD，基于自建人脸库通过base64编码方式存储人脸图片，通过调用腾讯云人脸对比API场景实现

### 工程介绍

#### 1，face-easy (后端springboot工程)

项目地址：[susantyp/face-easy (gitee.com)](https://gitee.com/susantyp/face-easy)

拉取相关代码

在工程sql文件夹下面，有基于mysql的sql脚本

在application.yml 文件中配置 腾讯云的secretId和secretKey

然后还需要选购一下腾讯云人脸对比api

#### 2，face-ui(前端VUE 2.X 工程)

项目地址：[susantyp/face-ui (gitee.com)](https://gitee.com/susantyp/face-ui)

拉取相关代码

npm install  安装相关依赖

npm run serve 运行服务

刚刚开始进入浏览器，需要授权浏览器摄像头权限，点击同意即可

### 怎么配置腾讯云？

详细文档：https://blog.csdn.net/Susan003/article/details/125914027?spm=1001.2014.3001.5502
<!-- 本示例未包含完整css，获取外链css请参考上文，在hello uni-app项目中查看 -->
<template>
  <view class="body">
    <view class="container">
      <form @submit="formSubmit" @reset="formReset">
        <view class="uni-form-item uni-column">
          <view class="title" style="color: #efefef;">工号</view>
          <input class="uni-input" style="        color: #efefef;" name="tno" placeholder="请输入您的工号" />
        </view>
        <view class="uni-form-item uni-column">
          <view class="title" style="color: #efefef;">密码</view>
          <input class="uni-input" name="password" style="        color: #efefef;" placeholder="请输入您的密码" type="password" />
        </view>
        <view class="uni-form-item uni-column">
          <view class="title" style="color: #efefef;">姓名</view>
          <input class="uni-input" name="name" placeholder="请输入您的姓名" style="        color: #efefef;" />
        </view>
        <view class="uni-form-item uni-column">
          <view class="title" style="color: #efefef;">年龄</view>
          <input class="uni-input" name="age" placeholder="请输入您的年龄" style="        color: #efefef;" />
        </view>
        <button form-type="submit" type="default" class="bottom-button" style="    background-color: #8470FF; color: #efefef;">注册</button>
      </form>
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {}
    },
    methods: {
      formSubmit: function(e) {
        console.log('form发生了submit事件，携带数据为：' + JSON.stringify(e.detail.value))
        var formdata = e.detail.value
        uni.request({
          url: 'http://43.143.44.118:8888/registerandcontroller/teacherregister',
          method: 'POST',
          data: {
            tno: parseInt(e.detail.value.tno),
            password: e.detail.value.password,
            teaName: e.detail.value.name,
            teaAge: e.detail.value.age
          },
          success(res) {
       
              uni.showToast({
                title: '信息已提交,请等待审批！',
                duration: 2500,
                icon: 'none',
              })
      
            uni.navigateTo({
              url:'../../pages/index/index'
            })

          }
        })
      },
    }
  }
</script>

<style lang="scss">
  .title {
    font-family: "楷体";
    color: #efefef;
  }
  .uni-form-item .title {
    padding: 20rpx 0;
  }

  .body {
    background-image: url("https://pic3.zhimg.com/v2-2cf0b25bf0036cbacc286ac9b6489ffe_r.jpg");
    background-size: 100% 100%;
    background-attachment: fixed;
  }

  .bottom-button {background-color: #aa55ff;}

  input {
    height: 76px;
  }

  .title {
    text-align: center;
    color: #000000;
    font-size: 20px;
    height: 88rpx;
  }
  .bottom-button {
    background-color: #8470FF;
    border-radius: 20px;
  
  }
</style>

<!-- 本示例未包含完整css，获取外链css请参考上文，在hello uni-app项目中查看 -->
<template>
  <view class="body">
    <view>
      <form @submit="formSubmit" @reset="formReset">
        <view class="uni-form-item uni-column">
          <view class="title" style="        color: #efefef;">学号</view>
          <input class="uni-input" style="color: #efefef;" name="sno" placeholder="请输入你的学号" />
        </view>
        <view class="uni-form-item uni-column">
          <view class="title" style="        color: #efefef;">密码</view>
          <input class="uni-input" style="color: #efefef;" name="password" placeholder="请输入你的密码" type="password" />
        </view>
        <view class="uni-form-item uni-column">
          <view class="title" style="        color: #efefef;">姓名</view>
          <input class="uni-input" style="        color: #efefef;" name="name" placeholder="请输入你的姓名" />
        </view>
        <view class="uni-form-item uni-column">
          <view class="title" style="        color: #efefef;">性别</view>
          <radio-group name="gender">
            <label>
              <radio value="男" /><text>男</text>
            </label>
            <label>
              <radio value="女" /><text>女</text>
            </label>
          </radio-group>
        </view>
        <view class="uni-form-item uni-column">
          <view class="title" style="        color: #efefef;">年龄</view>
          <input class="uni-input" style="        color: #efefef;" name="age" placeholder="请输入你的年龄" />
        </view>
        <button form-type="submit" type="default" class="bottom-button"
          style="background-color: #aa55ff ;    color: #efefef;">注册</button>
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
          url: 'http://43.143.44.118:8888/registerandcontroller/register',
          method: 'POST',
          data: {
            sno: parseInt(e.detail.value.sno),
            password: e.detail.value.password,
            name: e.detail.value.name,
            gender: e.detail.value.gender,
            age: e.detail.value.age
          },
          success(res) {
            uni.showToast({
              title: '成功注册，请登录！',
              duration: 1500,
              icon: 'none',
            })
            uni.navigateTo({
              url: '../../pages/index/index'
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
    font-size: 45px;

  }

  .uni-form-item .title {
    padding: 20rpx 0;
  }

  .body {
    background-image: url("https://pic3.zhimg.com/v2-2cf0b25bf0036cbacc286ac9b6489ffe_r.jpg");
    background-size: 100% 100%;
    background-attachment: fixed;
  }

  .bottom-button {
    background-color: #8470FF;
    border-radius: 20px;

  }

  input {
    height: 80px;
  }

  .title {
    text-align: center;
    color: #000000;
    font-size: 20px;
  }
</style>

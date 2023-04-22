<template>
  <view class="body" style="background-image: url('https://up.6188.me/s/pm/98/41/5d/f9e4b4ffcc8709320d2bf090285d4198.jpeg'); height: 100vh;    background-size: 100% 100% ;
    background-attachment: fixed;">
    <!-- 用户登录区域 -->
    <form @submit="formSubmit" class="my-from">
      <view class="login">
        <view class="uni-form-item ">
          <view class="title">管理员账号</view>
          <input class="uni-input" name="input" placeholder="请输入管理员账号" />
        </view>
        <view class="uni-form-item">
          <view class="title">密码</view>
          <input class="uni-input" name="password" type="password" placeholder="请输入密码" />
        </view>
        <view class="login-button">

          <!--            <navigator url="../../subpkg/register/register" class="register">
                              </navigator> -->
<!--          <button size="mini" type="primary" @click="turntoregister()">
            注册
          </button> -->

          <button size="mini" type="primary" form-type="submit">登录</button>

        </view>


      </view>
    </form>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        
      }
    },
    methods: {
      formSubmit: function(e) {
        console.log(e.detail.value)
        console.log('form发生了submit事件，携带数据为：' + JSON.stringify(e.detail.value))
        var formdata = e.detail.value
        uni.request({
          url: 'http://43.143.44.118:8888/admin/adminlogin',
          method: 'POST',
          data: {
            name: e.detail.value.input,
            password: e.detail.value.password
          },
          success(res) {
            //成功请求数据后的操作
            console.log(res)
            if (res.data.code != 200) {
              return uni.showToast({
                title: '账号或密码错误！',
                duration: 1500,
                icon: 'none',
              })
            }else{
              uni.showToast({
                title: '登陆成功！',
                duration: 1500,
                icon: 'none',
              })
              // 登录成功就要完成导航的跳转了,后面要拼上老师的参数
              uni.navigateTo({
                url:'../../subpkg/admin-system/admin-system'
              })
            }
          }
        })
      },
    }
  }
</script>

<style lang="scss">
  swiper {
    height: 400rpx;

    .swiper-item,
    image {
      width: 100%;
      height: 100%;
    }
  }

  .body {
    display: flex;
    flex-direction: column;
    justify-content: space-around;
  }

  .login {
    border-radius: 25px;
    background-color: #f1edff;
    margin: 50rpx;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    border: 1px solid #efe3eb;
    box-shadow: 6px 6px #efe3eb;
    height: 400rpx;
    opacity: 60%;
  }

  .login-button {
    display: flex;
    flex-flow: row nowrap;
    justify-content: space-around;
  }

  .uni-form-item {
    padding-left: 20rpx;
  }
</style>

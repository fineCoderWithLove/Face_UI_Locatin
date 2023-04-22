<template>
  <view class="body" style="background-image: url('https://bkimg.cdn.bcebos.com/pic/023b5bb5c9ea15ce05f3c82bbe003af33a87b2f6'); height: 100vh;    background-size: 100% 100% ;
    background-attachment: fixed;">
    <!-- 用户登录区域 -->
    <form @submit="formSubmit" class="my-from">
      <view class="login">
        <view class="uni-form-item ">
          <view class="title">工号</view>
          <input class="uni-input" name="input" placeholder="请输入工号" />
        </view>
        <view class="uni-form-item">
          <view class="title">密码</view>
          <input class="uni-input" name="password" type="password" placeholder="请输入密码" />
        </view>
        <view class="login-button">

          <!--            <navigator url="../../subpkg/register/register" class="register">
                              </navigator> -->
          <button size="mini" type="primary" @click="turntoregister()">
            注册
          </button>

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
        swiperList: [
          "https://ts1.cn.mm.bing.net/th/id/R-C.b1a2a1b1f5282f55e9132a5a9f6906b2?rik=nUfm5hooZiIKCg&riu=http%3a%2f%2fwww.jdxzz.com%2fuploads%2fallimg%2f190921%2f4-1Z921111123.jpg&ehk=%2bJ5%2frU3gWkSP5Dw2UjgdaL9CdvtCQOJtCIlCtHJ%2bwx8%3d&risl=&pid=ImgRaw&r=0",
          "https://ts1.cn.mm.bing.net/th/id/R-C.dc5fccbb1b682aed4964515d0b4b93c4?rik=f5I0UMmlw9R3nA&riu=http%3a%2f%2fwww.liuxuehr.com%2fuploads%2fallimg%2f190507%2f1_1726097973.jpg&ehk=PQ0OcTeaQB17NqODgHIQLY8tqVoLAGJgcAgR8eT%2f4BY%3d&risl=&pid=ImgRaw&r=0",
          "https://ts1.cn.mm.bing.net/th/id/R-C.8f9e9c759de32359f25c08d4fe816690?rik=8sVuIbrllOpuKg&riu=http%3a%2f%2fwww.liuxuehr.com%2fuploads%2fallimg%2f190507%2f1_1726094092.jpg&ehk=VIf65V73pS2eCPJBOI0N3gBT82joV385dPct1Qe52wo%3d&risl=&pid=ImgRaw&r=0"
        ],
      }
    },
    methods: {
      turntoregister(){
        console.log("点击了注册");
        uni.navigateTo({
          url:'../../subpkg/teacher-register/teacher-register'
        })
      },
      formSubmit: function(e) {
        console.log(e.detail.value)
        console.log('form发生了submit事件，携带数据为：' + JSON.stringify(e.detail.value))
        var formdata = e.detail.value
        uni.request({
          url: 'http://43.143.44.118:8888/teacher/teacherlogin',
          method: 'POST',
          data: {
            tno: parseInt(e.detail.value.input),
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
            } else {
              uni.showToast({
                title: '登陆成功！',
                duration: 1500,
                icon: 'none',
              })
              // 登录成功就要完成导航的跳转了,后面要拼上老师的参数
              uni.navigateTo({
                url: '../../subpkg/admin-teacher/admin-teacher?tno=' + res.data.data.tno
              })
            }
          },
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
    border-radius: 40px;
    background-color: #b5bdff;
    margin: 90rpx;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    border: 1px solid #81c9ff;
    box-shadow: 6px 6px #6A5ACD;
    height: 400rpx;
    opacity: 70%;
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

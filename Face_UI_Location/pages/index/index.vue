<template>
  <view style="    background-image: url('https://pic3.zhimg.com/v2-385a6b12ae13c4316468f206ac01f245_r.jpg');background-size: 100% 100% ;
    background-attachment: fixed;">
    <view style="height: 100vh;">
      <!-- 轮播图区域 -->
      <swiper :indicator-dots="true" :autoplay="true" :interval="3000" :duration="1000" :circular="true">
        <!-- 循环渲染轮播图的 item 项 -->
        <swiper-item v-for="item in swiperList" :key="i">
          <view class="swiper-item">
            <!-- 动态绑定图片的 src 属性 -->
            <image :src="item"></image>
          </view>
        </swiper-item>
      </swiper>
      <!-- 用户登录区域 -->
      <form @submit="formSubmit" class="my-from">
        <view class="login">
          <view class="uni-form-item ">
            <view class="title">学号</view>
            <input class="uni-input" name="input" placeholder="请输入学号" />
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
  </view>

</template>

<script>
  export default {
    data() {
      return {
        title: 'Hello',
        swiperList: [
          "https://ts1.cn.mm.bing.net/th/id/R-C.b1a2a1b1f5282f55e9132a5a9f6906b2?rik=nUfm5hooZiIKCg&riu=http%3a%2f%2fwww.jdxzz.com%2fuploads%2fallimg%2f190921%2f4-1Z921111123.jpg&ehk=%2bJ5%2frU3gWkSP5Dw2UjgdaL9CdvtCQOJtCIlCtHJ%2bwx8%3d&risl=&pid=ImgRaw&r=0",
         "https://5b0988e595225.cdn.sohucs.com/images/20171001/47152bd8c5aa41dd9716c492571f94ce.jpeg",
        "https://nimg.ws.126.net/?url=http%3A%2F%2Fdingyue.ws.126.net%2F2021%2F0808%2Fd67dd88bj00qxiikt0019c000hs00anm.jpg&thumbnail=660x2147483647&quality=80&type=jpg"
		],
        username: "asdasf",
        password: "123456",
        title: 'input',
        focus: false,
        inputValue: '',
        showClearIcon: false,
        inputClearValue: '',
        changeValue: '',
        showPassword: true
      }
    },
    onLoad() {

    },
    methods: {
      changePassword: function() {
        this.showPassword = !this.showPassword;
      },
      turntoregister() {
        console.log("点击到了")
        uni.navigateTo({
          //在此传递参数
          url: '../../subpkg/register/register'
        })
      },
       formSubmit:  function(e) {
        console.log(e.detail.value)
        console.log('form发生了submit事件，携带数据为：' + JSON.stringify(e.detail.value))
        var formdata = e.detail.value
        uni.request({
          url: 'http://43.143.44.118:8888/registerandcontroller/login',
          method: 'POST',
          data: {
            sno: parseInt(e.detail.value.input),
            password: e.detail.value.password
          },
          //成功请求数据后的操作
          success(res) {
            console.log("dengluy");
            if (res.data.code != 200) {
              return uni.showToast({
                title: '账号或密码错误！',
                duration: 1500,
                icon: 'none',
              })
            }
            //校验数据库成功之后跳转到个人信息页面
            else {
              console.log("登录成功！")
              uni.setStorage({
                key: "studentid",
                data: e.detail.value.input,
                success() {
                  console.log('成功存储到缓存')
                }
              })
              uni.navigateTo({
                //在此传递参数
                url: '../person-info/person-info?mysno=' + parseInt(e.detail.value.input)
              })
            }
          },fail(){
            
          }
        })

      },

    }
  }
</script>

<style lang="scss">
  * {
    font-family: "楷体";
  }
  swiper {
    height: 400rpx;

    // height: 100vh;
    .swiper-item,
    image {
      width: 100%;
      height: 100%;
    }
  }


  .login {
    font-size: 18px;
        font-family: "楷体";
    border-radius: 25px;
    background-color: #f1edff;
    margin: 50rpx;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    border: 1px solid #efe3eb;
    box-shadow: 6px 6px #efe3eb;
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

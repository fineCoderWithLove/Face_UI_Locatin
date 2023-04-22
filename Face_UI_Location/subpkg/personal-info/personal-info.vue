<template>
  <view class="body">
    <view class="tihuan"></view>
    <view class="personphoto"
      style="width: 200px; height: 200px; border-radius: 100%; padding-left: 23%; margin-bottom: 60rpx;">
      <image :src="src" style="width: 100%; height: 100%; border-radius: 100%;"></image>
    </view>

    <view class="my-info">
      <view class="sno">学号：{{sno}}</view>
      <view class="stu-name">姓名：{{stuName}}</view>
      <view class="stu-gender">性别：{{gender}}</view>
      <view class="stu-stuage">年龄：{{stuAge}}</view>
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        sno: 0,
        src: '',
        gender: '',
        stuAge: 0,
        stuName: ''
      };
    },
    onLoad(options) {
      this.sno = options.sno
      this.getPersonInfo()
    },
    methods: {
      async getPersonInfo() {
        const data = await uni.$http.get('/student/studetails?sno=' + this.sno)
        if (data.data.code === 200) {
          this.src = data.data.data.src
          this.gender = data.data.data.gender
          this.sno = data.data.data.sno
          this.stuAge = data.data.data.stuAge
          this.stuName = data.data.data.stuName
        }

        if (this.src === null) {
          uni.showToast({
            title: '请上传您的图像信息！',
            duration: 1500,
            icon: 'none',
          })
          setTimeout(() => {
            this.getYourImage()
          }, 1000)

        }
      },
      getYourImage() {
        let that = this
        console.log("进入了选图片")
        uni.chooseMedia({
          count: 1,
          mediaType: 'image',
          sourceType: 'camera',
          maxDuration: 30,
          camera: 'back',
          success(res) {
            // 在chooose的回调函数里面获取base64编码，获取成功以后再把base64编码进行上传，上传base和头像的url可以分布执行没有先后之分（因为是在两个表）
            console.log("是否获取图片成功");
            that.src = res.tempFiles[0].tempFilePath
            const fs = wx.getFileSystemManager()
            // 成功之后将数据的baseurl进行上传
            fs.readFile({
              filePath: that.src,
              encoding: 'base64',
              position: 0,
              success(res) {
                console.log(res.data)
                that.base = res.data
                uni.request({
                  url: 'http://43.143.44.118:8888/fileupload/base64',
                  method: 'POST',
                  data: {
                    imageBase: that.base,
                    name:that.stuName
                  },
                  success(res) {
                    console.log("success传递base64码")
                    console.log(res);
                  }
                })
              },
              fail(res) {
                console.error(res)
              }
            }),
            //上传头像的信息
            console.log("是否获取图片成功");
            let those = that
            that.src = res.tempFiles[0].tempFilePath
            uni.uploadFile({
              url: 'http://43.143.44.118:8888/fileupload/upload', //仅为示例，非真实的接口地址
              filePath: that.src,
              name: 'file',
              formData: {
                'user': 'test',
                sno: that.sno
              },
              success: (uploadFileRes) => {
                console.log("上传图片成功了");
              },
              fail() {
                console.log("上传图片失败了");
              },
              complete() {
                console.log("上传图片了");
              }
            })
          }
        })
      }
    }
  }
</script>

<style lang="scss">
  .tihuan {
    background-image: url('https://tse4-mm.cn.bing.net/th/id/OIP-C.BDtLxeuvHuL6h_BKH8_CwQHaNK?pid=ImgDet&rs=1');
    height: 40rpx;
    width: 100%;
    background-size: 100% 100%;
    background-attachment: fixed;
  }

  .checkdate,
  .checkinfo,
  .checklocation,
  .ischeck,
  .ischeckstring,
  .checkinfostring {
    color: #000000;
  }

  .fotter {
    margin-top: 60rpx;
  }

  .checkin {
    position: fixed;
    bottom: 0px;
    border: 2px solid #007AFF;
  }

  .my-info {
    color: #efefef;
    height: 600rpx;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
  }

  .sno,
  .stu-name,
  .stu-gender,
  .stu-name,
  .stu-stuage {
    display: flex;
    padding-left: 40rpx;
    background-image: url('https://tse4-mm.cn.bing.net/th/id/OIP-C.BDtLxeuvHuL6h_BKH8_CwQHaNK?pid=ImgDet&rs=1');
    background-size: 100% 100%;
    background-attachment: fixed;
    width: 80%;
    margin-left: 55rpx;
    height: 150rpx;
    line-height: 150rpx;
    border: 1px solid #2A3C70;

  }



  .hava-image {
    padding-right: 170px;
  }

  .example-body {
    padding: 10px;
    padding-top: 0;
  }

  .custom-image-box {
    /* #ifndef APP-NVUE */
    display: flex;
    /* #endif */
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
  }

  .text {
    font-size: 14px;
    color: #333;
  }

  .body {

    flex-direction: column;
    background-image: url('https://tse4-mm.cn.bing.net/th/id/OIP-C.BDtLxeuvHuL6h_BKH8_CwQHaNK?pid=ImgDet&rs=1');
    background-size: 100% 100%;
    background-attachment: fixed;
    width: 100%;
    height: 100vh;

    color: #EFEFEF;
  }

  .image {
    display: flex;
    justify-content: space-around;
    padding-left: 180rpx;
    height: 400rpx;
    background-image: url('https://tse4-mm.cn.bing.net/th/id/OIP-C.BDtLxeuvHuL6h_BKH8_CwQHaNK?pid=ImgDet&rs=1');
    width: 100%;
  }
</style>

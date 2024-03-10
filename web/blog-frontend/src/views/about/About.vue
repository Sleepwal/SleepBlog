<template>
  <div class="about-body slide-in-container">
    <div class="content"><h2>《关于我们》</h2></div>
    <div class="about-item-list parallax1 p1">
      <AboutItem v-for="item in userList"
                 :data="item"></AboutItem>
    </div>

    <div class="content"> <h2>《项目技术栈》</h2></div>
    <div class="parallax p2 rowCC">
      <ul class="parallax-content">
        <li>前端: Vue3 + Element Plus</li>
        <li>后端: Springboot + MyBatisPlus + SpringSecurity</li>
        <li>数据库: MySQL + Redis</li>
      </ul>
    </div>


    <div class="content"> <h2>《免责声明》</h2></div>
    <div class="parallax p3 rowCC">
      <div>
        <p class="parallax-content">
          本站的所有内容仅供参考和信息目的，并不构成任何法律、金融或专业建议。我们努力确保所提供的信息准确、全面和最新，但不能保证其准确性和完整性。
          对于因使用本站提供的信息而导致的任何损失或损害，包括但不限于直接、间接、特殊、偶然或结果性损失或损害，本站概不负责。
          用户应自行承担使用本站信息的风险，并在必要时寻求专业意见。
        </p>
      </div>

    </div>

  </div>
</template>

<script setup>
import {getAuthorList} from "@/api/user";
import {ref} from "vue";
import AboutItem from "@/views/about/com/AboutItem.vue";

const userList = ref([])
//获取用户（作者）列表
const loadUserList = async () => {
  userList.value = await getAuthorList()
}
loadUserList()

</script>

<style lang="scss">
.about-body {
  border-radius: 30px;

  .about-item-list {
    display: flex;
    flex-wrap: wrap;
    padding: 5px 10%;
  }
}

.parallax {
  width: 100%;
  height: 80vh;
  position: relative;
  overflow: hidden;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: top center;
  background-attachment: fixed;

  .parallax-content {
    background-color: var(--grey-1-a9);
    font-size: 18px;
    width: 60vw;
    border-radius: 10px;
    padding: 50px
  }
}
.parallax1 {
  width: 100%;
  position: relative;
  overflow: hidden;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: top center;
  background-attachment: fixed;
}
.content {
  width: 100%;
  height: 10vh;
  margin: 0 auto;
  text-align: justify;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  //background-color: black;
  background-color: var(--grey-6);
  color: var(--grey-3);
}

.p1 {
  background-image: url("https://ts1.cn.mm.bing.net/th/id/R-C.aac15df2528246b0794b422f5f588fd2?rik=jWQkodCBbbDx7A&riu=http%3a%2f%2fi.52desktop.cn%3a81%2fupimg%2fallimg%2f20110224%2f2011224145510844778018.jpg&ehk=BgvDnJlJ%2btOdePpnWjQwwwgmLWabOsoQFT6L0Qn1V00%3d&risl=&pid=ImgRaw&r=0");
}
.p2 {
  background-image: url("https://www.2008php.com/2011_Website_appreciate/11-02-28/20110228224514.jpg");
}
.p3 {
  background-image: url("https://pic1.zhimg.com/v2-e0ca937a1d3296e7463aa0aa096bef48_r.jpg");
}

</style>
<template>
  <div class="about-item" :id="data.userId">
    <el-card class="about-item-card">
      <div class="about-item-avatar">
        <el-image :src="data.avatar" v-if="data.avatar"/>
        <el-image v-else :src="imageList[Math.floor(Math.random() * (imageList.length - 1))]"/>
      </div>
      <div class="about-item-info">
        <div class="about-item-nick-name">
          昵称: {{data.nickname}} |
          性别: {{data.sex === '1' ? '男' : '女'}}
        </div>
        <div>
          共写博客：<span class="about-item-count">{{data.count}}</span>
        </div>
        <div class="introduction">
          <span v-html="marked(data.introduction)"></span>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup>

import {marked} from "marked";

const props = defineProps({
    data: {
      type: Object
    }
  })

  const imageList = [
    'https://pic3.zhimg.com/80/v2-e54cd5b1379ea09779d3863799143222_720w.webp',
    'https://pic4.zhimg.com/80/v2-4f0dcdc277a2c5638038a295da8d0163_720w.webp',
    'https://pic1.zhimg.com/80/v2-490796a9728cf9aa0e59eeca44bd92f8_720w.webp',
    'https://pic1.zhimg.com/80/v2-cb468542da9fe6f080fd5fdc3c93fabc_720w.webp',
    'https://pic4.zhimg.com/80/v2-f46fc70da2be9c6d469e417a2e7d3363_720w.webp',
    'https://pic3.zhimg.com/80/v2-6e09d61eb599c17faf1aab3c9a6bd272_720w.webp',
    'https://pic4.zhimg.com/80/v2-f8ecb9be33f2c02bcec57b6d8202adef_720w.webp',
    'https://pic3.zhimg.com/80/v2-924b620136f6289aadc8dff3d4c8251a_720w.webp',
    'https://pic1.zhimg.com/80/v2-0b7c6e6bcf82f36b63ad8edee19c48ac_720w.webp',
    'https://pic1.zhimg.com/80/v2-58818a983f4d4ea0f46468fd7178a860_720w.webp',
    'https://pic3.zhimg.com/80/v2-f5831c96080ae4cd2f38ac993c72f042_720w.webp',
    'https://pic3.zhimg.com/80/v2-531fe2c328a3116791a73c5b39cd87c2_720w.webp'
  ]
</script>

<style lang="scss">
  .about-item {
    font-size: 15px;
    margin-bottom: 30px;
    display: flex;

    .about-item-card {
      width: 25vw;
      margin-right: 10px;
      border-radius: 20px;
    }

    .about-item-avatar {
      width: 200px;
      //height: 200px;
      border-radius: 100px;
      overflow: hidden;
      margin-left: 10%;

      &:hover {
        transition: 1s;
        transform: rotate(360deg);
      }

      img {
        width: 100%;
      }
    }

    .about-item-info {
      flex: 1;
      margin-left: 20px;

      .about-item-nick-name {
        font-size: 21px;
        font-weight: bold;
      }
      .about-item-count {
        color: indianred;
        font-weight: bold;
        font-size: 18px;
      }
      .introduction {
        margin-top: 10px;
        font-size: 18px;
      }
    }
  }
</style>
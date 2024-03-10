<template>
  <MdEditor :modelValue="text" :theme="useSystemStore().getTheme()"/>
</template>

<script setup>
import { MdEditor } from 'md-editor-v3';
import 'md-editor-v3/lib/style.css';
import {useSystemStore} from "@/stores/system";
import {computed} from "vue";

const props = defineProps({
  text: {
    type: String,
    default: '发个友善的评论来见证当下!'
  }
})


</script>